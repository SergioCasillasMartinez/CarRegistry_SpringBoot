package com.scasmar.carregistry.service.impl;

import com.scasmar.carregistry.entity.UserEntity;
import com.scasmar.carregistry.model.User;
import com.scasmar.carregistry.respository.UserRepository;
import com.scasmar.carregistry.service.UserService;
import com.scasmar.carregistry.service.converts.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public CompletableFuture<List<User>> getAllUsers() {
        List<UserEntity> userEntitiesList = userRepository.findAll();
        List<User> userList = userEntitiesList.stream().map(userConverter::toUser).toList();

        return CompletableFuture.completedFuture(userList);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        return userEntityOptional.map(userConverter::toUser).orElse(null);
    }

    @Override
    public UserEntity addUser(UserEntity newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(String email, User user) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(user.getEmail());
        if (userEntityOptional.isPresent()){
            UserEntity userEntity = userConverter.toEntity(user);
            userEntity.setId(user.getId());
            userEntity.setRole(user.getRole());

            return userConverter.toUser(userRepository.save(userEntity));
        } else {
            return null;
        }
    }

    @Override
    public void deleteUser(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        userEntityOptional.ifPresent(entity -> userRepository.deleteById(entity.getId()));
    }
}