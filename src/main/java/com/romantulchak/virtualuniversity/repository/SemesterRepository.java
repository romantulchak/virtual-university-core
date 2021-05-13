package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    @Query(value = "SELECT s FROM Semester s LEFT OUTER JOIN s.groups sg WHERE sg.id = :groupId")
    Collection<Semester> findAllSemesterForGroup(@Param("groupId") long groupId);
}
