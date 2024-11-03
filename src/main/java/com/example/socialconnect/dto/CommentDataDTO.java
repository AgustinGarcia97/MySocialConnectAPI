package com.example.socialconnect.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDataDTO {
    private Long commentId;
    private String comment;
    private LocalDateTime createdAt;
    private List<LikeDTO> likes;
}
