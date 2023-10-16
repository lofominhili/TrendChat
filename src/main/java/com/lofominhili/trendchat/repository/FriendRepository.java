package com.lofominhili.trendchat.repository;

import com.lofominhili.trendchat.entities.FriendEntity;
import com.lofominhili.trendchat.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    List<FriendEntity> findAllByUser(UserEntity user);
}
