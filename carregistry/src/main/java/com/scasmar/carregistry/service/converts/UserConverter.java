package com.scasmar.carregistry.service.converts;

import com.scasmar.carregistry.entity.UserEntity;
import com.scasmar.carregistry.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User toUser(UserEntity entity){
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setSurName(entity.getSurname());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setRole(entity.getRole());

        return user;
    }

    public UserEntity toEntity(User entity){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(entity.getId());
        userEntity.setName(entity.getName());
        userEntity.setSurname(entity.getSurName());
        userEntity.setEmail(entity.getEmail());
        userEntity.setPassword(entity.getPassword());
        userEntity.setRole(entity.getRole());

        return userEntity;
    }
}
