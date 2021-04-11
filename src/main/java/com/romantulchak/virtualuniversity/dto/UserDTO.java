package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.StudentDetails;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.model.enumes.Gender;
import com.romantulchak.virtualuniversity.model.enumes.StudentStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Comparator;

public abstract class UserDTO implements Comparable<UserDTO>{
    @JsonView({Views.SemesterView.class, Views.StudentView.class,Views.TeacherStudentGrades.class, Views.StudentGroupView.class})
    private long id;

    @JsonView({Views.StudentView.class,Views.SemesterView.class,Views.StudentGrades.class,Views.TeacherStudentGrades.class, Views.StudentGroupView.class})
    private String firstName;

    @JsonView({Views.StudentView.class, Views.SemesterView.class,Views.StudentGrades.class,Views.TeacherStudentGrades.class, Views.StudentGroupView.class})
    private String lastName;

    @JsonView(Views.StudentView.class)
    private String privateEmail;

    @JsonView(Views.StudentView.class)
    private String email;

    @JsonView(Views.StudentView.class)
    private Gender gender;

    @JsonView({Views.StudentView.class, Views.TeacherStudentGrades.class, Views.StudentGroupView.class, Views.ScheduleView.class})
    private String numberIdentifier;

    public UserDTO() {
    }

    public UserDTO(long id, String firstName, String lastName, String privateEmail, String email, Gender gender, String numberIdentifier) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.privateEmail = privateEmail;
        this.email = email;
        this.gender = gender;
        this.numberIdentifier = numberIdentifier;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrivateEmail() {
        return privateEmail;
    }

    public void setPrivateEmail(String privateEmail) {
        this.privateEmail = privateEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


    public String getNumberIdentifier() {
        return numberIdentifier;
    }

    public void setNumberIdentifier(String numberIdentifier) {
        this.numberIdentifier = numberIdentifier;
    }

    @Override
    public int compareTo(UserDTO o) {
        return Comparator.comparing(UserDTO::getFirstName)
                .thenComparing(UserDTO::getLastName)
                .compare(this, o);
    }
}
