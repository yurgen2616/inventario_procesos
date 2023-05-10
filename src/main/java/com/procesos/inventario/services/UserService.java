package com.procesos.inventario.services;

import com.procesos.inventario.models.User;

import java.util.List;

public interface UserService {

    User getUser(Long id);
    Boolean createUser(User user);
    List<User> allUser();
    Boolean updateUser(Long id, User user);
    String login(User user);
}
