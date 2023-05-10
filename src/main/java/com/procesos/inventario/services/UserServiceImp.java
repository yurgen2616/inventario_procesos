package com.procesos.inventario.services;
import com.procesos.inventario.models.User;
import com.procesos.inventario.repository.UserRepository;
import com.procesos.inventario.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.procesos.inventario.utils.Constants.PASSWORD_INCORRECT;
import static com.procesos.inventario.utils.Constants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JWTUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Boolean createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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
            userBD.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(userBD);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String login(User user) {
        Optional<User> userBd = userRepository.findByEmail(user.getEmail());
        if(userBd.isEmpty()){
            throw new RuntimeException(USER_NOT_FOUND);
        }
        if(!passwordEncoder.matches(user.getPassword(),userBd.get().getPassword())){
            throw new RuntimeException(PASSWORD_INCORRECT);
        }
        return jwtUtil.create(String.valueOf(userBd.get().getId()),
                user.getEmail());
    }


}
