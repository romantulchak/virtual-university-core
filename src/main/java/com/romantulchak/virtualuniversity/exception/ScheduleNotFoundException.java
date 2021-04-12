package com.romantulchak.virtualuniversity.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(){
        super("Schedule not found");
    }
}
