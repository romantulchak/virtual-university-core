package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByLogin(String login);

    boolean existsByLogin(String login);
    Collection<Teacher> findAllBySubjects_Id(long subjectId);

}
