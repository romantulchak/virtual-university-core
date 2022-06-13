package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "SELECT l FROM Lesson l LEFT OUTER JOIN l.subjectTeacher lst LEFT OUTER JOIN l.scheduleDay ls WHERE ls.id = :dayId AND lst.teacher.id = :teacherId")
    Collection<Lesson> findLessonsForTeacherByDay(@Param("dayId") long dayId, @Param("teacherId") long teacherId);

    @Modifying
    @Query(value = "UPDATE Lesson l set l.status = :status, l.previousStatus = :previousStatus, l.comment = :comment WHERE l.id = :id")
    void updateStatus(@Param("id") long lessonId, @Param("status") LessonStatus status, @Param("previousStatus") LessonStatus previousStatus, @Param("comment") String comment);

    @Query(value = "SELECT l.status FROM Lesson l WHERE l.id = :lessonId")
    Optional<LessonStatus> findLessonActualStatus(@Param("lessonId") long lessonId);

    boolean existsLessonByDateStartLessThanEqualAndDateEndGreaterThanEqualAndScheduleDayId(LocalDateTime dateStart, LocalDateTime dateEnd, long scheduleDayId);

    boolean existsLessonByDateStartLessThanEqualAndDateEndGreaterThanEqualAndScheduleDayIdAndIdNot(LocalDateTime dateStart, LocalDateTime dateEnd, long scheduleDayId, long id);

    boolean existsById(long id);

    boolean existsByIdAndSubjectTeacherTeacherId(long id, long teacherId);

    @Modifying
    @Query(value = "UPDATE Lesson l SET l.dateStart = :dateStart, l.dateEnd = :dateEnd, l.roomNumber = :roomNumber, l.subjectTeacher = :subjectTeacherGroup WHERE l.id = :lessonId")
    void updateLessonAfterEdit(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd, @Param("roomNumber") String roomNumber, @Param("subjectTeacherGroup") SubjectTeacherGroup subjectTeacherGroup, @Param("lessonId") long id);
}
