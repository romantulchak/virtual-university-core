package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.projection.GroupDetailsLimited;
import com.romantulchak.virtualuniversity.projection.GroupLimited;
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

    Optional<StudentGroup> findByStudents_Id(long id);

}
