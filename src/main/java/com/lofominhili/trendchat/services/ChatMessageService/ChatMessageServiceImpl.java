package com.lofominhili.trendchat.services.ChatMessageService;

import com.lofominhili.trendchat.dto.ChatMessageDTO;
import com.lofominhili.trendchat.entities.ChatMessageEntity;
import com.lofominhili.trendchat.entities.UserEntity;
import com.lofominhili.trendchat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessageDTO saveMessage(String content, UserEntity sender, UserEntity receiver) {
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setContent(content);
        chatMessageEntity.setSender(sender);
        chatMessageEntity.setReceiver(receiver);
        chatMessageEntity.setTimeStamp(new Date());
        return entityToDto(chatMessageRepository.save(chatMessageEntity));
    }

    @Override
    public List<ChatMessageDTO> getMessages(UserEntity sender, UserEntity receiver) {
        List<ChatMessageEntity> result = new ArrayList<>();
        result.addAll(chatMessageRepository.findAllBySenderAndReceiver(sender, receiver));
        result.addAll(chatMessageRepository.findAllBySenderAndReceiver(receiver, sender));
        result.sort(this::sortMessages);
        return result.stream().map(this::entityToDto).toList();
    }

    private int sortMessages(ChatMessageEntity first, ChatMessageEntity second) {
        if (first.getTimeStamp().before(second.getTimeStamp())) return -1;
        else if (first.getTimeStamp().after(second.getTimeStamp())) return 1;
        else return 0;
    }

    private ChatMessageDTO entityToDto(ChatMessageEntity chatMessageEntity) {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setContent(chatMessageEntity.getContent());
        chatMessageDTO.setUsername(chatMessageEntity.getSender().getUsername());
        chatMessageDTO.setTimeStamp(chatMessageEntity.getTimeStamp());
        return chatMessageDTO;
    }
}
