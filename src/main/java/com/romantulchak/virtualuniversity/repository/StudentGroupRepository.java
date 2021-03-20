package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {


    @Query(value = "SELECT EXISTS (SELECT * FROM student_group sg WHERE name = :name)", nativeQuery = true)
    boolean isExistsByName(@Param("name") String name);


}
