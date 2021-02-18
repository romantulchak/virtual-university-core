package com.romantulchak.virtualuniversity.exception;

public class StudentWithSameLoginAlreadyExists extends RuntimeException{

    public StudentWithSameLoginAlreadyExists(String login){
        super(String.format("Student with login %s already exists", login));
    }
}
