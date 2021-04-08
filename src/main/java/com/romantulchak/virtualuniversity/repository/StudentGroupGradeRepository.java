package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.StudentGroupGrade;
import com.romantulchak.virtualuniversity.model.enumes.GradeStatus;
import com.romantulchak.virtualuniversity.projection.StudentGradeLimitedStudent;
import com.romantulchak.virtualuniversity.projection.StudentGradeLimitedTeacher;
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
    @Query(value ="INSERT INTO student_group_grade (grade, student_id, student_group_id, subject_teacher_group_id, status) VALUES (0, :studentId, :groupId, :subjectId, :status)", nativeQuery = true)
    void saveStudentGrade(@Param("studentId") long studentId, @Param("groupId") long groupId, @Param("subjectId") long subjectId, @Param("status") GradeStatus status);

    @Modifying
    @Query(value ="UPDATE StudentGroupGrade sgg SET sgg.grade = :grade WHERE sgg.id = :studentGroupGradeId")
    void setGrade(@Param("studentGroupGradeId") long studentGroupGradeId, @Param("grade") double grade);

    @Modifying
    @Query(value = "UPDATE StudentGroupGrade sgg SET sgg.status = :status WHERE sgg.id = :studentGroupGradeId")
    void setStatusForGrade(@Param("status")GradeStatus status, @Param("studentGroupGradeId") long studentGroupGradeId);

    @Query(value = "SELECT DISTINCT sgg.id as id, sgg.student as student, sgg.grade as grade FROM StudentGroupGrade sgg LEFT OUTER JOIN sgg.studentGroup sg" +
                    " LEFT JOIN sgg.subjectTeacherGroup sst LEFT JOIN sst.subject" +
                    " WHERE sg.id = :groupId AND sst.subject.id = :subjectId")
    Collection<StudentGradeLimitedTeacher> findStudentGradesForGroupAndSubjectByTeacher(@Param("groupId") long groupId, @Param("subjectId") long subjectId);


    @Query(value = "SELECT sgg.id as id, sgg.subjectTeacherGroup as subjectTeacherGroup, sgg.grade as grade  FROM StudentGroupGrade sgg LEFT JOIN sgg.studentGroup sg WHERE sgg.student.id = :studentId AND sgg.student.currentGroup.id = sg.id")
    Collection<StudentGradeLimitedStudent> findStudentGradesForStudent(@Param("studentId") long studentId);

    @Query(value = "SELECT sgg.grade FROM StudentGroupGrade sgg LEFT OUTER JOIN sgg.student s LEFT OUTER JOIN sgg.subjectTeacherGroup sst WHERE s.id = :studentId AND sst.subject.id = :subjectId AND sgg.studentGroup.id = :groupId")
    double findGradeForStudentBySubject(@Param("groupId") long groupId, @Param("subjectId") long subjectId, @Param("studentId") long studentId);

    @Query(value = "SELECT EXISTS(SELECT id FROM student_group_grade WHERE student_group_grade.student_id = :studentId AND student_group_grade.student_group_id = :groupId) ", nativeQuery = true)
    boolean gradesAlreadyExists(@Param("studentId") long studentId, @Param("groupId") long groupId);
}
