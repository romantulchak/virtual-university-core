package com.romantulchak.virtualuniversity.dto;

import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;

public class SubjectTeacherGroupDTO {

    private long id;

    private SubjectDTO subject;

    private TeacherDTO teacher;

    private StudentGroupDTO studentGroup;

    public SubjectTeacherGroupDTO(){

    }

    public SubjectTeacherGroupDTO(SubjectTeacherGroup subjectTeacherGroup, SubjectDTO subject, TeacherDTO teacher, StudentGroupDTO studentGroup){
        this.id = subjectTeacherGroup.getId();
        this.subject = subject;
        this.teacher = teacher;
        this.studentGroup = studentGroup;
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
}
