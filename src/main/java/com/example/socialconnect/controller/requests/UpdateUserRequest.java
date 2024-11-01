package com.example.socialconnect.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class UpdateUserRequest {
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String photoUrl;

}
