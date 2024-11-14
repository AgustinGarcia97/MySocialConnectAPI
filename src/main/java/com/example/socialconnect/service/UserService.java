package com.example.socialconnect.service;

import com.example.socialconnect.controller.requests.CreateUserRequest;
import com.example.socialconnect.controller.requests.SearchUserRequest;
import com.example.socialconnect.controller.requests.UpdateUserRequest;
import com.example.socialconnect.dto.PhotoDTO;
import com.example.socialconnect.dto.PostDTO;
import com.example.socialconnect.dto.UserDTO;
import com.example.socialconnect.model.Photo;
import com.example.socialconnect.model.User;
import com.example.socialconnect.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public UserDTO getUserDTOById(UUID userId) {
        return modelMapper.map( userRepository.findById(userId), UserDTO.class);
    }

    @Transactional
    public User getUserById(UUID userId) {
        return  userRepository.findById(userId).get();
    }


    @Transactional
    public UserDTO addUser(CreateUserRequest userRequest){
        User user = modelMapper.map(userRequest, User.class);
        Photo photo = null;

        if (userRequest.getProfilePicture() != null) {
            photo = photoService.searchPhoto(userRequest.getProfilePicture());
            if (photo == null) {
                photo = new Photo();
                photo.setPhotoUrl(userRequest.getProfilePicture());
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
        UserDTO userToDelete = getUserDTOById(userId);
        if(userToDelete != null){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Transactional
    public List<UserDTO> search(SearchUserRequest searchUser) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);

        Root<User> userRoot = cq.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        if (searchUser.getUsername() != null && !searchUser.getUsername().isEmpty()) {
            predicates.add(cb.like(cb.lower(userRoot.get("username")), "%" + searchUser.getUsername().toLowerCase() + "%"));
            cq.orderBy(cb.asc(cb.length(userRoot.get("username"))));
        }



        cq.where(predicates.toArray(new Predicate[0]));

        List<User> users = entityManager.createQuery(cq).getResultList();

        return users.stream()
                .map(user -> {
                    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                    List<PostDTO> postDTOs = user.getUserPostList().stream()
                            .map(post -> modelMapper.map(post, PostDTO.class))
                            .collect(Collectors.toList());
                    userDTO.setPosts(postDTOs);

                    return userDTO;
                })
                .collect(Collectors.toList());
    }





}
