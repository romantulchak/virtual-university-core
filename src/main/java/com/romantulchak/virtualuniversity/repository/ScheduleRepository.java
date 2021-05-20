package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(value = "SELECT s FROM Schedule s JOIN FETCH s.days sd WHERE s.semester.id = :semesterId")
    Optional<Schedule> findScheduleBySemesterId(@Param("semesterId") long semesterId);

    @Query(value = "SELECT schedule.id FROM schedule WHERE schedule.semester_id = :semesterId", nativeQuery = true)
    Optional<Long> findScheduleIdBySemesterId(@Param("semesterId") long semesterId);

    @Query(value = "SELECT s FROM Schedule s JOIN FETCH s.days WHERE s.id = :id AND s.semester.id = :semesterId")
    Optional<Schedule> findByIdWithDays(@Param("id") long id, @Param("semesterId") long semesterId);

    @Query(value = "SELECT EXISTS(SELECT id FROM schedule WHERE id = :scheduleId AND semester_id = :semesterId) ", nativeQuery = true)
    boolean existsByIdAndSemester(@Param("scheduleId") long scheduleId, @Param("semesterId") long semesterId);
}
