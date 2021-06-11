package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.SubjectFile;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.model.enumes.SubjectType;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hibernate.Hibernate.isInitialized;

public class SubjectDTO implements Comparable<SubjectDTO> {
    @JsonView({Views.SubjectView.class,Views.SemesterView.class, Views.StudentGrades.class,Views.TeacherStudentGrades.class, Views.TeacherSubjectView.class, Views.StudentGroupView.class, Views.SubjectGrade.class, Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private long id;

    @JsonView({Views.SubjectView.class, Views.SemesterView.class, Views.SpecializationView.class, Views.StudentGrades.class,Views.TeacherStudentGrades.class, Views.TeacherSubjectView.class, Views.StudentGroupView.class, Views.SubjectGrade.class, Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private String name;

    @JsonView({Views.SubjectView.class,Views.SemesterView.class, Views.SpecializationView.class, Views.StudentGrades.class,Views.TeacherStudentGrades.class, Views.TeacherSubjectView.class, Views.StudentGroupView.class, Views.SubjectGrade.class, Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private SubjectType type;
   
    @JsonView({Views.SubjectView.class,Views.SemesterView.class, Views.SpecializationView.class})
    private Collection<TeacherDTO> teachers;

    @JsonView(Views.SubjectView.class)
    private Collection<SubjectFile> files;

    @JsonView({Views.StudentGrades.class, Views.SubjectGrade.class})
    private short ects;


    public SubjectDTO() {
    }
    public SubjectDTO(long id, String name, SubjectType type){
        this.id = id;
        this.name = name;
        this.type = type;
    }
    //TODO: remove if
    public SubjectDTO(Subject subject) {
        this(subject.getId(), subject.getName(), subject.getType());
        this.ects = subject.getEcts();
        if(subject.getTeachers() != null && isInitialized(subject.getTeachers())) {
            this.teachers = subject.getTeachers()
                    .stream()
                    .map(TeacherDTO::new)
                    .collect(Collectors.toList());
        }
    }


    public Collection<SubjectFile> getFiles() {
        return files;
    }

    public void setFiles(Collection<SubjectFile> files) {
        this.files = files;
    }

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

    public Collection<TeacherDTO> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<TeacherDTO> teachers) {
        this.teachers = teachers;
    }

    public short getEcts() {
        return ects;
    }

    public void setEcts(short ects) {
        this.ects = ects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDTO that = (SubjectDTO) o;
        return id == that.id && ects == that.ects && Objects.equals(name, that.name) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, ects);
    }

    @Override
    public int compareTo(SubjectDTO o) {
        return Comparator.comparing(SubjectDTO::getName).compare(this, o);
    }
}
