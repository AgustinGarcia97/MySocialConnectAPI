package com.example.socialconnect.controller;

import com.example.socialconnect.controller.requests.CreatePostRequest;
import com.example.socialconnect.dto.PostDTO;
import com.example.socialconnect.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequiredArgsConstructor
@RequestMapping(value = "/posts", produces = "application/json")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        PostDTO postDTO = postService.findPostDTOById(postId);
        return ResponseEntity.ok(postDTO);
    }


    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostRequest createPostRequest) {
        PostDTO postDTO = postService.createPost(createPostRequest);
        return ResponseEntity.ok(postDTO);
    }
}
