package com.lofominhili.trendchat.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageDTO {

    private String username;

    private String content;

    private Date timeStamp;
}
