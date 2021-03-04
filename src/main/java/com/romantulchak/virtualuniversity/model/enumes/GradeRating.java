package com.romantulchak.virtualuniversity.model.enumes;

import com.romantulchak.virtualuniversity.exception.GradeNotFoundException;

import java.util.Arrays;

public enum GradeRating {
    A(5), B(4.5), C(4), D(3.5), E(3) ,F(2);
    private final double value;
    GradeRating(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public static GradeRating getByValue(double value){
        return Arrays.stream(GradeRating.values())
                .filter(x->x.getValue() == value)
                .findFirst()
                .orElseThrow(GradeNotFoundException::new);
    }
}
