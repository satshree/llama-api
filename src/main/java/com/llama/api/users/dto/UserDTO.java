package com.llama.api.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    String username;

    String email;
}
