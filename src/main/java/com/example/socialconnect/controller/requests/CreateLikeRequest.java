package com.example.socialconnect.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Data
public class CreateLikeRequest {
    private Long postId;
    private Long commentId;
    private UUID userId;
}
