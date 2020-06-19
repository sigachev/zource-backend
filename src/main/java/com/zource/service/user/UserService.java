package com.zource.service.user;

import com.zource.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    User updateUser(User user);

    User findByUsername(String username);

    User findById(Long id);

    User findByEmail(String email);

    List<User> findAllUsers();

}
