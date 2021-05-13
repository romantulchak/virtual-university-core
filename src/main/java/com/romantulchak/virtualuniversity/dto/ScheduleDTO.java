package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.ScheduleDay;
import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.model.Views;

import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;

public class ScheduleDTO {

    @JsonView(Views.ScheduleView.class)
    private long id;

    @JsonView(Views.ScheduleView.class)
    private StudentGroupDTO studentGroup;

    @JsonView(Views.ScheduleView.class)
    private SemesterDTO semesterDTO;

    @JsonView(Views.ScheduleView.class)
    private Collection<ScheduleDayDTO> days;

    public ScheduleDTO(long id ){
        this.id = id;
    }

    public ScheduleDTO(long id, Collection<ScheduleDayDTO> days) {
        this(id);
        this.days = days;
    }

    public ScheduleDTO(long id, Collection<ScheduleDayDTO> days, StudentGroupDTO studentGroup) {
        this(id);
        this.days = days;
        this.studentGroup = studentGroup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StudentGroupDTO getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroupDTO studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Collection<ScheduleDayDTO> getDays() {
        return days;
    }

    public void setDays(Collection<ScheduleDayDTO> days) {
        this.days = days;
    }
}
