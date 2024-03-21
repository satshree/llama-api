package com.llama.api.users.serializer;

import com.llama.api.Utils;
import com.llama.api.users.models.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSerialized {
    String id;

    String first_name;

    String last_name;

    String username;

    String email;

    String address;

    String phone;

    String last_login;

    String date_joined;

    public static UserSerialized serialize(Users user) {
        return new UserSerialized(
                user.getId().toString(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getUserProfile().getAddress(),
                user.getUserProfile().getPhone(),
                Utils.parseDate(user.getLastLogin()),
                Utils.parseDate(user.getDateJoined())
        );
    }
}
