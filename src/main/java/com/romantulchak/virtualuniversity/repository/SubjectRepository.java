package com.romantulchak.virtualuniversity.repository;

import antlr.actions.python.CodeLexer;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.SubjectFile;
import com.romantulchak.virtualuniversity.model.enumes.SubjectType;
import com.romantulchak.virtualuniversity.projection.SubjectFileProjection;
import com.romantulchak.virtualuniversity.projection.SubjectLimited;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Transactional
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query(value = "SELECT * FROM subject LEFT OUTER JOIN subject_teacher st on subject.id = st.subject_id WHERE st.teacher_id <> :teacherId", nativeQuery = true)
    Collection<Subject> findAvailableSubjects(@Param("teacherId") long id);

    Page<Subject> findAllByTeachers_Id(long teachers_id, Pageable pageable);

    @Query(value = "SELECT * FROM subject LEFT OUTER JOIN specialization_subject ss on subject.id = ss.subject_id WHERE specialization_id = :id ", nativeQuery = true)
    Collection<Subject> findAllForSpecialization(@Param("id") long id);

    @Query(value = "SELECT * FROM subject sb WHERE NOT EXISTS(SELECT * FROM subject_teacher_group join subject s on s.id = subject_teacher_group.subject_id WHERE sb.id = s.id AND subject_teacher_group.student_group_id = :id)", nativeQuery = true)
    Collection<Subject> findAvailableSubjectsForGroup(@Param("id") long id);

    @Query(value = "SELECT sub.id as id, sub.name as name, sub.type as type FROM Subject sub")
    Collection<SubjectLimited> findAllWithoutTeachers();

    @Query(value = "SELECT id, name, type FROM subject WHERE subject.id NOT IN (SELECT st.subject_id FROM subject_teacher st WHERE teacher_id = :id)", nativeQuery = true)
    Collection<SubjectLimited> findAvailableSubjectsForTeacher(@Param("id") long id);

    @Modifying
    @Query(value = "INSERT INTO subject_teacher (subject_id, teacher_id) VALUES (:subjectId, :teacherId)", nativeQuery = true)
    void saveSubjectTeacher(@Param("teacherId") long teacherId, @Param("subjectId") long subjectId);

    boolean existsByNameAndType(String name, SubjectType type);

    @Query(value = "SELECT s FROM Subject s LEFT JOIN FETCH s.files WHERE s.id = :id")
    Optional<Subject> findSubjectFiles(@Param("id")long subjectId);

    @Query(value = "SELECT local_path FROM subject_files WHERE name = :filename", nativeQuery = true)
    Optional<String> findLocalPathToFile(@Param("filename") String filename);

    @Query(value = "SELECT s FROM Subject s LEFT OUTER JOIN s.teachers")
    Collection<Subject> findAllSubjectsWithTeachers();

    @Query(value = "SELECT * FROM subject sb WHERE NOT EXISTS(SELECT * FROM subject_teacher_group join subject s on s.id = subject_teacher_group.subject_id WHERE sb.id = s.id AND subject_teacher_group.semester_id = :semesterId)", nativeQuery = true)
    Collection<Subject> findAllSubjectsWithTeachersBySemester(@Param("semesterId") long semesterId);

    @Query(value = "SELECT EXISTS (SELECT id FROM subject LEFT JOIN subject_teacher st on subject.id = st.subject_id WHERE st.teacher_id = :teacherId AND subject_id = :subjectId)", nativeQuery = true)
    boolean hasAccessToSubject(@Param("teacherId") long teacherId, @Param("subjectId") long subjectId);
}
