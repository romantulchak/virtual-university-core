package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.CourseDTO;
import com.romantulchak.virtualuniversity.exception.CourseIsNullException;
import com.romantulchak.virtualuniversity.model.Course;
import com.romantulchak.virtualuniversity.repository.CourseRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    CourseServiceImpl courseService;


    @Test
    void createCourse() {
//        String testName = "Informatyka";
//        Course course = new Course(testName);
//        courseService.createCourse(course);
//        doAnswer(invocation -> {
//            Course c = invocation.getArgument(0);
//            assertEquals(testName, c.getName());
//            return null;
//        }).when(courseService).createCourse(course);
//        courseService.createCourse(course);

    }


    @Test
    void findAllCourses(){
//        courseService.createCourse(new Course("Test"));
//        when(courseService.findAllCourses()).thenReturn()
    }

    void courseNotExistsByName(){
      //  when(courseRepository).thenReturn()
    }

    @Test
    void createInvalidCourseIsNullException(){

        CourseIsNullException exception = assertThrows(CourseIsNullException.class, ()->{
            courseService.createCourse(null);
        });
        assertEquals(exception.getMessage(), "Course cannot be null");

    }
}
