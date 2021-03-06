package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.*;

import javax.persistence.*;

public class TeacherSubjectStudentGradeLinkDTO {

    @JsonView({Views.StudentGrades.class,Views.TeacherStudentGrades.class})
    private long id;

    @JsonView(Views.TeacherStudentGrades.class)
    private StudentDTO student;

    @JsonView({Views.StudentGrades.class,Views.TeacherStudentGrades.class})
    private SubjectDTO subject;

    @JsonView({Views.StudentGrades.class,Views.TeacherStudentGrades.class})
    private GradeDTO grade;

    @JsonView({Views.StudentGrades.class,Views.TeacherStudentGrades.class})
    private SpecializationDTO specialization;

    @JsonView({Views.StudentGrades.class,Views.TeacherStudentGrades.class})
    private TeacherDTO teacher;

    @JsonView({Views.StudentGrades.class,Views.TeacherStudentGrades.class})
    private SemesterDTO semester;

    public TeacherSubjectStudentGradeLinkDTO() {
    }

    public TeacherSubjectStudentGradeLinkDTO(long id, SubjectDTO subject, GradeDTO grade, SpecializationDTO specialization, TeacherDTO teacher, StudentDTO student, SemesterDTO semester) {
        this.id = id;
        this.subject = subject;
        this.grade = grade;
        this.specialization = specialization;
        this.teacher = teacher;
        this.student = student;
        this.semester = semester;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public GradeDTO getGrade() {
        return grade;
    }

    public void setGrade(GradeDTO grade) {
        this.grade = grade;
    }

    public SpecializationDTO getSpecialization() {
        return specialization;
    }

    public void setSpecialization(SpecializationDTO specialization) {
        this.specialization = specialization;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public SemesterDTO getSemester() {
        return semester;
    }

    public void setSemester(SemesterDTO semester) {
        this.semester = semester;
    }
}
