package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.enumes.ERole;
import com.romantulchak.virtualuniversity.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailsServiceImpl implements UserDetailsService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentDetailsServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByLogin(username).orElseThrow(()-> new UsernameNotFoundException("User not found exception"));
        return UserDetailsImpl.build(student, student.getRoles(), ERole.STUDENT);
    }
}
