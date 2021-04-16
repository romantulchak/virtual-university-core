package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.ScheduleDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;

public interface ScheduleDayRepository extends JpaRepository<ScheduleDay, Long> {

    @Query(value = "SELECT EXISTS(SELECT id FROM schedule_day WHERE schedule_day.schedule_id = :scheduleId AND schedule_day.day = :day)", nativeQuery = true)
    boolean checkIfDayAvailable(@Param("scheduleId") long id, @Param("day") LocalDate day);

    @Query(value = "SELECT sd FROM ScheduleDay sd WHERE sd.day >= :dayAfter AND sd.day <= :dayBefore AND sd.schedule.id = :scheduleId")
    Collection<ScheduleDay> findScheduleDaysByRange(@Param("dayAfter") LocalDate dayAfter, @Param("dayBefore") LocalDate dayBefore, @Param("scheduleId") long scheduleId);

    @Query(value = "SELECT sd FROM ScheduleDay sd LEFT JOIN sd.schedule s WHERE s.studentGroup.id = :groupId AND sd.day >= :dayAfter AND sd.day <= :dayBefore")
    Collection<ScheduleDay> findScheduleDaysForWeek(@Param("groupId") long groupId, @Param("dayAfter") LocalDate dayAfter, @Param("dayBefore") LocalDate dayBefore);

    boolean existsById(long id);
}
