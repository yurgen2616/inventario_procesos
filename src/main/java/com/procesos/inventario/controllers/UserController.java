package com.procesos.inventario.controllers;


import com.procesos.inventario.models.User;
import com.procesos.inventario.services.UserService;
import com.procesos.inventario.services.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserServiceImp userServiceImp;

    @GetMapping("/{id}")
    public ResponseEntity findUserById(@PathVariable Long id) {
        Map response = new HashMap();
        try {
            return new ResponseEntity(userServiceImp.getUser(id),HttpStatus.OK);
        }catch (Exception e){
            response.put("Status","404");
            response.put("Message","No se encontro el usuario!");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("")
    public ResponseEntity<Void> saveUser(@RequestBody User user) {
        Map response = new HashMap();
        boolean userResp = userServiceImp.createUser(user);

        if (userResp == true){
            response.put("Status","201");
            response.put("message","Se creo el usuario");
            return new ResponseEntity(response,HttpStatus.CREATED);
        }
        response.put("Status","400");
        response.put("message","Hubo un error al crear el usuario");
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
//        if (!userServiceImp.createUser(user)) {
//        response.put("Status","400");
//        response.put("message","Hubo un error al crear el usuario");
//        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
//        }
//        response.put("Status","201");
//        response.put("message","Se creo el usuario");
//        return new ResponseEntity(response,HttpStatus.CREATED);

    }

    @GetMapping("")
    public ResponseEntity getAllUser() {
        Map response = new HashMap();
        try {
        return ResponseEntity.ok(userServiceImp.allUser());
        }catch (Exception e){
            response.put("Status","204");
            response.put("Message","No se encontraron los usuarios!");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user){
        Map response = new HashMap();
        if (!userServiceImp.updateUser(id,user)) {
            response.put("Status","400");
            response.put("message","Hubo un error al actualizar el usuario");
            return new ResponseEntity<>(response,HttpStatus.PAYMENT_REQUIRED);
        }
        response.put("Status","201");
        response.put("message","Se actualizo el usuario");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
