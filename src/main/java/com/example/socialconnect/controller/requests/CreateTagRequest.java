package com.example.socialconnect.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateTagRequest {
    private List<UUID> userIdList;
    private Long postId;
}
