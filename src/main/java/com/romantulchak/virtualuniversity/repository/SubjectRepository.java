package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query(value = "SELECT * FROM subject LEFT OUTER JOIN subject_teacher st on subject.id = st.subject_id WHERE st.teacher_id <> :teacherId", nativeQuery = true)
    Collection<Subject> findAvailableSubjects(@Param("teacherId") long id);
}
