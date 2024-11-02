package com.example.socialconnect.controller;

import com.example.socialconnect.controller.requests.CreateCommentRequest;
import com.example.socialconnect.dto.CommentDTO;
import com.example.socialconnect.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long commentId) {
        CommentDTO commentDTO = commentService.getCommentDTOById(commentId);
        return ResponseEntity.ok(commentDTO);
    }


    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CreateCommentRequest comment) {
        CommentDTO commentDTO = commentService.createComment(comment);
        return ResponseEntity.ok(commentDTO);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Boolean> deleteComment( @PathVariable Long commentId) {
        boolean flag = commentService.deleteComment(commentId);
        return ResponseEntity.ok(flag);
    }
}
