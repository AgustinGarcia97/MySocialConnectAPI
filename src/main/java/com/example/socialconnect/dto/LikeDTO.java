package com.example.socialconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private Long likeId;
    private PostIdDTO post;
    private UserIdDTO user;
}
