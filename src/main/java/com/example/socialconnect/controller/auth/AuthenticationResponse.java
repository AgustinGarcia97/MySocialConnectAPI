package com.example.socialconnect.controller.auth;


import com.example.socialconnect.dto.PhotoDTO;
import com.example.socialconnect.dto.PostDTO;
import com.example.socialconnect.dto.UserIdDTO;
import com.example.socialconnect.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    private UUID userId;
    private Role role;
    private String name;
    private String lastname;
    private String email;
    private List<UserIdDTO> followers;
    private List<UserIdDTO> following;
    private String username;
    private List<PostDTO> posts;
    private PhotoDTO profilePicture;
}
