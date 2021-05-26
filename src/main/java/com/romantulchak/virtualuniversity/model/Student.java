package com.romantulchak.virtualuniversity.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.romantulchak.virtualuniversity.model.enumes.Gender;
import com.romantulchak.virtualuniversity.model.enumes.StudentStatus;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "students_roles", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    private StudentGroup studentGroup;

    @OneToMany(mappedBy = "student")
    private Collection<StudentGroupGrade> studentGroupGrades;

    public Student(long id, String firstName, String lastName, String numberIdentifier){
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setNumberIdentifier(numberIdentifier);
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

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Collection<StudentGroupGrade> getStudentGroupGrades() {
        return studentGroupGrades;
    }

    public void setStudentGroupGrades(Collection<StudentGroupGrade> studentGroupGrades) {
        this.studentGroupGrades = studentGroupGrades;
    }
    @PreRemove
    public void preRemove(){
        setStudentGroup(null);
    }
}
