package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.ScheduleDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface ScheduleDayRepository extends JpaRepository<ScheduleDay, Long> {

    @Query(value = "SELECT sd FROM ScheduleDay sd WHERE sd.schedule.id = :scheduleId AND sd.semester.id = :semesterId")
    Collection<ScheduleDay> findAllDaysForScheduleAndSemester(@Param("scheduleId") long scheduleId, @Param("semesterId") long semesterId);

    @Query(value = "SELECT EXISTS(SELECT id FROM schedule_day WHERE schedule_day.schedule_id = :scheduleId AND schedule_day.day = :day)", nativeQuery = true)
    boolean checkIfDayAvailable(@Param("scheduleId") long id, @Param("day") LocalDate day);

    @Query(value = "SELECT sd FROM ScheduleDay sd WHERE sd.day >= :dayAfter AND sd.day <= :dayBefore AND sd.schedule.id = :scheduleId")
    Collection<ScheduleDay> findScheduleDaysByRange(@Param("dayAfter") LocalDate dayAfter, @Param("dayBefore") LocalDate dayBefore, @Param("scheduleId") long scheduleId);

    @Query(value = "SELECT sd FROM ScheduleDay sd LEFT JOIN sd.schedule s WHERE sd.day >= :dayAfter AND sd.day <= :dayBefore AND sd.semester.id = :semesterId")
    Collection<ScheduleDay> findScheduleDaysForWeek(@Param("semesterId") long semesterId, @Param("dayAfter") LocalDate dayAfter, @Param("dayBefore") LocalDate dayBefore);

    @Query(value = "SELECT sd FROM ScheduleDay sd LEFT OUTER JOIN sd.lessons sdl LEFT OUTER JOIN sd.schedule sds WHERE sdl.subjectTeacher.teacher.id = :teacherId AND sd.semester.id = :semesterId")
    Collection<ScheduleDay> findAllScheduleDayForTeacher(@Param("teacherId") long teacherId, @Param("semesterId") long semesterId);

    @Query(value = "SELECT sd FROM ScheduleDay sd LEFT OUTER JOIN sd.lessons sdl LEFT OUTER JOIN sd.schedule sds WHERE sdl.subjectTeacher.teacher.id = :teacherId AND sd.semester.id = :semesterId AND sd.day >= :dayAfter AND sd.day <= :dayBefore")
    Collection<ScheduleDay> findScheduleDayForTeacherByWeek(@Param("teacherId") long teacherId, @Param("semesterId") long semesterId, @Param("dayAfter") LocalDate dayAfter, @Param("dayBefore") LocalDate dayBefore);

    @Query(value = "SELECT sd FROM ScheduleDay sd LEFT OUTER JOIN sd.lessons sdl LEFT OUTER JOIN sd.schedule sds WHERE sdl.subjectTeacher.teacher.id = :teacherId AND sd.semester.id = :semesterId AND sd.day >= :dayAfter AND sd.day <= :dayBefore")
    Collection<ScheduleDay> findScheduleDayForTeacherForWeek(@Param("teacherId") long teacherId, @Param("semesterId") long semesterId, @Param("dayAfter") LocalDate dayAfter, @Param("dayBefore") LocalDate dayBefore);

    @Query(value = "SELECT sd FROM ScheduleDay sd LEFT OUTER JOIN sd.lessons sdl  WHERE sdl.subjectTeacher.studentGroup.name = :groupName AND sd.day = :day AND sd.semester.id = :semesterId")
    Optional<ScheduleDay> findScheduleDayByAndGroupName(@Param("day") LocalDate day, @Param("groupName") String groupName, @Param("semesterId") long semesterId);

    boolean existsById(long id);
}
