package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.exception.StudentWithSameLoginAlreadyExists;
import com.romantulchak.virtualuniversity.model.Role;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;
import com.romantulchak.virtualuniversity.payload.request.RegistrationRequest;
import com.romantulchak.virtualuniversity.repository.RoleRepository;
import com.romantulchak.virtualuniversity.repository.StudentGradeRepository;
import com.romantulchak.virtualuniversity.repository.StudentRepository;
import com.romantulchak.virtualuniversity.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentGradeRepository studentGradeRepository;
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public StudentServiceImpl(StudentGradeRepository studentGradeRepository, StudentRepository studentRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.studentGradeRepository = studentGradeRepository;
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Student studentGrade() {

        return studentRepository.findById(7L).orElse(null);
    }

    @Override
    public void createStudent(Student student) {
        if(studentRepository.existsByLogin(student.getLogin())) {
            throw new StudentWithSameLoginAlreadyExists(student.getLogin());
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
           
        studentRepository.save(student);
    }
}
