package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    boolean existsByName(String name);

    Optional<Semester> findFirstBySpecialization_IdAndSemesterNumber(long specialization_id, int semesterNumber);
}
