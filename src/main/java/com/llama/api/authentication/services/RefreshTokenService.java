package com.llama.api.authentication.services;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.llama.api.authentication.models.RefreshToken;
import com.llama.api.authentication.repository.RefreshTokenRepository;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.exceptions.TokenRefreshException;
import com.llama.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RefreshTokenService {
    @Value("${llama.app.jwtRefreshExpirationMs}")
    Long refreshTokenDurationMs;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserService userService;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String userID) throws ResourceNotFound {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userService.getUser(userID));
        refreshToken.setExpiryDate(
                Date.from(
                        new Date()
                                .toInstant()
                                .plusMillis(refreshTokenDurationMs)
                )
        );
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(new Date()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(String userID) throws ResourceNotFound {
        return refreshTokenRepository.deleteByUser(userService.getUser(userID));
    }
}