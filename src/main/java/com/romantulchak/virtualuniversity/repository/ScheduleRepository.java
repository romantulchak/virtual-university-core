package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(value = "SELECT s FROM Schedule s JOIN FETCH s.days sd WHERE s.studentGroup.id = :groupId")
    Optional<Schedule> findScheduleByGroupId(@Param("groupId") long groupId);

    @Query(value = "SELECT s.id FROM Schedule s WHERE s.studentGroup.id = :groupId")
    Optional<Long> findScheduleIdByGroupId(@Param("groupId") long groupId);

    @Query(value = "SELECT s FROM Schedule s JOIN FETCH s.days WHERE s.id = :id")
    Optional<Schedule> findByIdWithDays(@Param("id") long id);
}
