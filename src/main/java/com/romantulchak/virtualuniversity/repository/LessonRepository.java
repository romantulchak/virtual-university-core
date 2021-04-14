package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface LessonRepository extends JpaRepository<Lesson, Long> {


    boolean existsLessonByDateStartLessThanEqualAndDateEndGreaterThanEqual(LocalDateTime dateStart, LocalDateTime dateEnd);

    boolean existsById(long id);
}
