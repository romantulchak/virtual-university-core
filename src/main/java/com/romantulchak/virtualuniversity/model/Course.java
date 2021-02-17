package com.romantulchak.virtualuniversity.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(mappedBy = "course")
    private Collection<Specialization> specialization;

    public Course(){}

    public Course(String name){}


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

    public Collection<Specialization> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Collection<Specialization> specialization) {
        this.specialization = specialization;
    }
}
