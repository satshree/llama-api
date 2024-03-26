package com.llama.api.users.repository;

import com.llama.api.users.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    public Optional<Users> findByUsername(String username);

//    public List<Users> findByFirstName(String firstName);
//
//    public List<Users> findByLastName(String lastName);

    public Optional<Users> findByEmail(String email);
}
