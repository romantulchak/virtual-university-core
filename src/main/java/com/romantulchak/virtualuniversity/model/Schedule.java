package com.romantulchak.virtualuniversity.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    private Collection<ScheduleDay> days;

    @ManyToOne
    private Semester semester;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Collection<ScheduleDay> getDays() {
        return days;
    }

    public void setDays(Collection<ScheduleDay> days) {
        this.days = days;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
