package com.llama.api.authentication.repository;

import com.llama.api.authentication.models.RefreshToken;
import com.llama.api.users.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    Integer deleteByUser(Users user);
}
