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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

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
        System.out.println("HELLO " + request.getRefreshToken());
        String requestRefreshToken = request.getRefreshToken();

        if (requestRefreshToken != null && jwtUtils.validateJwtToken(requestRefreshToken)) {
            // REFRESH TOKEN IS VERIFIED

            String username = jwtUtils.getUserNameFromJwtToken(requestRefreshToken);
            String newAccessToken = jwtUtils.generateTokenFromUsername(username);

            return ResponseEntity.ok(new TokenResponse(newAccessToken, requestRefreshToken));
        }


        throw new TokenRefreshException(requestRefreshToken, "Cannot verify refresh token");
    }

//    @PostMapping("/signout")
//    public ResponseEntity<?> logoutUser() throws ResourceNotFound {
//        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UUID userId = user.getId();
//        refreshTokenService.deleteByUserId(userId.toString());
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Log out successful");
//
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/register/")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) throws ResourceNotFound {
        // CHECK USERNAME, make this logic better later
        try {
            userService.getUserByUsername(registerRequest.getUsername());
            throw new RuntimeException("User '" + registerRequest.getUsername() + "' already exists");
        } catch (UsernameNotFoundException e) {
            // NO SUCH USER EXISTS, CONTINUE
        }

        // CHECK EMAIL, make this logic better later
        try {
            userService.getUserByEmail(registerRequest.getEmail());
            throw new RuntimeException("User '" + registerRequest.getEmail() + "' already exists");
        } catch (ResourceNotFound e) {
            // NO SUCH USER EXISTS, CONTINUE
        }

        // CREATE USER PROFILE
        UserDTO user = new UserDTO();
        UserProfileDTO profile = new UserProfileDTO();

        BeanUtils.copyProperties(registerRequest, user);
        BeanUtils.copyProperties(registerRequest, profile);

        Users userModel = userService.addUser(user, profile);

        // SET PASSWORD
        userService.setPassword(
                userModel.getId().toString(),
                passwordEncoder.encode(registerRequest.getPassword()
                )
        );

        Map<String, Object> response = new HashMap<>();

        response.put("message", "User registered");
        response.put("user", userService.getUserSerialized(userModel.getId().toString()));

        return ResponseEntity.ok(response);
    }
}
