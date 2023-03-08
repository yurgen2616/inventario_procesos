package com.procesos.inventario.controllers;


import com.procesos.inventario.models.User;
import com.procesos.inventario.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping(value = "user/{id}")
    public User getUser(Long id){
        return userService.getUser(id);
    }


}
