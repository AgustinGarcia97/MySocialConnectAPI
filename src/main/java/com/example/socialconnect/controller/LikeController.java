package com.example.socialconnect.controller;


import com.example.socialconnect.controller.requests.CreateLikeRequest;
import com.example.socialconnect.dto.LikeDTO;
import com.example.socialconnect.service.LikeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/create")
    private ResponseEntity<LikeDTO> createLike(@RequestBody CreateLikeRequest createLikeRequest) {
        LikeDTO created = likeService.addLike(createLikeRequest);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{postId}")
    private ResponseEntity<List<LikeDTO>> getLikesPosts(@PathVariable Long postId) {
        List<LikeDTO> likes = likeService.getLikesPosts(postId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/{postId}/comments")
    private ResponseEntity<List<LikeDTO>> getLikesPostsComments(@PathVariable Long postId ) {
        List<LikeDTO> likes = likeService.getLikes(postId);
        return ResponseEntity.ok(likes);

    }


    @DeleteMapping("/{likeId}/{commentId}")
    private ResponseEntity<String> deleteLike(@PathVariable Long likeId, @PathVariable Long commentId){
        return ResponseEntity.ok(likeService.deleteLike(likeId,commentId));
    }


    @DeleteMapping("/{postId}/user/{userId}")
    private ResponseEntity<String> deleteLikePost(@PathVariable Long postId, @PathVariable UUID userId){
        likeService.deleteLikePost(postId,userId);
        return ResponseEntity.ok("like deleted");
    }


}
