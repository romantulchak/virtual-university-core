package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
