package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.model.enumes.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsById(long id);
    Collection<Teacher> findAllBySubjects_Id(long subjectId);

    @Query(value = "SELECT t FROM Teacher t LEFT OUTER JOIN t.roles tr WHERE tr.name = :role")
    List<Teacher> findTeachersByRole(@Param("role") RoleType role);
}
