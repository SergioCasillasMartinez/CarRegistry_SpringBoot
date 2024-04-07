package com.scasmar.carregistry.service.impl;

import com.scasmar.carregistry.dto.LoginRequest;
import com.scasmar.carregistry.dto.LoginResponse;
import com.scasmar.carregistry.dto.SingUpRequest;
import com.scasmar.carregistry.entity.UserEntity;
import com.scasmar.carregistry.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse signup(SingUpRequest request) throws BadRequestException {
        var user = UserEntity
                .builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();

        user = userService.addUser(user);
        var jwt = jwtService.generateToken(user);
        return LoginResponse.builder().jwt(jwt).build();
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUser(), request.getPassword()));
        var user = userRepository.findByEmail(request.getUser()).orElseThrow(() -> new IllegalArgumentException("Invalid user or password"));

        var jwt = jwtService.generateToken(user);
        return LoginResponse.builder().jwt(jwt).build();
    }

}
