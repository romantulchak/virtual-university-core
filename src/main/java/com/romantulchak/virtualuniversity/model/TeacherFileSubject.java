package com.romantulchak.virtualuniversity.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class TeacherFileSubject {

    @Embedded
    private SubjectFile subjectFile;

    private long teacherId;

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
}
