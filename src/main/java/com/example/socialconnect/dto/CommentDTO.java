package com.example.socialconnect.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {
    private Long commentId;
    private String comment;
    private UserIdDTO user;
    private LocalDateTime createdAt;
    private List<LikeDTO> likes;
    private PostIdDTO post;
}
