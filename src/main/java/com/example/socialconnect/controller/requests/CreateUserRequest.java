package com.example.socialconnect.controller.requests;

import com.example.socialconnect.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class CreateUserRequest {

    @NotBlank
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String profilePicture;
    private Role role;


}
