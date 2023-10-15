package com.lofominhili.trendchat.services.ChatMessageService;

import com.lofominhili.trendchat.dto.ChatMessageDTO;
import com.lofominhili.trendchat.entities.UserEntity;

import java.util.List;

public interface ChatMessageService {
    ChatMessageDTO saveMessage(String content, UserEntity sender, UserEntity receiver);

    List<ChatMessageDTO> getMessages(UserEntity sender, UserEntity receiver);
}
