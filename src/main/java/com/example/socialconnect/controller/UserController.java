package com.example.socialconnect.controller;


import com.example.socialconnect.controller.requests.CreateUserRequest;
import com.example.socialconnect.controller.requests.UpdateUserRequest;
import com.example.socialconnect.dto.UserDTO;
import com.example.socialconnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

   @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") UUID userId) {
       UserDTO userDTO = userService.getUserDTOById(userId);
       return new ResponseEntity<>(userDTO,OK);
   }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getSomeUsers() {
        List<UserDTO> someUsers = userService.getSomeUsers();
        return new ResponseEntity<>(someUsers, OK);
    }


    @PostMapping("/create")
   public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest createUserRequest){
       UserDTO createdUser = userService.addUser(createUserRequest);
       return new ResponseEntity<>(createdUser, CREATED);
   }

   @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") UUID userId, @RequestBody UpdateUserRequest updateUserRequest){
       UserDTO updatedUser = userService.updateUser(userId,updateUserRequest);
       return new ResponseEntity<>(updatedUser, OK);
   }

   @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("userId") UUID userId){
       boolean deleted = userService.deleteUser(userId);
       return new ResponseEntity<>(deleted, OK);
   }


}
