package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.ScheduleDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDayRepository extends JpaRepository<ScheduleDay, Long> {
}
