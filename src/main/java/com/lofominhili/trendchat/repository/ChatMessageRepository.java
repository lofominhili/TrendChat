package com.lofominhili.trendchat.repository;

import com.lofominhili.trendchat.entities.ChatMessageEntity;
import com.lofominhili.trendchat.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    List<ChatMessageEntity> findAllBySenderAndReceiver(UserEntity sender, UserEntity receiver);

}
