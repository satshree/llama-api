package com.llama.api.users.controllers;

import com.llama.api.users.dto.UserDTO;
import com.llama.api.users.dto.UserProfileDTO;
import com.llama.api.users.models.Users;
import com.llama.api.users.requests.PasswordRequest;
import com.llama.api.users.requests.UserRequest;
import com.llama.api.users.serializer.UserSerialized;
import com.llama.api.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<UserSerialized> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(UserSerialized.serialize(userService.getUserByUsername(username)));
    }

    @PostMapping("/")
    public ResponseEntity<UserSerialized> updateUser(@Valid @RequestBody UserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users user = userService.getUserByUsername(username);

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

        user = userService.updateUser(user, userDTO, userProfileDTO);

        return ResponseEntity.ok(UserSerialized.serialize(user));
    }

    @PostMapping("/password/")
    public ResponseEntity<Map<String, String>> setPassword(@Valid @RequestBody PasswordRequest passwordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users user = userService.getUserByUsername(username);

        user.setPassword(
                passwordEncoder.encode(passwordRequest.getPassword())
        );

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password updated");

        return ResponseEntity.ok(response);
    }
}
