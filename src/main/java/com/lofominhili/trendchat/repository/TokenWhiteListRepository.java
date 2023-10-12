package com.lofominhili.trendchat.repository;

import com.lofominhili.trendchat.entities.TokenWhiteListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenWhiteListRepository extends JpaRepository<TokenWhiteListEntity, Long> {
    Optional<TokenWhiteListEntity> findByToken(String token);

    void deleteAllByUsername(String username);

    void deleteByToken(String token);
}
