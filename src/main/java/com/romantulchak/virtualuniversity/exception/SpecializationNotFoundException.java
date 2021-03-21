package com.romantulchak.virtualuniversity.exception;

import com.romantulchak.virtualuniversity.model.Specialization;

public class SpecializationNotFoundException extends RuntimeException {
    public SpecializationNotFoundException(long specializationId) {
        super(String.format("Specialization with id %d not found", specializationId));
    }
    public SpecializationNotFoundException(){
        super("Specialization not found");
    }
}
