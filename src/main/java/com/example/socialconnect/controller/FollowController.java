package com.example.socialconnect.controller;


import com.example.socialconnect.controller.requests.CreateFollowRequest;
import com.example.socialconnect.dto.FollowDTO;
import com.example.socialconnect.service.FollowService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{followerId}/follow/{followedId}")
    public ResponseEntity<Boolean> follow(@PathVariable UUID followerId, @PathVariable UUID followedId) {
        boolean flag = followService.toFollow(followerId,followedId);
        return ResponseEntity.ok(flag);

    }

    @DeleteMapping("/{followerId}/unfollow/{followedId}")
    public ResponseEntity<Boolean> unfollow(@PathVariable UUID followerId, @PathVariable UUID followedId) {
        boolean flag = followService.toUnfollow(followerId,followedId);
        return ResponseEntity.ok(flag);
    }


}
