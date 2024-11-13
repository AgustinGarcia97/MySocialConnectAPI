package com.example.socialconnect.controller.auth;



import com.example.socialconnect.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String lastname;
    private String email;
    private String password;
    private String username;
    private String profilePicture;
    private Role role;
    private String bio;
}
