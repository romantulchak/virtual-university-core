package com.romantulchak.virtualuniversity.utils;

import java.util.Locale;
import java.util.UUID;

public final class PasswordGeneratorUtil {

    public static String generate(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }

}
