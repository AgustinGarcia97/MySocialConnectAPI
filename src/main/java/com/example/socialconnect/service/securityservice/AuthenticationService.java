package com.example.socialconnect.service.securityservice;


import com.example.socialconnect.controller.auth.AuthenticationRequest;
import com.example.socialconnect.controller.auth.AuthenticationResponse;
import com.example.socialconnect.controller.auth.RegisterRequest;
import com.example.socialconnect.controller.config.JwtService;
import com.example.socialconnect.repository.UserRepository;
import com.example.socialconnect.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .name(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))

                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken( user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
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
                .role(user.getRole())
                .build();
    }
}
