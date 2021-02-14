package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.StudentStatus;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private long id;

    private String firstName;

    private String lastName;

    private String login;

    private String password;

    @Embedded
    private StudentDetails studentDetails;

    @Enumerated(EnumType.STRING)
    private StudentStatus studentStatus;

    @ManyToMany
    @JoinTable(name = "studets_specializations", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private Collection<Specialization> specializations;


    public Student(String firstName, String lastName, String login, String password, StudentDetails studentDetails, StudentStatus studentStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.studentDetails = studentDetails;
        this.studentStatus = studentStatus;
    }

    public Student() {

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StudentDetails getUserDetails() {
        return studentDetails;
    }

    public void setUserDetails(StudentDetails studentDetails) {
        this.studentDetails = studentDetails;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(StudentStatus studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Collection<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Collection<Specialization> specializations) {
        this.specializations = specializations;
    }
}
