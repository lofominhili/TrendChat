package com.lofominhili.trendchat.services.FriendService;

import com.lofominhili.trendchat.exceptions.FriendDuplicateException;
import com.lofominhili.trendchat.exceptions.FriendsListVisibleException;

import java.util.List;

public interface FriendService {
    void addFriend(String username) throws FriendDuplicateException;

    List<String> getListOfFriends(String username) throws FriendsListVisibleException;

    List<String> getListOfFriends();

    void hideFriends() throws FriendsListVisibleException;
}
