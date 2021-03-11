package com.romantulchak.virtualuniversity.exception;

public class SpecializationNotFoundException extends RuntimeException {
    public SpecializationNotFoundException(long specializationId) {
        super(String.format("Specialization with id not found"));
    }
}
