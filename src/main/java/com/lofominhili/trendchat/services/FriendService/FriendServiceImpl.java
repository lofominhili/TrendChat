package com.lofominhili.trendchat.services.FriendService;

import com.lofominhili.trendchat.entities.FriendEntity;
import com.lofominhili.trendchat.entities.UserEntity;
import com.lofominhili.trendchat.exceptions.FriendDuplicateException;
import com.lofominhili.trendchat.exceptions.FriendsListVisibleException;
import com.lofominhili.trendchat.repository.FriendRepository;
import com.lofominhili.trendchat.repository.UserRepository;
import com.lofominhili.trendchat.utils.FriendsVisibility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Override
    @Transactional
    public void addFriend(String username) throws FriendDuplicateException {
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity addedFriend = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with this username was not found!"));
        FriendEntity friend = new FriendEntity();
        friend.setUser(currentUser);
        friend.setFriend(addedFriend);
        for (FriendEntity friendEntity : friendRepository.findAllByUser(currentUser)) {
            if (friendEntity.getFriend().getUsername().equals(addedFriend.getUsername()))
                throw new FriendDuplicateException("This user already in your friend list");
        }
        friendRepository.save(friend);
    }

    @Override
    public List<String> getListOfFriends(String username) throws FriendsListVisibleException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with this username was not found!"));
        if (user.getFriendsVisibility().equals(FriendsVisibility.INVISIBLE))
            throw new FriendsListVisibleException("User hidden his friends list");
        List<FriendEntity> friends = friendRepository.findAllByUser(user);
        return friends.stream().map(friend -> friend.getFriend().getUsername()).toList();
    }

    @Override
    public List<String> getListOfFriends() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<FriendEntity> friends = friendRepository.findAllByUser(user);
        return friends.stream().map(friend -> friend.getFriend().getUsername()).toList();
    }

    @Override
    public void hideFriends() throws FriendsListVisibleException {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getFriendsVisibility().equals(FriendsVisibility.INVISIBLE))
            throw new FriendsListVisibleException("Your friends list is already hidden");
        user.setFriendsVisibility(FriendsVisibility.INVISIBLE);
        userRepository.save(user);
    }
}
