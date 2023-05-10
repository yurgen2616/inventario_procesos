package com.procesos.inventario.controllers;

import com.procesos.inventario.models.User;
import com.procesos.inventario.services.UserService;
import com.procesos.inventario.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.procesos.inventario.utils.Constants.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    private ApiResponse apiResponse;

    @GetMapping("/{id}")
    public ResponseEntity findUserById(@PathVariable Long id) {
        try {
            apiResponse = new ApiResponse(REGISTER_FOUND, userService.getUser(id));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(REGISTER_NOT_FOUND, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity saveUser(@RequestBody User user) {
        if (!userService.createUser(user)) {
            apiResponse = new ApiResponse(REGISTER_BAD, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        apiResponse = new ApiResponse(REGISTER_CREATED, "");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity getAllUser() {
        List<User> registeredUsers = userService.allUser();
        if (registeredUsers.isEmpty()) {
            apiResponse = new ApiResponse(REGISTER_EMPTY, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        apiResponse = new ApiResponse(REGISTER_LIST, registeredUsers);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!userService.updateUser(id, user)) {
            apiResponse = new ApiResponse(REGISTER_DATA_ERROR, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.PAYMENT_REQUIRED);
        }
        apiResponse = new ApiResponse(REGISTER_UPDATED, "");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
