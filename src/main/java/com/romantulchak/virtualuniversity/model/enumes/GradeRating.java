package com.romantulchak.virtualuniversity.model.enumes;

public enum GradeRating {
    A(5), B(4.5), C(4), D(3.5), E(3) ,F(2);
    private double grade;
    GradeRating(double grade){
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

}
