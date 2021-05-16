package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.projection.StudentDataLimited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findByLogin(String login);

    boolean existsByLogin(String login);

    Optional<Student> findStudentById(long id);

    Collection<Student> findStudentByFirstNameAndLastName(String firstName, String lastName);

    @Query(value = "SELECT s.id as id, s.firstName as firstName, s.lastName as lastName, s.numberIdentifier as numberIdentifier FROM Student s LEFT OUTER JOIN s.studentGroup sg WHERE sg.id IS NULL ")
    Collection<StudentDataLimited> findStudentsWithoutGroup();

    @Query(value = "SELECT s.id as id, s.firstName as firstName, s.lastName as lastName, s.numberIdentifier as numberIdentifier FROM Student s WHERE s.id = :studentId")
    Optional<StudentDataLimited> findStudentInformation(@Param("studentId") long studentId);

    @Modifying
    @Query(value = "UPDATE  Student s SET s.studentGroup.id = :currentGroupId WHERE s.id = :studentId")
    void updateCurrentGroup(@Param("currentGroupId") long currentGroupId, @Param("studentId") long studentId);

    @Query(value = "SELECT s.currentSemester FROM Student s WHERE s.id = :studentId")
    int getCurrentStudentSemester(@Param("studentId") long id);

}
