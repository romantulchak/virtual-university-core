package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentGradeRepository extends JpaRepository<TeacherSubjectStudentGradeLink, Long> {
}
