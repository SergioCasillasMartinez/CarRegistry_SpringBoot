package com.scasmar.carregistry.controller;

import com.scasmar.carregistry.controller.dto.UserRequest;
import com.scasmar.carregistry.controller.dto.UserResponse;
import com.scasmar.carregistry.controller.mapper.UserMapper;
import com.scasmar.carregistry.model.User;
import com.scasmar.carregistry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('VENDOR')")
    public CompletableFuture<?> getAllUsers(){
        try {
            CompletableFuture<List<User>> userList = userService.getAllUsers();
            List<UserResponse> userResponseList = userList.get().stream().map(userMapper::toResponse).toList();

            return CompletableFuture.completedFuture(ResponseEntity.ok().body(userResponseList));
        } catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }

    @PutMapping("/updateRole/{email}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> updateRole(@PathVariable String email, @RequestBody UserRequest userRequest){
        try{
            userService.updateUser(email, userMapper.toModel(userRequest));

            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{email}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> deleteUser(@PathVariable String email, @RequestBody UserRequest userRequest){
        try{
            userService.deleteUser(email);

            return ResponseEntity.ok().body("Deleted User with email: " + email);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
