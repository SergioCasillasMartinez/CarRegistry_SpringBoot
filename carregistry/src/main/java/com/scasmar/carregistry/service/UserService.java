package com.scasmar.carregistry.service;

import com.scasmar.carregistry.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

  UserEntity addUser(UserEntity newUser);
  void addUserImage(Long id, MultipartFile file) throws IOException;
  byte[] getUserImage(Long id);
}
