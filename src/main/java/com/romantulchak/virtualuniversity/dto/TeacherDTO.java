package com.romantulchak.virtualuniversity.dto;

import com.romantulchak.virtualuniversity.model.Teacher;

public class TeacherDTO extends UserDTO{

    public TeacherDTO(){}
    public TeacherDTO(Teacher teacher) {
        super(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getPrivateEmail(), teacher.getEmail(),teacher.getGender());
    }
}
