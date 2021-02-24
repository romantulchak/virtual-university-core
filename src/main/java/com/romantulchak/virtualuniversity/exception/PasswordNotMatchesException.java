package com.romantulchak.virtualuniversity.exception;

public class PasswordNotMatchesException extends RuntimeException{
    public PasswordNotMatchesException() {
        super("Password mismatch");
    }
}
