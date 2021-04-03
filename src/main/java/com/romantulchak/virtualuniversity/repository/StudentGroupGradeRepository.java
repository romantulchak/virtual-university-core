package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.StudentGroupGrade;
import com.romantulchak.virtualuniversity.projection.StudentGradeLimited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
@Repository
public interface StudentGroupGradeRepository extends JpaRepository<StudentGroupGrade, Long> {


    @Modifying
    @Query(value ="INSERT INTO student_group_grade (grade, student_id, student_group_id, subject_teacher_group_id) VALUES (0, :studentId, :groupId, :subjectId)", nativeQuery = true)
    void saveStudentGrade(@Param("studentId") long studentId, @Param("groupId") long groupId, @Param("subjectId") long subjectId);

    @Modifying
    @Query(value ="UPDATE StudentGroupGrade sgg SET sgg.grade = :grade WHERE sgg.id = :studentGroupGradeId")
    void setGrade(@Param("studentGroupGradeId") long studentGroupGradeId, @Param("grade") double grade);

//
//
//    @Query(value = "SELECT DISTINCT * FROM student_group_grade LEFT OUTER JOIN student_group sg on student_group_grade.student_group_id = sg.id " +
//                    "LEFT JOIN subject_teacher_group stg on sg.id = stg.student_group_id " +
//                    "WHERE teacher_id = :teacherId AND stg.student_group_id = :groupId AND student_group_grade.subject_id = :subjectId", nativeQuery = true)
    @Query(value = "SELECT DISTINCT sgg.id as id, sgg.student as student, sgg.grade as grade FROM StudentGroupGrade sgg LEFT OUTER JOIN sgg.studentGroup sg" +
                    " LEFT JOIN sgg.subjectTeacherGroup sst LEFT JOIN sst.subject" +
                    " WHERE sg.id = :groupId AND sst.subject.id = :subjectId")
    Collection<StudentGradeLimited> findStudentGradesForGroupAndSubjectByTeacher(@Param("groupId") long groupId, @Param("subjectId") long subjectId);


}
