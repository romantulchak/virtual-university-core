package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.TeacherDTO;
import com.romantulchak.virtualuniversity.exception.PasswordNotMatchesException;
import com.romantulchak.virtualuniversity.exception.TeacherNotFoundException;
import com.romantulchak.virtualuniversity.exception.TeacherWithSameLoginAlreadyExistsException;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;
import com.romantulchak.virtualuniversity.repository.TeacherRepository;
import com.romantulchak.virtualuniversity.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void create(Teacher teacher) {
        if (teacherRepository.existsByLogin(teacher.getLogin())){
            throw new TeacherWithSameLoginAlreadyExistsException(teacher.getLogin());
        }
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacherRepository.save(teacher);
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Teacher teacher = teacherRepository.findById(resetPasswordRequest.getUserId()).orElseThrow(()-> new TeacherNotFoundException(resetPasswordRequest.getUserId()));
        if (passwordEncoder.matches(teacher.getPassword(), resetPasswordRequest.getOldPassword())) {
            teacher.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
            teacherRepository.save(teacher);
        }else {
            throw new PasswordNotMatchesException();
        }
    }

    @Override
    public Collection<TeacherDTO> findAllTeachers() {
        return teacherRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private TeacherDTO convertToDTO(Teacher teacher){
        return new TeacherDTO(teacher);
    }

}
