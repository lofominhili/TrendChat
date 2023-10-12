package com.lofominhili.trendchat.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "_token_whitelist")
public class TokenWhiteListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "token")
    private String token;

    @Column(name = "username")
    private String username;
}
