package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.ScheduleLessonRequest;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.model.enumes.RequestDecision;
import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleLessonRequestRepository extends JpaRepository<ScheduleLessonRequest, Long> {

    @Query(value = "SELECT slr FROM ScheduleLessonRequest slr ORDER BY slr.id DESC ")
    Page<ScheduleLessonRequest> findAllRequests(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE ScheduleLessonRequest slr SET slr.decision = :decision, slr.previousStatus = :previousStatus WHERE slr.id = :requestId")
    void updateRequest(@Param("requestId") long requestId, @Param("decision") RequestDecision decision, @Param("previousStatus")LessonStatus previousStatus);

    @Modifying
    @Query(value = "UPDATE ScheduleLessonRequest slr SET slr.info.requestStatus = :requestStatus, slr.info.userFullName = :userFullName, slr.info.username = :username WHERE slr.id = :requestId")
    void setRequestStatus(@Param("requestId") long requestId, @Param("requestStatus")RequestStatus requestStatus, @Param("userFullName") String userFullName, @Param("username") String username);

    @Modifying
    @Query(value = "UPDATE ScheduleLessonRequest slr SET slr.info.requestStatus = :requestStatus WHERE slr.id = :requestId")
    void setRequestStatus(@Param("requestId") long requestId, @Param("requestStatus")RequestStatus requestStatus);
}
