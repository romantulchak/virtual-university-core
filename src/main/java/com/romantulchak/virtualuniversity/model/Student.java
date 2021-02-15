package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.Gender;
import com.romantulchak.virtualuniversity.model.enumes.Role;
import com.romantulchak.virtualuniversity.model.enumes.StudentStatus;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Student extends UserAbstract{


    @Embedded
    private StudentDetails studentDetails;

    @Enumerated(EnumType.STRING)
    private StudentStatus studentStatus;

    @Embedded
    public Address address;

    @ManyToMany
    @JoinTable(name = "studets_specializations", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private Collection<Specialization> specializations;

    @OneToMany(mappedBy = "student")
    private Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks;

    public Student(String firstName, String lastName, String login, String password, StudentDetails studentDetails, StudentStatus studentStatus, Gender gender, String privateEmail, String email, Role role) {
        super(firstName, lastName, login, password, gender, privateEmail, email,role);
        this.studentDetails = studentDetails;
        this.studentStatus = studentStatus;
    }

    public Student() {

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

    public StudentDetails getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(StudentDetails studentDetails) {
        this.studentDetails = studentDetails;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Collection<TeacherSubjectStudentGradeLink> getTeacherSubjectStudentGradeLinks() {
        return teacherSubjectStudentGradeLinks;
    }

    public void setTeacherSubjectStudentGradeLinks(Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks) {
        this.teacherSubjectStudentGradeLinks = teacherSubjectStudentGradeLinks;
    }
}
