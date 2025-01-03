package com.example.assignment.util;

import java.util.UUID;

public class StringUtil {
    public static String generateRandomStringId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, Math.min(100, uuid.length()));
    }

    public static boolean isValidName(String inputString) {
        final String regex = "^[A-Za-z0-9-_]+$";
        return inputString.matches(regex);
    }

    public static boolean isValidCode(String input) {
        final String regex = "^[A-Z0-9_]+$";
        return input.matches(regex);
    }
}
