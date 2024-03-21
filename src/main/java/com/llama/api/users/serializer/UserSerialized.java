package com.llama.api.users.serializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.llama.api.users.models.UserProfile;
import com.llama.api.users.models.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public static UserSerialized serialize(Users user) {
        return new UserSerialized(
                user.getId().toString(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getUserProfile().getAddress(),
                user.getUserProfile().getPhone(),
                user.getLastLogin(),
                user.getDateJoined()
        );
    }
}
