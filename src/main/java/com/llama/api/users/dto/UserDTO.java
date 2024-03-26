package com.llama.api.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    String username;

    String email;
}
