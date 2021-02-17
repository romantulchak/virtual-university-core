package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findByLogin(String login);

    boolean existsByLogin(String login);

}
