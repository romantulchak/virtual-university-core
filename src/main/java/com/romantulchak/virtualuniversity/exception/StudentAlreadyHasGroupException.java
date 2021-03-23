package com.romantulchak.virtualuniversity.exception;

import com.romantulchak.virtualuniversity.model.Student;

import java.util.List;

public class StudentAlreadyHasGroupException extends RuntimeException {
    public StudentAlreadyHasGroupException(List<Student> students) {
        super(String.format("Students: %s already has group",students.toString()));
    }
}
