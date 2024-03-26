package com.llama.api.authentication.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    String access;
    String refresh;
}
