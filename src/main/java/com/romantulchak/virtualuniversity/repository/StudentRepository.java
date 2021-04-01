package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.projection.StudentLimited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT s.id as id, s.firstName as firstName, s.lastName as lastName FROM Student s LEFT OUTER JOIN s.studentGroups sg WHERE sg.id = :groupId")
    Collection<StudentLimited> findStudentByGroupId(@Param("groupId") long id);
  }
