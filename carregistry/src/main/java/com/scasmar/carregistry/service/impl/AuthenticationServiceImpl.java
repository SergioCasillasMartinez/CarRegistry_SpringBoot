package com.scasmar.carregistry.service.impl;

import com.scasmar.carregistry.controller.dto.LoginRequest;
import com.scasmar.carregistry.controller.dto.JwtResponse;
import com.scasmar.carregistry.controller.dto.SingUpRequest;
import com.scasmar.carregistry.entity.UserEntity;
import com.scasmar.carregistry.respository.UserRepository;
import com.scasmar.carregistry.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse signup(SingUpRequest request) throws BadRequestException {
        var user = UserEntity
                .builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_CLIENT")
                .build();

        user = userService.addUser(user);
        var jwt = jwtService.generateToken(user);
        return JwtResponse.builder().jwt(jwt).build();
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUser(), request.getPassword()));
        var user = userRepository.findByEmail(request.getUser()).orElseThrow(() -> new IllegalArgumentException("Invalid user or password"));

        var jwt = jwtService.generateToken(user);
        return JwtResponse.builder().jwt(jwt).build();
    }

}
