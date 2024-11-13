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
import com.example.socialconnect.repository.CommentRepository;
import com.example.socialconnect.repository.LikeRepository;
import com.example.socialconnect.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    private final PostService postService;

    private final CommentService commentService;

    private final UserService userService;

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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

    @Transactional
    public List<LikeDTO> getLikesPosts(Long postId) {

        return likeRepository
                .findAll()
                .stream()
                .filter(like -> Objects.equals(like.getPost().getPostId(), postId))
                .map(like -> modelMapper.map(like,LikeDTO.class))
                .collect(Collectors.toList());

    }


    @Transactional
    public List<LikeDTO> getLikes(Long postId) {
        List<Comment> comments = postRepository.findById(postId).get().getComments();

        return comments.stream().map(comment -> modelMapper.map(comment.getLikes(),LikeDTO.class)).collect(Collectors.toList());

    }


    @Transactional
    public String deleteLike(Long likeId,Long commentId) {
        Comment comment = commentService.getCommentById(commentId);

        comment.removeLikeChild(likeRepository.findById(likeId).get());
        commentRepository.save(comment);
        likeRepository.deleteById(likeId);
        return ("Dislike exitoso");
    }

    @Transactional
    public String deleteLikePost(Long postId, UUID userId){
        List<Like> likes = likeRepository
                .findAll()
                .stream()
                .filter(like -> like.getPost() != null)
                .filter(like -> Objects.equals(like.getPost().getPostId(), postId))
                .toList();

        Optional<Like> like = likes
                                .stream()
                                .filter(l -> l.getUser().getUserId().equals(userId) )
                                .findFirst();


        like.ifPresent(value -> likeRepository.deleteById(value.getLikeId()));




        return "Dislike post exitoso";
    }


}
