package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentGradeRepository extends JpaRepository<TeacherSubjectStudentGradeLink, Long> {
    Collection<TeacherSubjectStudentGradeLink> findAllByStudent_Id(long id);
}
