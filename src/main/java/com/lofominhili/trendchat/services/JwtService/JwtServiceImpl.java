package com.lofominhili.trendchat.services.JwtService;

import com.lofominhili.trendchat.entities.TokenWhiteListEntity;
import com.lofominhili.trendchat.exceptions.TokenValidationException;
import com.lofominhili.trendchat.repository.TokenWhiteListRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.access_token.lifetime}")
    private Integer tokenLifetime;

    private final TokenWhiteListRepository tokenWhiteListRepository;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String token = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifetime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        TokenWhiteListEntity tokenWhiteListEntity = new TokenWhiteListEntity();
        tokenWhiteListEntity.setToken(token);
        tokenWhiteListEntity.setUsername(extractUsername(token));
        tokenWhiteListRepository.save(tokenWhiteListEntity);
        return token;
    }

    @Override
    public void clearWhiteList(String username) {
        tokenWhiteListRepository.deleteAllByUsername(username);
    }

    @Override
    public void deleteTokenInWhiteList(String token) {
        tokenWhiteListRepository.deleteByToken(token);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) throws TokenValidationException {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            throw new TokenValidationException(e.getMessage());
        }
    }

    @Override
    public boolean isTokenInWhiteList(String token) {
        return tokenWhiteListRepository.findByToken(token).isPresent();
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) return null;
        return header.split(" ")[1].trim();
    }
}
