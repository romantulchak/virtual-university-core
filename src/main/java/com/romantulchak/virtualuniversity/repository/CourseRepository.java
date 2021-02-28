package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
