package com.romantulchak.virtualuniversity.exception;

public class StudentGradeNotFoundException extends RuntimeException{
    public StudentGradeNotFoundException(){
        super("Student subject grade not found");
    }
}
