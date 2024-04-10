package com.scasmar.carregistry.controller.mapper;

import com.scasmar.carregistry.controller.dto.UserRequest;
import com.scasmar.carregistry.controller.dto.UserResponse;
import com.scasmar.carregistry.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toModel(UserRequest model){
        User user = new User();

        user.setName(model.getName());
        user.setSurName(model.getSurName());
        user.setEmail(model.getEmail());
        user.setPassword(model.getPassword());
        user.setRole(model.getRole());

        return user;
    }

    public UserResponse toResponse(User entity){
        UserResponse userResponse = new UserResponse();

        userResponse.setName(entity.getName());
        userResponse.setSurName(entity.getSurName());
        userResponse.setEmail(entity.getEmail());

        return userResponse;
    }
}
