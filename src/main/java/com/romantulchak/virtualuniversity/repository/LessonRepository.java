package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface LessonRepository extends JpaRepository<Lesson, Long> {


    boolean existsLessonByDateStartLessThanEqualAndDateEndGreaterThanEqual(LocalDateTime dateStart, LocalDateTime dateEnd);

}
