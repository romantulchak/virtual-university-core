package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacherGroup, Long> {


    @Modifying
    @Query(value = "INSERT INTO subject_teacher_group (student_group_id, subject_id, teacher_id)  VALUES (:groupId,:subjectId, :teacherId)", nativeQuery = true)
    void saveSubjectTeacherGroup(@Param("groupId") long groupId, @Param("subjectId") long subjectId, @Param("teacherId") long teacherId);
}
