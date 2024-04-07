package com.scasmar.carregistry.controller;

import com.scasmar.carregistry.controller.dto.LoginRequest;
import com.scasmar.carregistry.controller.dto.SingUpRequest;
import com.scasmar.carregistry.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<?> singup(@RequestBody SingUpRequest request) {
       try {
           return ResponseEntity.ok(authenticationServiceImpl.signup(request));
       }
       catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationServiceImpl.login(request));
    }
}
