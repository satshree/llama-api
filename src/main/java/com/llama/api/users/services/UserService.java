package com.llama.api.users.services;

import com.llama.api.users.UserUtils;
import com.llama.api.users.dto.UserDTO;
import com.llama.api.users.models.Users;
import com.llama.api.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUser(String id) {
        return userRepository.findById(UUID.fromString(id)).orElseThrow(
                // implement later
        );
    }

    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Users addUser(UserDTO user) {
        Users userModel = new Users();

        BeanUtils.copyProperties(user, userModel);
        return userRepository.save(userModel);
    }

    public Users updateUser(String id, UserDTO user) {
        Users userModel = userRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        // implement later
                );

        BeanUtils.copyProperties(user, userModel);
        return userRepository.save(userModel);
    }

    public void setPassword(String id, String password) {
        Users user = userRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        // implement later
                );

        user.setPassword(UserUtils.hashPassword(password));

        userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }
}
