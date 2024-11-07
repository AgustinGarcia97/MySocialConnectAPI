package com.example.socialconnect.controller;

import com.example.socialconnect.controller.requests.CreatePostRequest;
import com.example.socialconnect.dto.PostDTO;
import com.example.socialconnect.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/posts", produces = "application/json")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        PostDTO postDTO = postService.findPostDTOById(postId);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postsDTO = postService.getSomePosts();
        return ResponseEntity.ok(postsDTO);
    }


    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostRequest createPostRequest) {
        PostDTO postDTO = postService.createPost(createPostRequest);
        return ResponseEntity.ok(postDTO);
    }
}
