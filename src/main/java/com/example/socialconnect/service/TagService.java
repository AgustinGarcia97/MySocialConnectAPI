package com.example.socialconnect.service;

import com.example.socialconnect.controller.requests.CreateTagRequest;
import com.example.socialconnect.dto.TagDTO;
import com.example.socialconnect.model.Post;
import com.example.socialconnect.model.Tag;
import com.example.socialconnect.model.User;
import com.example.socialconnect.repository.PostRepository;
import com.example.socialconnect.repository.TagRepository;
import com.example.socialconnect.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final PostRepository postRepository;

    private final UserService userService;

    private final PostService postService;
    private final ModelMapper modelMapper;

    public List<TagDTO> createTag(CreateTagRequest createTagRequest){
        List<UUID> userIdList = createTagRequest.getUserIdList();
        List<Tag> tagged = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        Long postId = createTagRequest.getPostId();

        Post post =postService.findPostById(postId);

        if(post == null){
            return null;
        }

        userIdList.forEach(
                        userId -> {
                            User user =  userService.getUserById(userId);
                            if(user != null){
                                userList.add(user);
                            }
                        });

        userList.forEach(
                user -> {
                    Tag tag = new Tag();
                    tag.setUser(user);
                    tagged.add(tag);
                    tag.setPost(post);
                    tagRepository.save(tag);
                }
        );

        post.setTagged(tagged);
        postRepository.save(post);

        return
                tagged.stream()
                        .map(tag -> modelMapper.map(tag, TagDTO.class))
                        .collect(Collectors.toList());


    }


}
