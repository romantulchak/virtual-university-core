package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    boolean existsByName(String name);

    Optional<Semester> findFirstBySpecialization_IdAndSemesterNumber(long specialization_id, int semesterNumber);
    Collection<Semester> findAllBySpecialization_Id(long specializationId);

    @Query(value = "SELECT * FROM semester LEFT OUTER JOIN specialization_semester ss on semester.id = ss.semester_id WHERE specialization_id = :id",nativeQuery = true)
    Collection<Semester> findSemestersForSpecialization(@Param("id") long id);
}
