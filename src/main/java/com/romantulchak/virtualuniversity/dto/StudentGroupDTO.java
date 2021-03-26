package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.*;

import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Comparator;

public class StudentGroupDTO implements Comparable<StudentGroupDTO>{

    @JsonView(Views.StudentGroupView.class)
    private long id;

    @JsonView(Views.StudentGroupView.class)
    private String name;

    @JsonView(Views.StudentGroupView.class)
    private SemesterDTO semester;

//    private Schedule schedule;
    @JsonView(Views.StudentGroupView.class)
    private Collection<StudentDTO> students;
    @JsonView(Views.StudentGroupView.class)
    private SpecializationDTO specialization;
    @JsonView(Views.StudentGroupView.class)
    private Collection<SubjectTeacherGroupDTO> subjects;

    public StudentGroupDTO(){

    }

    public StudentGroupDTO(StudentGroup studentGroup, Collection<SubjectTeacherGroupDTO> subjects) {
        this.id = studentGroup.getId();
        this.name = studentGroup.getName();
        this.subjects = subjects;
//        this.semester = group.getSemester();
//        this.schedule = studentGroup.getSchedule();
//        this.students = studentGroup.getStudents();
    }

    /**
     *
     * @param name - Student Group Name
     * @param students - Students in Group
     * @param subjects - Subjects in Group
     */
    public StudentGroupDTO(String name, Collection<StudentDTO> students, Collection<SubjectTeacherGroupDTO> subjects, SpecializationDTO specializationDTO) {
        this.name = name;
        this.students = students;
        this.subjects = subjects;
        this.specialization = specializationDTO;
    }

    /**
     * Constructor when you want to get
     * all Groups to show
     *
     * @param id - Student Group Id
     * @param name Student Group Name
     * @param semester Student Group Semester
     */
    public StudentGroupDTO(long id, String name, SemesterDTO semester){
        this.id = id;
        this.name = name;
        this.semester = semester;
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

    public SemesterDTO getSemester() {
        return semester;
    }

    public void setSemester(SemesterDTO semester) {
        this.semester = semester;
    }

    public Collection<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Collection<StudentDTO> students) {
        this.students = students;
    }

    public SpecializationDTO getSpecialization() {
        return specialization;
    }

    public void setSpecialization(SpecializationDTO specialization) {
        this.specialization = specialization;
    }

    public Collection<SubjectTeacherGroupDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Collection<SubjectTeacherGroupDTO> subjects) {
        this.subjects = subjects;
    }

    @Override
    public int compareTo(StudentGroupDTO o) {
        return Comparator.comparing(StudentGroupDTO::getName).compare(this, o);
    }
}
