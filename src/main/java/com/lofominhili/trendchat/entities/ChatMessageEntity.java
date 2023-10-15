package com.lofominhili.trendchat.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name = "_chat_message")
@Data
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "time_stamp")
    private Date timeStamp;

    @ManyToOne
    private UserEntity sender;

    @ManyToOne
    private UserEntity receiver;
}
