package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.CourseDTO;
import com.romantulchak.virtualuniversity.model.Course;

import java.util.Collection;

public interface CourseService {
    Collection<CourseDTO> findAllCourses();
}
