package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.exception.TeacherWithSameLoginAlreadyExistsException;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.repository.TeacherRepository;
import com.romantulchak.virtualuniversity.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void createTeacher(Teacher teacher) {
        if (teacherRepository.existsByLogin(teacher.getLogin())){
            throw new TeacherWithSameLoginAlreadyExistsException(teacher.getLogin());
        }
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacherRepository.save(teacher);
    }
}
