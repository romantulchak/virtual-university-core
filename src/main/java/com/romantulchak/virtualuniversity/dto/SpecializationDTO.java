package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Views;

public class SpecializationDTO {

    @JsonView(Views.StudentView.class)
    private String name;


    public SpecializationDTO(){

    }
    public SpecializationDTO(Specialization specialization){
        this.name = specialization.getName();
    }

    public String getSpecializationName() {
        return name;
    }

    public void setSpecializationName(String name) {
        this.name = name;
    }
}
