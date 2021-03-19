package com.romantulchak.virtualuniversity.exception;

public class CourseWithNameAlreadyExists extends RuntimeException {
    public CourseWithNameAlreadyExists(String name) {
        super(String.format("Course with name %s already exists", name));
    }
}
