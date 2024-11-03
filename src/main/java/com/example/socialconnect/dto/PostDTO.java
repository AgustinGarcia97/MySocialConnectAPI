package com.example.socialconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDTO {
    private Long postId;
    private String title;
    private String description;
    private List<PhotoDTO> photoList;
    private List<CommentDTO> comments;
    private List<TagDTO> tagged;
}
