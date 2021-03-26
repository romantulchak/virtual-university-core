package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query(value = "SELECT * FROM subject LEFT OUTER JOIN subject_teacher st on subject.id = st.subject_id WHERE st.teacher_id <> :teacherId", nativeQuery = true)
    Collection<Subject> findAvailableSubjects(@Param("teacherId") long id);

    Collection<Subject> findAllByTeachers_Id(long teachers_id);

    @Query(value = "SELECT * FROM subject LEFT OUTER JOIN specialization_subject ss on subject.id = ss.subject_id WHERE specialization_id = :id ", nativeQuery = true)
    Collection<Subject> findAllForSpecialization(@Param("id") long id);

    @Query(value = "SELECT * FROM subject sb WHERE NOT EXISTS(SELECT * FROM subject_teacher_group join subject s on s.id = subject_teacher_group.subject_id WHERE sb.id = s.id AND subject_teacher_group.student_group_id = :id)", nativeQuery = true)
    Collection<Subject> findAvailableSubjectsForGroup(@Param("id") long id);


}
