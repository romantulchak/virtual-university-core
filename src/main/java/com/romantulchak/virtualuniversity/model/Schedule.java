package com.romantulchak.virtualuniversity.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private StudentGroup studentGroup;

    @OneToMany(mappedBy = "schedule")
    private Collection<ScheduleDay> days;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StudentGroup getGroup() {
        return studentGroup;
    }

    public void setGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Collection<ScheduleDay> getDays() {
        return days;
    }

    public void setDays(Collection<ScheduleDay> days) {
        this.days = days;
    }


}
