package com.romantulchak.virtualuniversity.exception;

public class SpecializationIsNullException extends RuntimeException{
    public SpecializationIsNullException(){
        super("Specialization cannot be null");
    }
}
