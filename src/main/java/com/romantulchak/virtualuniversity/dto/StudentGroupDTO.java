package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

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

    @JsonView(Views.StudentGroupView.class)
    private int studentsCount;

    public StudentGroupDTO(){

    }

    private StudentGroupDTO(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.semester = builder.semester;
        this.students = builder.students;
        this.specialization = builder.specialization;
        this.subjects = builder.subjects;
        this.studentsCount = builder.studentsCount;
    }

    private StudentGroupDTO(long id, String name){
        this.id = id;
        this.name = name;
    }

    public StudentGroupDTO(StudentGroup studentGroup, Collection<SubjectTeacherGroupDTO> subjects) {
        this(studentGroup.getId(), studentGroup.getName());
        this.subjects = subjects;
    }

    /**
     * Constructor when you want to get
     * details about group with
     * subjects and students
     * @param name - Student Group Name
     * @param students - Students in Group
     * @param subjects - Subjects in Group
     */
    public StudentGroupDTO(long id, String name, Collection<StudentDTO> students, Collection<SubjectTeacherGroupDTO> subjects, SpecializationDTO specializationDTO) {
        this(id, name);
        this.students = students;
        this.subjects = subjects;
        this.specialization = specializationDTO;
        this.studentsCount = students.size();
    }
    /**
     * Constructor when you want to get
     * all Groups to show
     * @param id - Student Group Id
     * @param name Student Group Name
     * @param semester Student Group Semester
     */
    public StudentGroupDTO(long id, String name, SemesterDTO semester){
        this(id, name);
        this.semester = semester;
    }
    /**
     * Constructor when you want to get
     * Group id, name and count of students
     * @param id - Student Group Id
     * @param name - Student Group Name
     * @param studentsCount - Student Group number of Students in group
     */
    public StudentGroupDTO(long id, String name, int studentsCount){
        this(id, name);
        this.studentsCount = studentsCount;
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

    public int getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }

    public static class Builder {
        private final long id;
        private final String name;

        public Builder(long id, String name){
            this.id = id;
            this.name = name;
        }

        private SemesterDTO semester;

        //    private Schedule schedule;
        private Collection<StudentDTO> students;

        private SpecializationDTO specialization;

        private Collection<SubjectTeacherGroupDTO> subjects;

        private int studentsCount;


        public Builder withSemester(Semester semester){
            this.semester = new SemesterDTO(semester);
            return this;
        }
        public Builder withStudents(Collection<Student> students){
            this.students = students.stream().map(StudentDTO::new).sorted().collect(Collectors.toList());
            return this;
        }
        public Builder withSpecialization(Specialization specialization){
            this.specialization = new SpecializationDTO(specialization);
            return this;
        }
        public Builder withSubjects(Collection<SubjectTeacherGroup> subjects){
            this.subjects = subjects.stream().map(SubjectTeacherGroupDTO::new).sorted().collect(Collectors.toList());
            return this;
        }
        public Builder withCounter(int studentsCount){
            this.studentsCount = studentsCount;
            return this;
        }
        public StudentGroupDTO build(){
            return new StudentGroupDTO(this);
        }


    }

    @Override
    public int compareTo(StudentGroupDTO o) {
        return Comparator.comparing(StudentGroupDTO::getName).compare(this, o);
    }

}
