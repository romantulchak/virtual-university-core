package com.romantulchak.virtualuniversity.exception;

public class TimeNotCorrectException extends RuntimeException {
    public TimeNotCorrectException(){
        super("The time is set incorrectly");
    }
}
