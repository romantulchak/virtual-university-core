package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.projection.GroupDetailsLimited;
import com.romantulchak.virtualuniversity.projection.GroupLimited;
import com.romantulchak.virtualuniversity.projection.GroupStudentsLimited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {


    @Query(value = "SELECT EXISTS (SELECT * FROM student_group sg WHERE name = :name)", nativeQuery = true)
    boolean isExistsByName(@Param("name") String name);

    @Query(value = "SELECT s.id as id, s.name as name, s.semester as semester FROM StudentGroup s")
    Collection<GroupLimited> allGroups();

    @Query(value = "SELECT DISTINCT s FROM StudentGroup s JOIN FETCH s.subjectTeacherGroups as sb LEFT JOIN FETCH s.students as ss WHERE s.id = :id")
    Optional<StudentGroup> groupByIdWithSubjectsAndStudents(@Param("id") long id);

    @Query(value = "SELECT s FROM StudentGroup  s JOIN FETCH s.students WHERE s.id = :id")
    Optional<StudentGroup> findGroupById(@Param("id") long id);

//    @Query(value = "SELECT id, name, count(gs) as count FROM student_group LEFT JOIN group_student gs on student_group.id = gs.group_id WHERE id = :id GROUP BY id, name", nativeQuery = true)
//    Optional<GroupStudentsLimited> findByStudents_Id(@Param("id") long id);

    @Query(value = "SELECT ss, count(st) as count, ss.name as name, ss.id as id, ss.specialization as specialization " +
                    "FROM StudentGroup ss LEFT JOIN FETCH ss.specialization as sp " +
                    "LEFT OUTER JOIN ss.students st WHERE ss.id = :id GROUP BY ss.id, ss.name, sp.id")
    Optional<GroupStudentsLimited> findByStudents_Id(@Param("id") long id);

    @Query(value = "SELECT DISTINCT sg FROM StudentGroup sg LEFT OUTER JOIN sg.subjectTeacherGroups as stg LEFT OUTER JOIN stg.teacher as t WHERE t.id = :id")
    Collection<GroupLimited> findGroupsForTeacher(@Param("id") long teacherId);
    
    @Query(value = "SELECT id FROM student_group LEFT JOIN group_student gs on student_group.id = gs.group_id WHERE gs.student_id = :studentId", nativeQuery = true)
    long findGroupIdByStudentId(@Param("studentId") long id);


    @Query(value = "SELECT EXISTS( SELECT id FROM subject_teacher_group WHERE subject_teacher_group.teacher_id = :teacherId)", nativeQuery = true)
    boolean hasAccess(@Param("teacherId") long id);
}
