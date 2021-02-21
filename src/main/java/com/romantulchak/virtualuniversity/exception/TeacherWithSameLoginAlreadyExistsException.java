package com.romantulchak.virtualuniversity.exception;

public class TeacherWithSameLoginAlreadyExistsException extends RuntimeException{

    public TeacherWithSameLoginAlreadyExistsException(String login){
        super(String.format("Teacher with login %s already exists", login));
    }
}
