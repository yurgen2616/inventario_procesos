package com.procesos.inventario.services;

import com.procesos.inventario.models.User;
import com.procesos.inventario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(Long id){
        return userRepository.findById(id).get();
    }
}
