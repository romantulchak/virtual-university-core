package com.romantulchak.virtualuniversity.utils;

import com.romantulchak.virtualuniversity.exception.CannotConvertStringToLocalDate;

import java.time.LocalDate;

public final class DateParserUtility {

    public static LocalDate parseStringToDate(String dateAsString){
        try {
            return LocalDate.parse(dateAsString);
        }catch (Exception e){
            throw new CannotConvertStringToLocalDate(dateAsString);
        }
    }

}
