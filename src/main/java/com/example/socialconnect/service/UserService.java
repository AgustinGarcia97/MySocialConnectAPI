package com.example.socialconnect.service;

import com.example.socialconnect.controller.requests.CreateUserRequest;
import com.example.socialconnect.controller.requests.UpdateUserRequest;
import com.example.socialconnect.dto.PhotoDTO;
import com.example.socialconnect.dto.UserDTO;
import com.example.socialconnect.model.Photo;
import com.example.socialconnect.model.User;
import com.example.socialconnect.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PhotoService photoService;

    private final ModelMapper modelMapper;

    private EntityManager entityManager;


    @Transactional
    public List<UserDTO> getSomeUsers() {
        Pageable pageable = PageRequest.of(0,10);
        Page<UserDTO> someUsers = userRepository.findAll(pageable).map((user -> modelMapper.map(user, UserDTO.class) ));
        return someUsers.getContent();
    }

    @Transactional
    public UserDTO getUserById(UUID userId) {
        return modelMapper.map( userRepository.findById(userId), UserDTO.class);
    }


    @Transactional
    public UserDTO addUser(CreateUserRequest userRequest){
        User user = modelMapper.map(userRequest, User.class);
        Photo photo = null;

        if (userRequest.getPhotoUrl() != null) {
            photo = photoService.searchPhoto(userRequest.getPhotoUrl());
            if (photo == null) {
                photo = new Photo();
                photo.setPhotoUrl(userRequest.getPhotoUrl());
            }
        }
        user.setProfilePicture(photo);
        User userCreated = userRepository.save(user);

        return modelMapper.map(userCreated, UserDTO.class);
    }

    @Transactional
    public UserDTO updateUser(UUID userId, UpdateUserRequest updateUserRequest){
        User existingUser = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("User not found with id: " + userId)
                );

        modelMapper.map(updateUserRequest,existingUser);

        if(updateUserRequest.getPhotoUrl() != null) {
            Photo photo = photoService.searchPhoto(updateUserRequest.getPhotoUrl());
            if (photo == null) {
                PhotoDTO newPhotoDTO = photoService.addPhoto(updateUserRequest.getPhotoUrl());
                Photo newPhoto = modelMapper.map(newPhotoDTO, Photo.class);
                existingUser.setProfilePicture(newPhoto);
            } else{
                Photo editedPhoto = photoService.updatePhoto(photo);
                existingUser.setProfilePicture(editedPhoto);
            }
        }

        User userSaved = userRepository.save(existingUser);
        return modelMapper.map(userSaved, UserDTO.class);
    }

    @Transactional
    public boolean deleteUser(UUID userId) {
        UserDTO userToDelete = getUserById(userId);
        if(userToDelete != null){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }




}
