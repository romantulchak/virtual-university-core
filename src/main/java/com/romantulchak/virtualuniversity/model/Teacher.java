package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.exception.StudentNotFoundException;
import com.romantulchak.virtualuniversity.model.enumes.Gender;
import com.romantulchak.virtualuniversity.model.enumes.ERole;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Teacher extends UserAbstract{


    @ManyToMany
    @JoinTable(name = "teacher_specialization", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private Collection<Specialization> specializations;

    @OneToMany(mappedBy = "teacher")
    private Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "teachers_roles", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "teachers")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Subject> subjects;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, Gender gender, String privateEmail, String email) {
        super(firstName, lastName, gender, privateEmail, email);
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Collection<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Collection<Subject> subjects) {
        this.subjects = subjects;
    }

}


