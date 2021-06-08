package com.romantulchak.virtualuniversity.exception;

public class ScheduleDayNotFoundException extends RuntimeException {
    public ScheduleDayNotFoundException(long dayId) {
        super(String.format("Day in schedule with id %d not found", dayId));
    }

    public ScheduleDayNotFoundException() {
        super("Day in schedule with id not found");
    }
}
