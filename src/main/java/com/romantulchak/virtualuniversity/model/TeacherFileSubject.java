package com.romantulchak.virtualuniversity.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class TeacherFileSubject {

    @Embedded
    private SubjectFile subjectFile;

    private long teacherId;

    private long studentGroupId;

    private long semesterId;

    public TeacherFileSubject(){

    }

    public TeacherFileSubject(SubjectFile subjectFile, long teacherId, long studentGroupId, long semesterId) {
        this.subjectFile = subjectFile;
        this.teacherId = teacherId;
        this.studentGroupId = studentGroupId;
        this.semesterId = semesterId;
    }

    public SubjectFile getSubjectFile() {
        return subjectFile;
    }

    public void setSubjectFile(SubjectFile subjectFile) {
        this.subjectFile = subjectFile;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(long studentGroupId) {
        this.studentGroupId = studentGroupId;
    }

    public long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(long semesterId) {
        this.semesterId = semesterId;
    }
}
