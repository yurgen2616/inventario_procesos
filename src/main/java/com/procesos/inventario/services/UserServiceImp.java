package com.procesos.inventario.services;

import com.procesos.inventario.models.User;
import com.procesos.inventario.repository.UserRepository;
import com.procesos.inventario.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Boolean createUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<User> allUser() {
        return userRepository.findAll();
    }

    @Override
    public Boolean updateUser(Long id, User user) {
        try {
            User userBD = userRepository.findById(id).get();
            userBD.setFirstName(user.getFirstName());
            userBD.setLastName(user.getLastName());
            userBD.setBirthday(user.getBirthday());
            userBD.setAddress(user.getAddress());
            userRepository.save(userBD);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String login(User user) {
        Optional<User> userBd = userRepository.findByEmail(user.getEmail());
        if (userBd.isEmpty()){
            throw new RuntimeException("El correo o el usuario no existe!");
        }
        if (!userBd.get().getPassword().equals(user.getPassword())) {
            throw new RuntimeException("La contraseña es incorrecta");
        }
        return jwtUtil.create(String.valueOf(userBd.get().getId()),
                String.valueOf(userBd.get().getEmail()));
    }


}
