package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.Gender;
import com.romantulchak.virtualuniversity.model.enumes.StudentStatus;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
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

    @ManyToMany(mappedBy = "students")
    private Collection<StudentGroup> studentGroups;

    @OneToMany(mappedBy = "student")
    private Collection<StudentGrade> studentGrades;


    public Student(long id, String firstName, String lastName){
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
    }

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

    public Collection<StudentGroup> getGroups() {
        return studentGroups;
    }

    public void setGroups(Collection<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public Collection<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Collection<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public Collection<StudentGrade> getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(Collection<StudentGrade> studentGrades) {
        this.studentGrades = studentGrades;
    }
}
