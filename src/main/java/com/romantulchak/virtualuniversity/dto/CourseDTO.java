package com.romantulchak.virtualuniversity.dto;

import com.romantulchak.virtualuniversity.model.Course;
import com.romantulchak.virtualuniversity.model.Specialization;

import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.stream.Collectors;

public class CourseDTO {

    private long id;

    private String name;

    private Collection<SpecializationDTO> specializationDTO;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.specializationDTO = course.getSpecialization().stream().map(SpecializationDTO::new).collect(Collectors.toList());
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

    public Collection<SpecializationDTO> getSpecializationDTO() {
        return specializationDTO;
    }

    public void setSpecializationDTO(Collection<SpecializationDTO> specializationDTO) {
        this.specializationDTO = specializationDTO;
    }
}
