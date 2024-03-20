package com.llama.api.users.serializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserSerialized {
    String id;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    String username;

    String email;

    String address;

    String phone;

    @JsonProperty("last_login")
    Date lastLogin;

    @JsonProperty("date_joined")
    Date dateJoined;
}
