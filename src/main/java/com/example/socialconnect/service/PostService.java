package com.example.socialconnect.service;

import com.example.socialconnect.controller.requests.CreatePostRequest;
import com.example.socialconnect.dto.PhotoDTO;
import com.example.socialconnect.dto.PostDTO;
import com.example.socialconnect.model.Photo;
import com.example.socialconnect.model.Post;
import com.example.socialconnect.model.User;
import com.example.socialconnect.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PhotoService photoService;

    private final UserService userService;

    private final ModelMapper modelMapper;


    @Transactional
    public Post findPostById(Long postId) {
        return postRepository
                .findById(postId)
                .get();


    }

    @Transactional
    public PostDTO findPostDTOById(Long postId) {
        return modelMapper.map(findPostById(postId), PostDTO.class);
    }




    @Transactional
    public PostDTO createPost(CreatePostRequest createPostRequest){
        Post post = modelMapper.map(createPostRequest, Post.class);
        List<Photo> photos = new ArrayList<>();
        List<String> images = createPostRequest.getPhotos();

        User author = userService.getUserById(createPostRequest.getUserId());
        if (author != null) {
            post.setUser(author);
        } else{
            return null;
        }

        if(images != null){
            images.forEach(image -> {
                Photo searched = photoService.searchPhoto(image);
                if(searched != null){
                    photos.add(searched);
                } else {
                    Photo newPhoto = new Photo();
                    newPhoto.setPhotoUrl(image);
                    photos.add(newPhoto);
                }
            });
        }
        post.setPhotoList(photos);
        Post savedPost = postRepository.save(post);

        return modelMapper.map(savedPost, PostDTO.class);


    }



}
