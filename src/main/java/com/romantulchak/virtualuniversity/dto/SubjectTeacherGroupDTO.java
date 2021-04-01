package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.model.enumes.SubjectType;

import java.util.Comparator;
import java.util.stream.Collectors;

public class SubjectTeacherGroupDTO implements Comparable<SubjectTeacherGroupDTO>{

    @JsonView(Views.StudentGroupView.class)
    private long id;

    @JsonView(Views.StudentGroupView.class)
    private SubjectDTO subject;

    @JsonView(Views.StudentGroupView.class)
    private TeacherDTO teacher;

    private StudentGroupDTO studentGroup;

    public SubjectTeacherGroupDTO(){

    }

    public SubjectTeacherGroupDTO(long id, String name, SubjectType type){
        this.subject = new SubjectDTO(id, name, type);
    }


    public SubjectTeacherGroupDTO(SubjectTeacherGroup subjectTeacherGroup){
        this.id = subjectTeacherGroup.getId();
        this.subject = new SubjectDTO(subjectTeacherGroup.getSubject());
        this.teacher = new TeacherDTO(subjectTeacherGroup.getTeacher());
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public StudentGroupDTO getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroupDTO studentGroup) {
        this.studentGroup = studentGroup;
    }


    @Override
    public int compareTo(SubjectTeacherGroupDTO o) {
        return Comparator
                .comparing(o1 -> getSubject().getName()).compare(this, o);

    }
}
