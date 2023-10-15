package com.lofominhili.trendchat.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lofominhili.trendchat.dto.ChatMessageDTO;
import com.lofominhili.trendchat.entities.UserEntity;
import com.lofominhili.trendchat.repository.UserRepository;
import com.lofominhili.trendchat.services.ChatMessageService.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final UserRepository userRepository;
    private final ChatMessageService chatMessageService;

    private final List<WebSocketSession> webSocketSessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessions.add(session);
        for (ChatMessageDTO chatMessageDTO : chatMessageService.getMessages(getSender(session), getReceiver(session))) {
            ObjectMapper mapper = new ObjectMapper();
            session.sendMessage(new TextMessage(mapper.writeValueAsBytes(chatMessageDTO)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        chatMessageService.saveMessage(message.getPayload(), getSender(session), getReceiver(session));
        for (WebSocketSession webSocketSession : getRequiredSessions(session)) {
            ObjectMapper mapper = new ObjectMapper();
            ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
            chatMessageDTO.setContent(message.getPayload());
            chatMessageDTO.setUsername(getSender(session).getUsername());
            chatMessageDTO.setTimeStamp(new Date());
            webSocketSession.sendMessage(new TextMessage(mapper.writeValueAsBytes(chatMessageDTO)));
        }
    }

    private List<WebSocketSession> getRequiredSessions(WebSocketSession session) {
        return webSocketSessions.stream().filter(s -> {
            Map<String, Object> users = s.getAttributes();
            UserEntity sender = (UserEntity) users.get("sender");
            UserEntity receiver = (UserEntity) users.get("receiver");
            return getSender(session).getUsername().equals(receiver.getUsername()) &&
                    getReceiver(session).getUsername().equals(sender.getUsername());
        }).toList();
    }

    private UserEntity getSender(WebSocketSession session) {
        return (UserEntity) ((UsernamePasswordAuthenticationToken) session.getPrincipal()).getPrincipal();
    }

    private UserEntity getReceiver(WebSocketSession session) {
        String path = session.getUri().getPath();
        String username = path.substring(path.lastIndexOf('/') + 1);
        return userRepository.findByUsername(username).get();
    }


}
