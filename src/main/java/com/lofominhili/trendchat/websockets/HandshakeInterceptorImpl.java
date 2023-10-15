package com.lofominhili.trendchat.websockets;

import com.lofominhili.trendchat.entities.UserEntity;
import com.lofominhili.trendchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class HandshakeInterceptorImpl implements HandshakeInterceptor {

    private final UserRepository userRepository;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        UserEntity sender = (UserEntity) ((UsernamePasswordAuthenticationToken) request.getPrincipal()).getPrincipal();
        String path = request.getURI().getPath();
        String username = path.substring(path.lastIndexOf('/') + 1);
        UserEntity receiver;
        if (userRepository.findByUsername(username).isEmpty() || username.equals(sender.getUsername())) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return false;
        }
        receiver = userRepository.findByUsername(username).get();
        attributes.put("sender", sender);
        attributes.put("receiver", receiver);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
