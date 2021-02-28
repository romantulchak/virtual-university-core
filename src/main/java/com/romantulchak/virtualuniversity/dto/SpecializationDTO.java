package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Views;

public class SpecializationDTO {

    private long id;
    @JsonView(Views.StudentView.class)
    private String name;


    public SpecializationDTO(){

    }
    public SpecializationDTO(Specialization specialization){
        this.id = specialization.getId();
        this.name = specialization.getName();
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
}
