package com.scasmar.carregistry.controller.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String surName;
    private String email;
    private String password;
    private String role;
}
