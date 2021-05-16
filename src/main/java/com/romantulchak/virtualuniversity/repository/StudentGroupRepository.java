package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.projection.*;
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

    @Query(value = "SELECT DISTINCT s FROM StudentGroup s JOIN FETCH s.subjectTeacherGroups as sb LEFT JOIN FETCH s.students as ss WHERE s.id = :id AND s.semester.id = sb.semester.id")
    Optional<StudentGroup> groupByIdWithSubjectsAndStudents(@Param("id") long id);

    /**
     * Student Group with students and subjects
     * @param id group id
     * @return StudentGroup
     */
    //TODO: fix JOIN FETCH
    @Query(value = "SELECT s FROM StudentGroup s LEFT OUTER JOIN s.students ss LEFT OUTER JOIN s.subjectTeacherGroups sst WHERE s.id = :id")
    Optional<StudentGroup> findGroupById(@Param("id") long id);

    @Query(value = "SELECT ss" +
                    " FROM StudentGroup ss LEFT JOIN ss.specialization as sp " +
                    "LEFT OUTER JOIN ss.students st JOIN FETCH ss.students WHERE st.id = :id")
    Optional<StudentGroup> findStudentGroupByStudentId(@Param("id") long id);

    @Query(value = "SELECT DISTINCT sg FROM StudentGroup sg LEFT OUTER JOIN sg.subjectTeacherGroups as stg LEFT OUTER JOIN stg.teacher as t WHERE t.id = :id AND sg.semester.id = stg.semester.id")
    Collection<GroupLimited> findGroupsForTeacher(@Param("id") long teacherId);
    
    @Query(value = "SELECT sg.id FROM StudentGroup sg LEFT JOIN sg.students ss WHERE ss.id = :studentId")
    long findGroupIdByStudentId(@Param("studentId") long id);

    @Query(value = "SELECT EXISTS( SELECT id FROM subject_teacher_group WHERE subject_teacher_group.teacher_id = :teacherId)", nativeQuery = true)
    boolean hasAccess(@Param("teacherId") long id);

    @Query(value = "SELECT s.id as id, s.name as name, s.specialization as specialization, s.semester as semester FROM StudentGroup s  LEFT JOIN s.specialization ss WHERE s.id = :groupId")
    Optional<GroupStudentsLimited> groupDetailsForTeacher(@Param("groupId") long groupId);

    @Query(value = "SELECT count(st) FROM StudentGroup s LEFT OUTER JOIN s.students st WHERE s.id = :groupId")
    int groupStudentsCount(@Param("groupId") long groupId);


}
