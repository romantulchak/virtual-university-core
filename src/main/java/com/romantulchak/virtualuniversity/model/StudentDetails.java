package com.romantulchak.virtualuniversity.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
public class StudentDetails {


    @NotBlank(message = "Passport is required")
    @JsonView(Views.StudentView.class)
    private String passport;


    @NotBlank(message = "Citizen is required")
    @JsonView(Views.StudentView.class)
    private String citizen;
    @JsonView(Views.StudentView.class)
    private LocalDate birthDay;

    public StudentDetails(){}


    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }
}
