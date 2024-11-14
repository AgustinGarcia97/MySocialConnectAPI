package com.example.socialconnect.service;

import com.example.socialconnect.controller.requests.CreatePostRequest;
import com.example.socialconnect.dto.PhotoDTO;
import com.example.socialconnect.dto.PostDTO;
import com.example.socialconnect.dto.TagDTO;
import com.example.socialconnect.model.Photo;
import com.example.socialconnect.model.Post;
import com.example.socialconnect.model.Tag;
import com.example.socialconnect.model.User;
import com.example.socialconnect.repository.PostRepository;
import com.example.socialconnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PhotoService photoService;

    private final UserService userService;

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


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
    public List<PostDTO> getSomePosts(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        List<Post> posts = postRepository.findAll(pageable).getContent();

        return posts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(post -> modelMapper.map(post, PostDTO.class))
                .toList();
    }


    @Transactional
    public PostDTO createPost(CreatePostRequest createPostRequest){
        Post post = modelMapper.map(createPostRequest, Post.class);
        List<Photo> photos = new ArrayList<>();
        List<String> images = createPostRequest.getPhotos();
        post.setCreatedAt(LocalDateTime.now());
        User author = userService.getUserById(createPostRequest.getUserId());
        List<String> tags = createPostRequest.getTagged();
        List<Tag> tagged = new ArrayList<>();
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
        if(tags != null){
            for (String tag : tags) {
                userRepository.findById(UUID.fromString(tag)).ifPresent(u -> tagged.add(new Tag(u)));
            }
            post.setTagged(tagged);
        }


        post.setPhotoList(photos);
        Post savedPost = postRepository.save(post);

        return modelMapper.map(savedPost, PostDTO.class);

    }

    @Transactional
    public List<PostDTO> getCommentByPostId(Long userId) {
        return null;
    }

    @Transactional
    public String deletePostById(Long postId) {
         postRepository.deleteById(postId);
         return("Post deleted");
    }

    @Transactional
    public List<PostDTO> getSomeFollowingPosts(UUID userId,int page, int size) {
        User user = userService.getUserById(userId);
        List<User> following = user.getFollowing();

        List<PostDTO> postsDTO = following.stream()
                .flatMap(userFollowing -> userFollowing.getUserPostList().stream())
                .map(post -> modelMapper.map(post, PostDTO.class))
                .toList();

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, postsDTO.size());

        if (fromIndex > postsDTO.size()) {
            return List.of();
        }
        return postsDTO.subList(fromIndex, toIndex);
    }

    @Transactional
    public List<PostDTO> getPostLiked(UUID userId) {
        return postRepository.findAll()
                        .stream()
                        .filter(post ->
                                post.getLikes()
                                .stream()
                                        .anyMatch(like ->
                                                        like.getUser()
                                                        .getUserId()
                                                        .equals(userId)))
                        .map(p -> modelMapper.map(p, PostDTO.class))
                        .toList();
    };

    @Transactional
    public List<PostDTO> getPostTagged(UUID userId) {
        return postRepository.findAll()
                .stream()
                .filter(post -> post.getTagged()
                        .stream()
                        .anyMatch(tag -> tag.getUser()
                                .getUserId().equals(userId
                                )
                        )
                )
                .map(post -> modelMapper.map(post,PostDTO.class)).toList();
    }



}
