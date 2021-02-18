package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.model.enumes.ERole;
import com.romantulchak.virtualuniversity.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TeacherDetailsServiceImpl implements UserDetailsService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherDetailsServiceImpl(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Teacher with login not found"));
        return UserDetailsImpl.build(teacher, teacher.getRoles(), ERole.ROLE_TEACHER);
    }
}
