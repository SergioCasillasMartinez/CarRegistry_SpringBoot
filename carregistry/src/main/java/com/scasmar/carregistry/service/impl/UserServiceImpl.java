package com.scasmar.carregistry.service.impl;

import com.scasmar.carregistry.entity.UserEntity;
import com.scasmar.carregistry.respository.UserRepository;
import com.scasmar.carregistry.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

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
    public UserEntity addUser(UserEntity newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public void addUserImage(Long id, MultipartFile file) throws IOException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(RuntimeException::new);

        userEntity.setImage(Base64.getEncoder().encodeToString(file.getBytes()));

        userRepository.save(userEntity);
    }

    @Override
    public byte[] getUserImage(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(RuntimeException::new);

        return Base64.getDecoder().decode(userEntity.getImage());
    }
}
