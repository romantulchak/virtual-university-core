package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
