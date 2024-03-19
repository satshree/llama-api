package com.llama.api.users;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserUtils {
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            return new String(hash, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            //
        }

        return "";
    }
}
