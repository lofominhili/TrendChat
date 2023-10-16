package com.lofominhili.trendchat.controllers;

import com.lofominhili.trendchat.dto.BasicDto.SuccessDTO;
import com.lofominhili.trendchat.exceptions.FriendDuplicateException;
import com.lofominhili.trendchat.exceptions.FriendsListVisibleException;
import com.lofominhili.trendchat.services.FriendService.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Add user to friends list")
    @SecurityRequirement(name = "JWT")
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

    @Operation(summary = "Get friends list of current user")
    @SecurityRequirement(name = "JWT")
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

    @Operation(summary = "Get friends list of chosen user")
    @SecurityRequirement(name = "JWT")
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

    @Operation(summary = "Hide friends list from other users")
    @SecurityRequirement(name = "JWT")
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
