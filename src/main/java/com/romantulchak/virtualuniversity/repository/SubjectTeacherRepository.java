package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacherGroup, Long> {
}
