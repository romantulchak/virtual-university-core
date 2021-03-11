package com.romantulchak.virtualuniversity.exception;

public class SpecializationSemesterAlreadyExists extends RuntimeException {
    public SpecializationSemesterAlreadyExists(String semesterName, String specializationName) {
        super(String.format("Specialization %s already has a semester %s", specializationName, semesterName));
    }
}
