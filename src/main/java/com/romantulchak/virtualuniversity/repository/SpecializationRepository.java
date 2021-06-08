package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    Collection<Specialization> findAllByStudents_id(@Param("studentId") long studentId);
    Collection<Specialization> findAllByTeachers_Id(@Param("teacherId") long teacherId);

    @Query(value = "SELECT s FROM Specialization s JOIN FETCH s.subjects WHERE s.id = :specializationId")
    Optional<Specialization> findSpecializationById(@Param("specializationId") long id);
}
