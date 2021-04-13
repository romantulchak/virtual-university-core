package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.Views;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;

public class ScheduleDayDTO implements Comparable<ScheduleDayDTO>{

    @JsonView(Views.ScheduleView.class)
    private long id;

    @JsonView(Views.ScheduleView.class)
    private LocalDate day;

    @JsonView(Views.ScheduleView.class)
    private Collection<LessonDTO> lessons;

    private ScheduleDTO schedule;

    public ScheduleDayDTO(){}

    public ScheduleDayDTO(long id, LocalDate day, Collection<LessonDTO> lessons) {
        this.id = id;
        this.day = day;
        this.lessons = lessons;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Collection<LessonDTO> getLessons() {
        return lessons;
    }

    public void setLessons(Collection<LessonDTO> lessons) {
        this.lessons = lessons;
    }

    public ScheduleDTO getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDTO schedule) {
        this.schedule = schedule;
    }

    @Override
    public int compareTo(ScheduleDayDTO o) {
        return Comparator.comparing(ScheduleDayDTO::getDay).compare(this, o);
    }
}
