package com.example.socialconnect.controller;


import com.example.socialconnect.controller.requests.CreateLikeRequest;
import com.example.socialconnect.dto.LikeDTO;
import com.example.socialconnect.service.LikeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/create")
    private ResponseEntity<LikeDTO> createLike(@RequestBody CreateLikeRequest createLikeRequest) {
        LikeDTO created = likeService.addLike(createLikeRequest);
        return ResponseEntity.ok(created);
    }

}
