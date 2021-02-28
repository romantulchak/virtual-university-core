package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
}
