package com.example.socialconnect.service;

import com.example.socialconnect.dto.PhotoDTO;
import com.example.socialconnect.model.Photo;
import com.example.socialconnect.repository.PhotoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PhotoService {
    private final ModelMapper modelMapper;

    private final PhotoRepository photoRepository;

    private EntityManager entityManager;

    @Transactional
    public PhotoDTO addPhoto(String photoUrl) {
        Photo photo = photoRepository.save(modelMapper.map(photoUrl, Photo.class));

        return modelMapper.map(photo, PhotoDTO.class);

    }

    @Transactional
    public Photo searchPhoto(String photoUrl) {
        return photoRepository.findByphotoUrl(photoUrl);
    }

    @Transactional
    public Photo updatePhoto(Photo photo){
        Photo searchedPhoto = photoRepository.findByphotoUrl(photo.getPhotoUrl());
        if(searchedPhoto != null){
            modelMapper.map(photo, searchedPhoto);
            return photoRepository.save(searchedPhoto);
        }

        return photo;
    }

}
