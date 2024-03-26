package com.llama.api.users.serializer;

import com.llama.api.users.models.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserSerialized {
    String id;
    String username;

    public static SimpleUserSerialized serialize(Users user) {
        return new SimpleUserSerialized(
                user.getId().toString(),
                user.getUsername()
        );
    }
}
