package com.lofominhili.trendchat.controllers;

import com.lofominhili.trendchat.dto.BasicDto.SuccessDTO;
import com.lofominhili.trendchat.exceptions.FriendDuplicateException;
import com.lofominhili.trendchat.exceptions.FriendsListVisibleException;
import com.lofominhili.trendchat.services.FriendService.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/friends")
@RequiredArgsConstructor
public class FriendsListController {

    private final FriendService friendService;

    @PostMapping(value = "/add/{username}")
    public ResponseEntity<SuccessDTO<String>> addFriend(@PathVariable String username) throws FriendDuplicateException {
        friendService.addFriend(username);
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "add friend",
                        "Successfully added"
                ), HttpStatus.OK
        );
    }

    @GetMapping(value = "/get")
    public ResponseEntity<SuccessDTO<List<String>>> getListOfFriends() {
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "get my friends",
                        friendService.getListOfFriends()
                ), HttpStatus.OK
        );
    }

    @GetMapping(value = "/get/{username}")
    public ResponseEntity<SuccessDTO<List<String>>> getListOfFriends(@PathVariable String username) throws FriendsListVisibleException {
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "get his friends",
                        friendService.getListOfFriends(username)
                ), HttpStatus.OK
        );
    }

    @PostMapping(value = "/hide")
    public ResponseEntity<SuccessDTO<String>> hideFriends() throws FriendsListVisibleException {
        friendService.hideFriends();
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "hide friends",
                        "Successfully hidden!"
                ), HttpStatus.OK
        );
    }

}
