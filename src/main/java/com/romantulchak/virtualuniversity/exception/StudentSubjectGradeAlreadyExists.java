package com.romantulchak.virtualuniversity.exception;

public class StudentSubjectGradeAlreadyExists extends RuntimeException{
    public StudentSubjectGradeAlreadyExists(long id){
        super(String.format("Student grade for subject with id: %d not found", id));
    }
}
