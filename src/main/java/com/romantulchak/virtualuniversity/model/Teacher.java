package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.Gender;
import com.romantulchak.virtualuniversity.model.enumes.Role;

import javax.persistence.*;
import java.util.Collection;
@Entity
public class Teacher extends UserAbstract{

    @ManyToMany
    @JoinTable(name = "teacher_specialization", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private Collection<Specialization> specializations;

    @OneToMany(mappedBy = "student")
    private Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks;


    public Teacher() {
    }

    public Teacher(String firstName, String lastName, String login, String password, Gender gender, String privateEmail, String email, Role role) {
        super(firstName, lastName, login, password, gender, privateEmail, email, role);
    }

    public Collection<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Collection<Specialization> specializations) {
        this.specializations = specializations;
    }

    public Collection<TeacherSubjectStudentGradeLink> getTeacherSubjectStudentGradeLinks() {
        return teacherSubjectStudentGradeLinks;
    }

    public void setTeacherSubjectStudentGradeLinks(Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks) {
        this.teacherSubjectStudentGradeLinks = teacherSubjectStudentGradeLinks;
    }
}


