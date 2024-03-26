package com.llama.api.authentication.services;

//import java.util.Date;
//import java.util.Optional;
//import java.util.UUID;

import com.llama.api.authentication.jwt.JwtUtils;
//import com.llama.api.authentication.repository.RefreshTokenRepository;
import com.llama.api.exceptions.ResourceNotFound;
//import com.llama.api.exceptions.TokenRefreshException;
import com.llama.api.users.models.Users;
import com.llama.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;


@Service
public class RefreshTokenService {
//    @Value("${llama.app.jwtRefreshExpirationMs}")
//    Long refreshTokenDurationMs;
//
//    @Autowired
//    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

//    public Optional<RefreshToken> findByToken(String token) {
//        return refreshTokenRepository.findByToken(token);
//    }

    public String createRefreshToken(String userID) throws ResourceNotFound {
        // ONLY THIS METHOD IS REQUIRED SINCE THERE IS NO REQUIREMENT FOR REFRESH TOKEN MODEL

        Users user = userService.getUser(userID);

        return jwtUtils.generateRefreshTokenFromUsername(user.getUsername());
    }

//    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.getExpiryDate().compareTo(new Date()) < 0) {
//            refreshTokenRepository.delete(token);
//            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new login request");
//        }
//
//        return token;
//    }

//    @Transactional
//    public int deleteByUserId(String userID) throws ResourceNotFound {
//        return refreshTokenRepository.deleteByUser(userService.getUser(userID));
//    }
}