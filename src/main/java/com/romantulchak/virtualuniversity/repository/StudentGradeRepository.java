package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Grade;
import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;
import com.romantulchak.virtualuniversity.model.enumes.GradeRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
@Transactional
@Repository
public interface StudentGradeRepository extends JpaRepository<TeacherSubjectStudentGradeLink, Long> {
    Collection<TeacherSubjectStudentGradeLink> findAllByStudent_Id(long id);
    Collection<TeacherSubjectStudentGradeLink> findAllByTeacher_IdAndSpecialization_IdAndSemester_Id(long teacherId, long specializationId, long semesterId);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE TeacherSubjectStudentGradeLink t SET t.grade = :grade WHERE t.id = :id")
    void setGrade(@Param("id") long id, @Param("grade") double grade);

    Collection<TeacherSubjectStudentGradeLink> findAllByStudent_IdAndSemester_SemesterNumber(long studentId, int semesterNumber);
}
