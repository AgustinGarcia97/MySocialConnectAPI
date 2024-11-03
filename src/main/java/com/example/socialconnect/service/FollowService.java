package com.example.socialconnect.service;

import com.example.socialconnect.dto.FollowDTO;
import com.example.socialconnect.model.User;
import com.example.socialconnect.repository.FollowRepository;
import com.example.socialconnect.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Data
@AllArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public boolean toFollow(UUID followerId, UUID followedId) {
        User follower = userRepository.findById(followerId).orElse(null);
        User followed = userRepository.findById(followedId).orElse(null);

        if (follower != null && followed != null) {
            if(!follower.getFollowing().contains(followed)) {
                follower.getFollowing().add(followed);
                followed.getFollowers().add(follower);
                userRepository.save(follower);
                return true;
            }
        }
        return false;
    }

    public boolean toUnfollow(UUID followerId, UUID followedId) {
        User follower = userRepository.findById(followerId).orElse(null);
        User followed = userRepository.findById(followedId).orElse(null);
        if (follower != null && followed != null) {
            follower.getFollowing().remove(followed);
            followed.getFollowers().remove(follower);
            return true;
        }
        return false;
    }
}
