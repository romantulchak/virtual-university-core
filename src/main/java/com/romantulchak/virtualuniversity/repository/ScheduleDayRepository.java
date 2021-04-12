package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.ScheduleDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ScheduleDayRepository extends JpaRepository<ScheduleDay, Long> {

    @Query(value = "SELECT EXISTS(SELECT id FROM schedule_day WHERE schedule_day.schedule_id = :scheduleId AND schedule_day.day = :day)", nativeQuery = true)
    boolean checkIfDayAvailable(@Param("scheduleId") long id, @Param("day") LocalDate day);
}
