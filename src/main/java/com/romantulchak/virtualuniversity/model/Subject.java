package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.SubjectType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
public class Subject implements Comparator<Subject> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SubjectType type;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinTable(name = "subject_teacher", joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Collection<Teacher> teachers;

    @OneToMany(mappedBy = "subject")
    private Collection<SubjectTeacherGroup> subjectTeacherGroups;

    @ManyToMany(mappedBy = "subjects")
    private Collection<Specialization> specializations;

    private String description;

    @ElementCollection
    private List<SubjectFile> files = new ArrayList<>();

    @ElementCollection
    private List<TeacherFileSubject> teacherFileSubjects = new ArrayList<>();

    private short ects;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectType getType() {
        return type;
    }

    public void setType(SubjectType type) {
        this.type = type;
    }

    public Collection<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Collection<SubjectTeacherGroup> getSubjectTeacherGroups() {
        return subjectTeacherGroups;
    }

    public void setSubjectTeacherGroups(Collection<SubjectTeacherGroup> subjectTeacherGroups) {
        this.subjectTeacherGroups = subjectTeacherGroups;
    }

    public Collection<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Collection<Specialization> specializations) {
        this.specializations = specializations;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubjectFile> getFiles() {
        return files;
    }

    public void setFiles(List<SubjectFile> files) {
        this.files = files;
    }

    public short getEcts() {
        return ects;
    }

    public void setEcts(short ects) {
        this.ects = ects;
    }

    public List<TeacherFileSubject> getTeacherFileSubjects() {
        return teacherFileSubjects;
    }

    public void setTeacherFileSubjects(List<TeacherFileSubject> teacherFileSubjects) {
        this.teacherFileSubjects = teacherFileSubjects;
    }

    @Override
    public int compare(Subject o1, Subject o2) {
        return o1.getName().compareTo(o2.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return id == subject.id && Objects.equals(name, subject.name) && type == subject.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}
