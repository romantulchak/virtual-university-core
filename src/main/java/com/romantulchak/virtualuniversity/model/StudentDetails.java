package com.romantulchak.virtualuniversity.model;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class StudentDetails {

    private String passport;

    private String citizen;

    private LocalDateTime birthDay;

    public StudentDetails(){}

    public StudentDetails(String passport, String citizen, LocalDateTime birthDay){
        this.passport = passport;
        this.citizen = citizen;
        this.birthDay = birthDay;
    }

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

    public LocalDateTime getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay;
    }

}
