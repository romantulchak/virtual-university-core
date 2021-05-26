package com.romantulchak.virtualuniversity.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.romantulchak.virtualuniversity.model.enumes.Gender;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends UserAbstract{


    @ManyToMany
    @JoinTable(name = "teacher_specialization", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private Collection<Specialization> specializations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "teachers_roles", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "teacher")
    private Collection<SubjectTeacherGroup> subjectTeacherGroups;

    @ManyToMany(mappedBy = "teachers")
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

    public Collection<SubjectTeacherGroup> getSubjectTeacherGroups() {
        return subjectTeacherGroups;
    }

    public void setSubjectTeacherGroups(Collection<SubjectTeacherGroup> subjectTeacherGroups) {
        this.subjectTeacherGroups = subjectTeacherGroups;
    }
}


