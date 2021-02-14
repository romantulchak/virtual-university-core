package com.romantulchak.virtualuniversity.dto;

import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.StudentDetails;
import com.romantulchak.virtualuniversity.model.enumes.StudentStatus;

import java.util.Collection;

public class UserDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String login;

    private String password;

    private StudentDetails studentDetails;

    private StudentStatus studentStatus;

    private Collection<Specialization> specializations;

    public UserDTO() {
    }

    public UserDTO(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.login = student.getLogin();
        this.password = student.getPassword();
        this.studentDetails = student.getUserDetails();
        this.studentStatus = student.getStudentStatus();
        this.specializations = student.getSpecializations();
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
