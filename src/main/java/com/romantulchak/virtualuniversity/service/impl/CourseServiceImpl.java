package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.components.Transformer;
import com.romantulchak.virtualuniversity.dto.CourseDTO;
import com.romantulchak.virtualuniversity.exception.CourseWithNameAlreadyExistsException;
import com.romantulchak.virtualuniversity.model.Course;
import com.romantulchak.virtualuniversity.payload.request.CreateCourseRequest;
import com.romantulchak.virtualuniversity.repository.CourseRepository;
import com.romantulchak.virtualuniversity.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final MessageSource messageSource;
    private final Transformer transformer;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<CourseDTO> findAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(transformer::convertToCourseDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createCourse(CreateCourseRequest createCourseRequest) {
        if (!courseRepository.existsCourseByName(createCourseRequest.getName())) {
            Course course = new Course(createCourseRequest.getName());
            courseRepository.save(course);
        } else {
            throw new CourseWithNameAlreadyExistsException(createCourseRequest.getName(), messageSource);
        }
    }
}
