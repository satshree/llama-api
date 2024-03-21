package com.llama.api.authentication.controllers;

import com.llama.api.authentication.jwt.JwtUtils;
import com.llama.api.authentication.requests.LoginRequest;
import com.llama.api.authentication.requests.RefreshRequest;
import com.llama.api.authentication.requests.RegisterRequest;
import com.llama.api.authentication.responses.JwtResponse;
import com.llama.api.authentication.responses.TokenResponse;
import com.llama.api.authentication.services.RefreshTokenService;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.exceptions.TokenRefreshException;
import com.llama.api.users.dto.UserDTO;
import com.llama.api.users.dto.UserProfileDTO;
import com.llama.api.users.models.Users;
import com.llama.api.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@CrossOrigin(origins = "*", maxAge = 3600) // change later to only accept from frontend applications
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/login/")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) throws ResourceNotFound {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Users user = (Users) authentication.getPrincipal();

        String refreshToken = refreshTokenService.createRefreshToken(user.getId().toString());

        userService.updateLastLogin(user.getId().toString());

        return ResponseEntity
                .ok(new JwtResponse(
                                jwt,
                                refreshToken,
                                "Bearer",
                                user.getId(),
                                user.getUsername()
                        )
                );
    }

    @PostMapping("/refresh/")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        if (requestRefreshToken != null && jwtUtils.validateJwtToken(requestRefreshToken)) {
            // REFRESH TOKEN IS VERIFIED

            String username = jwtUtils.getUserNameFromJwtToken(requestRefreshToken);
            String newAccessToken = jwtUtils.generateTokenFromUsername(username);

            return ResponseEntity.ok(new TokenResponse(newAccessToken, requestRefreshToken));
        }


        throw new TokenRefreshException(requestRefreshToken, "Cannot verify refresh token");
    }

    @PostMapping("/register/")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) throws ResourceNotFound {
        // CHECK USERNAME
        if (userService
                .usernameExists(
                        registerRequest.getUsername()
                )
        ) {
            throw new RuntimeException("User '" + registerRequest.getUsername() + "' already exists");
        }

        // CHECK EMAIL
        if (userService
                .emailExists(
                        registerRequest.getEmail()
                )
        ) {
            throw new RuntimeException("User '" + registerRequest.getEmail() + "' already exists");
        }

        // CREATE USER PROFILE
        UserDTO user = new UserDTO();
        UserProfileDTO profile = new UserProfileDTO();

        BeanUtils.copyProperties(registerRequest, user);
        BeanUtils.copyProperties(registerRequest, profile);

        Users userModel = userService.addUser(
                user,
                profile,
                passwordEncoder.encode(
                        registerRequest.getPassword()
                )
        );

        Map<String, Object> response = new HashMap<>();

        response.put("message", "User registered");
        response.put("user", userService.getUserSerialized(userModel.getId().toString()));

        return ResponseEntity.ok(response);
    }
}
