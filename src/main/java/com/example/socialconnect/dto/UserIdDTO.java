package com.example.socialconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdDTO {
    private UUID userId;
    private String name;
    private String lastname;
    private String username;
}
