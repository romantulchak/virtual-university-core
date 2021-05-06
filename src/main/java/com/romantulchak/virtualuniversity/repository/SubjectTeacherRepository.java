package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import com.romantulchak.virtualuniversity.projection.SubjectTeacherLimited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
@Repository
public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacherGroup, Long> {

    @Modifying
    @Query(value = "INSERT INTO subject_teacher_group (student_group_id, subject_id, teacher_id, semester_id)  VALUES (:groupId,:subjectId, :teacherId, :semesterId)", nativeQuery = true)
    void saveSubjectTeacherGroup(@Param("groupId") long groupId, @Param("subjectId") long subjectId, @Param("teacherId") long teacherId, @Param("semesterId") long semesterId);

    @Query(value = "SELECT DISTINCT s.id as id, s.name as name, s.type as type FROM SubjectTeacherGroup stg LEFT OUTER JOIN stg.subject s WHERE stg.studentGroup.id = :groupId AND stg.teacher.id = :teacherId")
    Collection<SubjectTeacherLimited> subjectsInGroupForTeacher(@Param("groupId") long groupId, @Param("teacherId") long teacherId);

    @Query(value = "SELECT stg FROM SubjectTeacherGroup stg WHERE stg.studentGroup.id = :groupId")
    Collection<SubjectTeacherGroup> findSubjectsForGroup(@Param("groupId") long groupId);

    SubjectTeacherGroup findBySubject_IdAndTeacher_IdAndStudentGroup_IdAndSemester_Id(long subjectId, long teacherId, long studentGroupId, long semesterId);
}
