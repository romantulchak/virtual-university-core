package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Course;
import com.romantulchak.virtualuniversity.model.Views;
import lombok.Data;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class CourseDTO {

    @JsonView(Views.CourseView.class)
    private long id;

    @JsonView(Views.CourseView.class)
    private String name;

    private Collection<SpecializationDTO> specializationDTO;

    public CourseDTO() {
    }

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.specializationDTO = course.getSpecialization().stream().map(SpecializationDTO::new).collect(Collectors.toList());
    }
}
