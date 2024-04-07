package com.scasmar.carregistry.service;

import com.scasmar.carregistry.controller.dto.LoginRequest;
import com.scasmar.carregistry.controller.dto.JwtResponse;
import com.scasmar.carregistry.controller.dto.SingUpRequest;
import org.apache.coyote.BadRequestException;

public interface AuthenticationService {

    JwtResponse signup(SingUpRequest request) throws BadRequestException;
    JwtResponse login(LoginRequest request);
}
