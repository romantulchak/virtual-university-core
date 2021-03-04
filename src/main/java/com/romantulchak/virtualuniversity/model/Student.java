package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.Gender;
import com.romantulchak.virtualuniversity.model.enumes.ERole;
import com.romantulchak.virtualuniversity.model.enumes.StudentStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends UserAbstract{


    @Embedded
    private StudentDetails studentDetails;

    @Enumerated(EnumType.STRING)
    private StudentStatus studentStatus;

    @Embedded
    public Address address;
    @Column(columnDefinition = "integer default 1")
    public int currentSemester = 1;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "studets_specializations", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private Collection<Specialization> specializations;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "students_roles", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public Student(String firstName, String lastName, StudentDetails studentDetails, StudentStatus studentStatus, Gender gender, String privateEmail, String email) {
        super(firstName, lastName, gender, privateEmail, email);
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }
}
