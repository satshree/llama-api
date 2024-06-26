package com.llama.api.authentication.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class JwtResponse {
    String access;
    String refresh;
    String type = "Bearer";
    UUID id;
    String username;
    Boolean isSuper;
}
