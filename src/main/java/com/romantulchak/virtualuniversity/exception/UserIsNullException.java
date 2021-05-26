package com.romantulchak.virtualuniversity.exception;

public class UserIsNullException extends RuntimeException {
    public UserIsNullException(){
        super("User is null");
    }
}
