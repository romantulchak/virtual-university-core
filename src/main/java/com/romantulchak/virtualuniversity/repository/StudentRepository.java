package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findByLogin(String login);

    boolean existsByLogin(String login);

    Optional<Student> findStudentById(long id);
    Collection<Student> findStudentByFirstNameAndLastName(String firstName, String lastName);

    //TODO: make projection
    @Query(value = "SELECT new Student(s.id, s.firstName, s.lastName) FROM Student s LEFT OUTER JOIN s.studentGroups sg WHERE sg.id IS NULL ")
    Collection<Student> findStudentWithoutGroup();
  }
