package com.example.socialconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID userId;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private PhotoDTO profilePicture;
    private List<FollowerDTO> followers;
    private List<FollowedDTO> following;
    private String bio;
    private List<PostDTO> posts;

}
