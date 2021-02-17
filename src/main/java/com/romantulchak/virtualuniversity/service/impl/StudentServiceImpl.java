package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;
import com.romantulchak.virtualuniversity.repository.StudentGradeRepository;
import com.romantulchak.virtualuniversity.repository.StudentRepository;
import com.romantulchak.virtualuniversity.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentGradeRepository studentGradeRepository;
    private final StudentRepository studentRepository;
    @Autowired
    public StudentServiceImpl(StudentGradeRepository studentGradeRepository, StudentRepository studentRepository){
        this.studentGradeRepository = studentGradeRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Student studentGrade() {

        Student student = studentRepository.findById(1L).orElse(null);
        return student;
    }
}
