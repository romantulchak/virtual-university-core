package com.romantulchak.virtualuniversity.exception;

public class GradeNotFoundException extends RuntimeException{

    public GradeNotFoundException(){
        super("Grade not found");
    }

}
