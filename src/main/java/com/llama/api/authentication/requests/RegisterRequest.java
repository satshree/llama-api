package com.llama.api.authentication.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    String username;
    String password;
    String email;
    String address;
    String phone;
}
