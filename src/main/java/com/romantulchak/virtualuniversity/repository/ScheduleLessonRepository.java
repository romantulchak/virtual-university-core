package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.ScheduleLessonRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleLessonRepository extends JpaRepository<ScheduleLessonRequest, Long> {

    @Query(value = "SELECT slr FROM ScheduleLessonRequest slr ORDER BY slr.id DESC ")
    List<ScheduleLessonRequest> findAllRequests(Pageable pageable);
}
