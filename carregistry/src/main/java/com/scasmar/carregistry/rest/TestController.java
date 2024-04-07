package com.scasmar.carregistry.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController{

    @GetMapping("/noAuth")
    public String noAuth(){
        return "Everyone can see this";
    }
@GetMapping("/users")
    @PreAuthorize("hasRole('USER')")
    public String users(){
        return "Only Users can see this";
    }
    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")

    public String admins(){
        return "Only Admin can see this";
    }
    @GetMapping("/roles")
    @PreAuthorize("hasAnyRole('USER',  'ADMIN')")
    public String roles(){
        return "Users and Admins can see this";
    }
}
