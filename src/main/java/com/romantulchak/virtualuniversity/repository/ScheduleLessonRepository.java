package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.ScheduleLessonRequest;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleLessonRepository extends JpaRepository<ScheduleLessonRequest, Long> {

    @Query(value = "SELECT slr FROM ScheduleLessonRequest slr ORDER BY slr.id DESC ")
    List<ScheduleLessonRequest> findAllRequests(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE ScheduleLessonRequest slr SET slr.decision = :decision, slr.previousStatus = :previousStatus WHERE slr.id = :requestId")
    void updateRequest(@Param("requestId") long requestId, @Param("decision")RequestStatus decision, @Param("previousStatus")LessonStatus previousStatus);

}
