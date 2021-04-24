package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "SELECT l FROM Lesson l LEFT OUTER JOIN l.subjectTeacher lst LEFT OUTER JOIN l.scheduleDay ls WHERE ls.id = :dayId AND lst.teacher.id = :teacherId")
    Collection<Lesson> findLessonsForTeacherByDay(@Param("dayId") long dayId, @Param("teacherId") long teacherId);

    boolean existsLessonByDateStartLessThanEqualAndDateEndGreaterThanEqual(LocalDateTime dateStart, LocalDateTime dateEnd);

    boolean existsById(long id);
}
