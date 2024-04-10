package com.scasmar.carregistry.service;

import com.scasmar.carregistry.entity.UserEntity;
import com.scasmar.carregistry.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
  CompletableFuture<List<User>> getAllUsers();
  User getUserByEmail(String email);
  UserEntity addUser(UserEntity newUser);
  User updateUser(String email, User user);
  void deleteUser(String email);
}
