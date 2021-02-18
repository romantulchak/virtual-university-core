package com.romantulchak.virtualuniversity.exception;

public class TeacherWithSameLoginAlreadyExists extends RuntimeException{

    public TeacherWithSameLoginAlreadyExists(String login){
        super(String.format("Teacher with login %s already exists", login));
    }
}
