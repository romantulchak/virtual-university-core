package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.StudentDTO;
import com.romantulchak.virtualuniversity.exception.PasswordNotMatchesException;
import com.romantulchak.virtualuniversity.exception.StudentNotFoundException;
import com.romantulchak.virtualuniversity.exception.StudentWithSameLoginAlreadyExistsException;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;
import com.romantulchak.virtualuniversity.repository.RoleRepository;
import com.romantulchak.virtualuniversity.repository.StudentGradeRepository;
import com.romantulchak.virtualuniversity.repository.StudentRepository;
import com.romantulchak.virtualuniversity.service.StudentService;
import com.romantulchak.virtualuniversity.utils.PasswordGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public StudentDTO create(Student student) {
        if(studentRepository.existsByLogin(student.getLogin())) {
            throw new StudentWithSameLoginAlreadyExistsException(student.getLogin());
        }
        String password = PasswordGeneratorUtil.generate();
        student.setPassword(passwordEncoder.encode(password));
        Student studentAfterSave = studentRepository.save(student);
        System.out.println(password);
        return convertToDTO(studentAfterSave);
    }

    @Override
    public StudentDTO getStudentInformation(long id) {
        return studentRepository.findStudentById(id).map(this::convertToDTO).orElseThrow(()-> new StudentNotFoundException(id));
    }

    @Override
    public void resetStudentPassword(ResetPasswordRequest resetPasswordRequest) {
        Student student = studentRepository.findStudentById(resetPasswordRequest.getUserId()).orElseThrow(() -> new StudentNotFoundException(resetPasswordRequest.getUserId()));
        if (passwordEncoder.matches(resetPasswordRequest.getOldPassword(), student.getPassword())){
            student.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
            studentRepository.save(student);
        }else {
            throw new PasswordNotMatchesException();
        }
    }

    private StudentDTO convertToDTO(Student student){
        return new StudentDTO(student);
    }

}
