package com.example.socialconnect.controller.requests;

import com.example.socialconnect.dto.TagDTO;
import com.example.socialconnect.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    private String title;
    private String description;
    private UUID userId;
    private List<String> photos;
    private String location;
    private List<String> tagged;
}
