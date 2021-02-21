package com.romantulchak.virtualuniversity.exception;

public class StudentWithSameLoginAlreadyExistsException extends RuntimeException{

    public StudentWithSameLoginAlreadyExistsException(String login){
        super(String.format("Student with login %s already exists", login));
    }
}
