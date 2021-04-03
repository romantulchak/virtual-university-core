package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsCourseByName(String name);


    @Query(value = "SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.specialization")
    Collection<Course> findCoursesWithSemesters();

}
