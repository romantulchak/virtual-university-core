package com.romantulchak.virtualuniversity.exception;

public class ScheduleIsNullException extends RuntimeException {
    public ScheduleIsNullException(){
        super("Schedule is null");
    }
}
