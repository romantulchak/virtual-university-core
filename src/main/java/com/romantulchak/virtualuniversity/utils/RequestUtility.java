package com.romantulchak.virtualuniversity.utils;

import com.romantulchak.virtualuniversity.dto.TeacherDTO;
import com.romantulchak.virtualuniversity.model.NotificationBox;
import com.romantulchak.virtualuniversity.model.ScheduleLessonRequest;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.Teacher;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;

public class RequestUtility {

    private final ScheduleLessonRequest request;

    public RequestUtility(ScheduleLessonRequest request){
        this.request = request;
    }

    public LocalDate getDay(){
        return request.getLesson()
                .getScheduleDay()
                .getDay();
    }

    public String getSubject(){
        return request.getLesson()
                .getSubjectTeacher()
                .getSubject()
                .getName();
    }

    public NotificationBox getNotificationBox(){
        return request.getLesson()
                .getSubjectTeacher()
                .getTeacher()
                .getNotificationBox();
    }

    public Teacher getTeacher(){
        return request.getLesson().getSubjectTeacher().getTeacher();
    }

    public Collection<Student> getStudents(){
        return request.getLesson()
                .getSubjectTeacher()
                .getStudentGroup()
                .getStudents();
    }

}
