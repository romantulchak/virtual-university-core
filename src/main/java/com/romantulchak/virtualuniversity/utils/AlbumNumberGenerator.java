package com.romantulchak.virtualuniversity.utils;

import java.security.SecureRandom;

public class AlbumNumberGenerator {

    public static String generateAlbumNumber(String firstName, String lastName){
        SecureRandom secureRandom = new SecureRandom();
        int number = secureRandom.nextInt(99999);
        return firstName.charAt(0) + lastName.charAt(0) + String.format("%05d", number);
    }

}
