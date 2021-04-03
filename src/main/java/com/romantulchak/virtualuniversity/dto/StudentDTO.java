package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.model.enumes.StudentStatus;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDTO extends UserDTO{

    @JsonView(Views.StudentView.class)
    private StudentDetails studentDetails;


    @JsonView(Views.StudentView.class)
    private StudentStatus studentStatus;


    @JsonView(Views.StudentView.class)
    public Address address;


    @JsonView(Views.StudentView.class)
    private Collection<SpecializationDTO> specializations;

    private Collection<TeacherSubjectStudentGradeLinkDTO> teacherSubjectStudentGradeLinks;
    @JsonView(Views.StudentView.class)
    private int currentSemester;

    private Set<Role> roles = new HashSet<>();



    public StudentDTO(){

    }

    //TODO
    public StudentDTO(Student student){
        super(student.getId(), student.getFirstName(), student.getLastName(), student.getPrivateEmail(), student.getEmail(), student.getGender());
        this.studentDetails = student.getStudentDetails();
        this.studentStatus = student.getStudentStatus();
        this.address = student.address;
        this.specializations = student.getSpecializations() != null ? student.getSpecializations().stream().map(SpecializationDTO::new).collect(Collectors.toList()) : null;
        this.currentSemester = student.getCurrentSemester();
    }

    public StudentDetails getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(StudentDetails studentDetails) {
        this.studentDetails = studentDetails;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(StudentStatus studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Collection<SpecializationDTO> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Collection<SpecializationDTO> specializations) {
        this.specializations = specializations;
    }

    public Collection<TeacherSubjectStudentGradeLinkDTO> getTeacherSubjectStudentGradeLinks() {
        return teacherSubjectStudentGradeLinks;
    }

    public void setTeacherSubjectStudentGradeLinks(Collection<TeacherSubjectStudentGradeLinkDTO> teacherSubjectStudentGradeLinks) {
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
