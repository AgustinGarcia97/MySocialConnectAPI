package com.example.socialconnect.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserRequest {
    private String username;
    private String name;
    private String lastname;
}
