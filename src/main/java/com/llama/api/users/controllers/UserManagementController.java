package com.llama.api.users.controllers;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.users.dto.UserDTO;
import com.llama.api.users.dto.UserProfileDTO;
import com.llama.api.users.models.Users;
import com.llama.api.users.requests.UserManagementRequest;
import com.llama.api.users.serializer.UserSerialized;
import com.llama.api.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/management/user")
public class UserManagementController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<List<UserSerialized>> getUsers() {
        return ResponseEntity.ok(userService.getAllUserSerialized());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<UserSerialized> getUser(@PathVariable("id") String id) throws ResourceNotFound {
        return ResponseEntity.ok(userService.getUserSerialized(id));
    }

    @PostMapping("/")
    public ResponseEntity<UserSerialized> createUser(@Valid @RequestBody UserManagementRequest userRequest) {
        UserDTO userDTO = new UserDTO(
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getUsername(),
                userRequest.getEmail()
        );

        UserProfileDTO userProfileDTO = new UserProfileDTO(
                userRequest.getAddress(),
                userRequest.getPhone()
        );

        Users user;
        if (userRequest.getIsSuper()) {
            user = userService.addSuperUser(
                    userDTO,
                    userProfileDTO,
                    userRequest.getPassword()
            );
        } else {
            user = userService.addUser(
                    userDTO,
                    userProfileDTO,
                    userRequest.getPassword()
            );
        }

        if (userRequest.getPassword() != null) {
            user.setPassword(
                    passwordEncoder.encode(
                            userRequest.getPassword()
                    )
            );
        }

        return ResponseEntity.ok(UserSerialized.serialize(user));
    }

    @PutMapping("/{id}/")
    public ResponseEntity<UserSerialized> updateUser(@PathVariable("id") String id, @Valid @RequestBody UserManagementRequest userRequest) throws ResourceNotFound {
        UserDTO userDTO = new UserDTO(
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getUsername(),
                userRequest.getEmail()
        );

        UserProfileDTO userProfileDTO = new UserProfileDTO(
                userRequest.getAddress(),
                userRequest.getPhone()
        );

        Users user = userService.updateUser(id, userDTO, userProfileDTO);

        if (userRequest.getPassword() != null) {
            user.setPassword(
                    passwordEncoder.encode(
                            userRequest.getPassword()
                    )
            );
        }

        return ResponseEntity.ok(UserSerialized.serialize(user));
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted");

        return ResponseEntity.ok(response);
    }
}
