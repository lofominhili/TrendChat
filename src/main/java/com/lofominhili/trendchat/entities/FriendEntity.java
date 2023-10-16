package com.lofominhili.trendchat.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "_friend")
@Data
public class FriendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private UserEntity friend;

}
