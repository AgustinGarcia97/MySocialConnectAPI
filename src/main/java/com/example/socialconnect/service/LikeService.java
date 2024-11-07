package com.example.socialconnect.service;

import com.example.socialconnect.controller.requests.CreateLikeRequest;
import com.example.socialconnect.dto.CommentDTO;
import com.example.socialconnect.dto.CommentIdDTO;
import com.example.socialconnect.dto.LikeDTO;
import com.example.socialconnect.dto.UserIdDTO;
import com.example.socialconnect.model.Comment;
import com.example.socialconnect.model.Like;
import com.example.socialconnect.model.Post;
import com.example.socialconnect.model.User;
import com.example.socialconnect.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    private final PostService postService;

    private final CommentService commentService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Transactional
    public LikeDTO addLike(CreateLikeRequest createLikeRequest) {
        Like like = modelMapper.map(createLikeRequest, Like.class);
        if(createLikeRequest.getPostId() != null) {
            Post post = postService.findPostById(createLikeRequest.getPostId());
            if (post != null) {
                post.setLikeToList(like);
                like.setPost(post);
            }
        }
        if(createLikeRequest.getCommentId() != null) {
            Comment comment = commentService.getCommentById(createLikeRequest.getCommentId());
            if (comment != null) {
                comment.addLikeToList(like);
                like.setComment(comment);
            }
        }
        if(createLikeRequest.getUserId() != null){
            User user = userService.getUserById(createLikeRequest.getUserId());
            like.setUser(user);
        }


       Like created = likeRepository.save(like);
        return modelMapper.map(created, LikeDTO.class);


    }


}
