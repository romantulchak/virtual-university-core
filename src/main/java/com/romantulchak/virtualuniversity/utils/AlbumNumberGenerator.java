package com.romantulchak.virtualuniversity.utils;

import java.security.SecureRandom;

public class AlbumNumberGenerator {

    public static String generateAlbumNumber(){
        SecureRandom secureRandom = new SecureRandom();
        int number = secureRandom.nextInt(99999);
        return String.format("%05d", number);
    }

}
