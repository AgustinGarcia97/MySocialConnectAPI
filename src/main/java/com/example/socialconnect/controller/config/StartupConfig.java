package com.example.socialconnect.controller.config;


import com.example.socialconnect.model.Role;
import com.example.socialconnect.model.User;
import com.example.socialconnect.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Data
public class StartupConfig {

    private final UserRepository userRepository;


    @Autowired
    public StartupConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Bean
    CommandLineRunner initDatabase( ) {

        return args -> {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String adminEmail = "admin@mail.com";


            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User user = User.builder()
                        .name("Admin")
                        .lastname("Admin")
                        .username("ElAdmin")
                        .password(passwordEncoder.encode("123456"))
                        .email(adminEmail)
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
