package com.example.socialconnect.service;

import com.example.socialconnect.controller.requests.CreateCommentRequest;
import com.example.socialconnect.dto.CommentDTO;
import com.example.socialconnect.model.Comment;
import com.example.socialconnect.model.Post;
import com.example.socialconnect.model.User;
import com.example.socialconnect.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {
    private final ModelMapper modelMapper;


    private final PostService postService;

    private final CommentRepository commentRepository;
    private final UserService userService;


    @Transactional
    public CommentDTO createComment(CreateCommentRequest createCommentRequest) {
        Long postId = createCommentRequest.getPostId();
        Comment comment = modelMapper.map(createCommentRequest, Comment.class);

        Post searchedPost = postService.findPostById(postId);
        User searchedUser = userService.getUserById(createCommentRequest.getUserId());

        if(searchedPost != null) {
            searchedPost.setCommentToList(comment);
        }
        if(searchedUser != null) {
            comment.setUser(searchedUser);
        }
        Comment saved = commentRepository.save(comment);
        return modelMapper.map(saved, CommentDTO.class);
    }

    @Transactional
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional
    public CommentDTO getCommentDTOById(Long id) {
        return modelMapper
                .map(commentRepository
                        .findById(id).orElse(null),
                        CommentDTO.class);
    }


    @Transactional
    public boolean deleteComment(Long commentId) {
       Comment comment = getCommentById(commentId);
       if(comment != null) {
           commentRepository.delete(comment);
           return true;
       }
       return false;

    }

}
