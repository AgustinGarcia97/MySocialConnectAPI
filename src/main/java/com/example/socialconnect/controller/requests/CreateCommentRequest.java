package com.example.socialconnect.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Data
@Jacksonized
public class CreateCommentRequest {
    private String comment;
    private UUID userId;
    private Long postId;
    private LocalDateTime createdAt;
}
