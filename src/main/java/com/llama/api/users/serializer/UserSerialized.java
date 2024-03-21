package com.llama.api.users.serializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.llama.api.users.models.UserProfile;
import com.llama.api.users.models.Users;
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

    public static UserSerialized serialize(Users user) {
        UserSerialized userSerialized = new UserSerialized();

        userSerialized.setId(user.getId().toString());
        userSerialized.setFirstName(user.getFirstName());
        userSerialized.setLastName(user.getLastName());
        userSerialized.setUsername(user.getUsername());
        userSerialized.setEmail(user.getEmail());
        userSerialized.setLastLogin(user.getLastLogin());
        userSerialized.setDateJoined(user.getDateJoined());

        userSerialized.setAddress(user.getUserProfile().getAddress());
        userSerialized.setPhone(user.getUserProfile().getPhone());

        return userSerialized;
    }
}
