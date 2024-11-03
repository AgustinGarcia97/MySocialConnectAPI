package com.example.socialconnect.controller;


import com.example.socialconnect.controller.requests.CreateTagRequest;
import com.example.socialconnect.dto.TagDTO;
import com.example.socialconnect.service.TagService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    private TagService tagService;

    @PostMapping("/send")
    public ResponseEntity<List<TagDTO>> addTag(@RequestBody CreateTagRequest createTagRequest) {
        List<TagDTO> tagsDTO = tagService.createTag(createTagRequest);
        return ResponseEntity.ok(tagsDTO);
    }

}
