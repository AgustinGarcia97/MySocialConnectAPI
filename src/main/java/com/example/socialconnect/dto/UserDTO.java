package com.example.socialconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String photoUrl;

}
