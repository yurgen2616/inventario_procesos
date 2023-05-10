package com.procesos.inventario.controllers;
import com.procesos.inventario.models.User;
import com.procesos.inventario.services.UserService;
import com.procesos.inventario.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.procesos.inventario.utils.Constants.USER_LOGIN;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    private ApiResponse apiResponse;
    Map data = new HashMap<>();

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        try {
            data.put("Token", userService.login(user));
            apiResponse = new ApiResponse(USER_LOGIN, data);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(e.getMessage(),"");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }
}