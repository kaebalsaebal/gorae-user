package com.gorae.gorae_user.secret.hash;

public class SecureHashUtils {
    public static String hash(String message){
        return message;
    }

    public static boolean matches(String password, String hashedPassword){
        String hashed = hash(password);

        return hashed.equals(hashedPassword);
    }
}
