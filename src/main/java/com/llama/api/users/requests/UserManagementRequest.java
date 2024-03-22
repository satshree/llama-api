package com.llama.api.users.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementRequest {
    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    String username;

    String email;

    String password = null;

    @JsonProperty("is_super")
    Boolean isSuper;

    String address;

    String phone;
}
