package com.example.socialconnect.service.securityservice;


import com.example.socialconnect.controller.auth.AuthenticationRequest;
import com.example.socialconnect.controller.auth.AuthenticationResponse;
import com.example.socialconnect.controller.auth.RegisterRequest;
import com.example.socialconnect.controller.config.JwtService;
import com.example.socialconnect.dto.PhotoDTO;
import com.example.socialconnect.dto.PostDTO;
import com.example.socialconnect.dto.UserIdDTO;
import com.example.socialconnect.model.Role;
import com.example.socialconnect.repository.UserRepository;
import com.example.socialconnect.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .role(request.getRole())
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken( user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .userId(user.getUserId())
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
      //autentica
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())); //si esta linea pasa, significa que el usuario esta autenticado dentro del server sin hacer una query de checkeo a la db
        //como se que existe, se busca la info del usuario

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        //si el usuario esta autenticado y encontre la data retorno un token
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .userId(user.getUserId())
                .role(user.getRole() != null? user.getRole(): Role.valueOf(""))
                .name(user.getName())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .email(user.getEmail())
                .posts(
                        user
                        .getUserPostList()
                        .stream()
                        .map(post -> modelMapper.map(post, PostDTO.class))
                        .collect(Collectors.toList())
                )
                .profilePicture(user.getProfilePicture() != null ?
                        modelMapper.map(user.getProfilePicture(), PhotoDTO.class)
                        :
                        new PhotoDTO("")
                )
                .followers(
                        user
                        .getFollowers()
                        .stream()
                        .map(follower -> modelMapper.map(follower, UserIdDTO.class))
                        .collect(Collectors.toList())
                )
                .following(user
                        .getFollowing()
                        .stream()
                        .map(following -> modelMapper.map(following, UserIdDTO.class))
                        .collect(Collectors.toList())
                )
                .build();
    }
}
