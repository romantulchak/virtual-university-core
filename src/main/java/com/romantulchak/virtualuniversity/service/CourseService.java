package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.CourseDTO;
import com.romantulchak.virtualuniversity.exception.CourseWithNameAlreadyExistsException;
import com.romantulchak.virtualuniversity.payload.request.CreateCourseRequest;

import java.util.Collection;

public interface CourseService {

    /**
     * Finds all courses in system with semesters
     *
     * @return list of {@link CourseDTO}
     */
    Collection<CourseDTO> findAllCourses();

    /**
     * Creates course if it doesn't exist otherwise
     * throw {@link CourseWithNameAlreadyExistsException}
     *
     * @param createCourseRequest to get necessary data
     */
    void createCourse(CreateCourseRequest createCourseRequest);
}
