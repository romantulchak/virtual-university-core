package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.CourseDTO;
import com.romantulchak.virtualuniversity.exception.CourseIsNullException;
import com.romantulchak.virtualuniversity.exception.CourseWithNameAlreadyExists;
import com.romantulchak.virtualuniversity.model.Course;
import com.romantulchak.virtualuniversity.repository.CourseRepository;
import com.romantulchak.virtualuniversity.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    private CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Collection<CourseDTO> findAllCourses() {
        Collection<Course> coursesWithSemesters = courseRepository.findCoursesWithSemesters();
        System.out.println(coursesWithSemesters);
        return courseRepository.findCoursesWithSemesters()
                .stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void createCourse(Course course) {
        if (course != null) {
            if (!courseRepository.existsCourseByName(course.getName())) {
                courseRepository.save(course);
            } else {
                throw new CourseWithNameAlreadyExists(course.getName());
            }
        } else {
            throw new CourseIsNullException();
        }
    }

    private CourseDTO convertToDTO(Course course) {
        return new CourseDTO(course);
    }
}
