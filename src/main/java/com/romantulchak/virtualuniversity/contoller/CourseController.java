package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.dto.CourseDTO;
import com.romantulchak.virtualuniversity.payload.request.CreateCourseRequest;
import com.romantulchak.virtualuniversity.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@CrossOrigin(value = "*",maxAge = 3600L)
@RequestMapping("/api/course")
public class CourseController {

    private final CourseServiceImpl courseService;

    @Autowired
    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Collection<CourseDTO> findAllCourses(){
        return courseService.findAllCourses();
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void createCourse(@Valid @RequestBody CreateCourseRequest course){
        courseService.createCourse(course);
    }
}
