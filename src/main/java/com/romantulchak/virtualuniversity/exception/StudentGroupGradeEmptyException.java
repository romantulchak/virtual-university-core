package com.romantulchak.virtualuniversity.exception;

public class StudentGroupGradeEmptyException extends RuntimeException {
    public StudentGroupGradeEmptyException(String msg) {
        super(String.format("Student grades is empty %s", msg));
    }
}
