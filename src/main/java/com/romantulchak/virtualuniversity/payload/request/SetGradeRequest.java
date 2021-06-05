package com.romantulchak.virtualuniversity.payload.request;

public class SetGradeRequest {
    private long gradeId;
    private double grade;

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

}
