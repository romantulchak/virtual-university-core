package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.StudentGroupGradeDTO;
import com.romantulchak.virtualuniversity.exception.StudentGroupGradeEmptyException;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.model.enumes.GradeStatus;
import com.romantulchak.virtualuniversity.projection.StudentGradeLimitedStudent;
import com.romantulchak.virtualuniversity.repository.StudentGroupGradeRepository;
import com.romantulchak.virtualuniversity.repository.StudentGroupRepository;
import com.romantulchak.virtualuniversity.repository.StudentRepository;
import com.romantulchak.virtualuniversity.repository.SubjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentGroupGradeServiceImplTest {

    @Mock
    private StudentGroupGradeRepository studentGroupGradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentGroupRepository studentGroupRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private StudentGroupGradeServiceImpl studentGroupGradeService;



    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        studentGroupGradeService  = new StudentGroupGradeServiceImpl(studentGroupGradeRepository, studentRepository);
    }

    @Test
    public void setGrade() {
        StudentGroupGrade studentGroupGrade = Mockito.spy(StudentGroupGrade.class);
        studentGroupGrade.setId(1);
        studentGroupGrade.setGrade(5);
        Collection<StudentGroupGrade> studentGroupGrades = new ArrayList<>(){{
           add(studentGroupGrade);
        }};
        studentGroupGradeService.setGrade(studentGroupGrades);
        verify(studentGroupGradeService, times(1)).setGrade(studentGroupGrades);
    }
    @Test(expected = StudentGroupGradeEmptyException.class)
    public void setGradeEmpty() {
        Collection<StudentGroupGrade> studentGroupGrades = new ArrayList<>();
        studentGroupGradeService.setGrade(studentGroupGrades);
    }

    @Test
    public void findStudentGradesBySubjectAndGroupForTeacher() {
    }

    @Test
    public void findStudentGrades() {
        long studentId = 172;
        when(studentRepository.existsById(studentId)).thenReturn(true);
        StudentGroup studentGroup = new StudentGroup();
        Student student = new Student(studentId, "Test", "test", "");
        student.setId(studentId);
        ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();
        StudentGradeLimitedStudent projection = projectionFactory.createProjection(StudentGradeLimitedStudent.class);
        SubjectTeacherGroup subjectTeacherGroup = new SubjectTeacherGroup();
        subjectTeacherGroup.setId(1);
        projection.setId(1);
        projection.setSubjectTeacherGroup(subjectTeacherGroup);
        projection.setGrade(3);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentGroupGradeRepository.findAllGradesForStudent(student.getId())).thenReturn(Collections.singletonList(projection));
        Collection<StudentGroupGradeDTO> studentGrades = studentGroupGradeService.findStudentGrades(studentId, 10);
        assertNotNull(studentGrades);
        assertEquals(1, studentGrades.size());
    }
    @Test
    public void findStudentGradesEmpty() {
        long studentId = 172;
        when(studentRepository.existsById(studentId)).thenReturn(true);
        Collection<StudentGradeLimitedStudent> students = new ArrayList<>();
        when(studentGroupGradeRepository.findAllGradesForStudent(studentId)).thenReturn(students);
        assertEquals(0, students.size());
    }
    @Test
    public void findGradeForStudentBySubject() {
        Student student = new Student();
        student.setId(3);
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(1);
        Subject subject = new Subject();
        subject.setId(4);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentGroupRepository.save(studentGroup)).thenReturn(studentGroup);
        when(subjectRepository.save(subject)).thenReturn(subject);
        assertEquals(1, studentGroup.getId());
        assertEquals(3, student.getId());
        assertEquals(4, subject.getId());
        studentGroupGradeRepository.saveStudentGrade(student.getId(), studentGroup.getId(), subject.getId(), GradeStatus.ACTIVE, studentGroup.getSemester().getId());

        StudentGroupGrade studentGroupGrade = new StudentGroupGrade();
        studentGroupGrade.setStudent(student);
        studentGroupGrade.setStudentGroup(studentGroup);
        studentGroupGrade.setId(5);
        studentGroupGrade.setGrade(5);
        SubjectTeacherGroup subjectTeacherGroup = new SubjectTeacherGroup();
        subjectTeacherGroup.setSubject(subject);
        studentGroupGrade.setSubjectTeacherGroup(subjectTeacherGroup);
        when(studentGroupGradeRepository.save(studentGroupGrade)).thenReturn(studentGroupGrade);
        assertEquals(5, studentGroupGrade.getId());

        double grade = 3.5;
        studentGroupGradeRepository.updateGrade(studentGroupGrade.getId(), grade);
        double gradeForStudentBySubject = studentGroupGradeService.findGradeForStudentBySubject(studentGroup.getId(), student.getId(), subject.getId(), studentGroup.getSemester().getId());
        assertEquals(3.5, gradeForStudentBySubject, 0);
    }
}
