package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.TeacherDTO;
import com.romantulchak.virtualuniversity.exception.PasswordNotMatchesException;
import com.romantulchak.virtualuniversity.exception.TeacherNotFoundException;
import com.romantulchak.virtualuniversity.exception.TeacherWithSameLoginAlreadyExistsException;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;
import com.romantulchak.virtualuniversity.repository.SubjectRepository;
import com.romantulchak.virtualuniversity.repository.TeacherRepository;
import com.romantulchak.virtualuniversity.service.TeacherService;
import com.romantulchak.virtualuniversity.utils.AlbumNumberGenerator;
import com.romantulchak.virtualuniversity.utils.EmailSender;
import com.romantulchak.virtualuniversity.utils.PasswordGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, PasswordEncoder passwordEncoder, SubjectRepository subjectRepository, EmailSender emailSender) {
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
        this.subjectRepository = subjectRepository;
        this.emailSender = emailSender;
    }


    @Override
    public void create(Teacher teacher) {
        if (teacherRepository.existsByLogin(teacher.getLogin())){
            throw new TeacherWithSameLoginAlreadyExistsException(teacher.getLogin());
        }
        String password = PasswordGeneratorUtil.generate();
        teacher.setPassword(passwordEncoder.encode(password));
        teacher.setNumberIdentifier("WT" + AlbumNumberGenerator.generateAlbumNumber());
        emailSender.sendMail(teacher.getEmail(), "Your data", String.format("Your login: %s Your password: %s", teacher.getLogin(), password));
        teacherRepository.save(teacher);
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Teacher teacher = teacherRepository.findById(resetPasswordRequest.getUserId()).orElseThrow(()-> new TeacherNotFoundException(resetPasswordRequest.getUserId()));
        if (passwordEncoder.matches(resetPasswordRequest.getOldPassword(), teacher.getPassword())){
            teacher.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
            teacherRepository.save(teacher);
        }else {
            throw new PasswordNotMatchesException();
        }
    }

    @Override
    public Collection<TeacherDTO> findAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO findTeacherById(long id) {
        return teacherRepository
                .findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

    @Override
    public void addSubjectsToTeacher(long id, Collection<Subject> subjects) {
        if (!teacherRepository.existsById(id)){
            throw new TeacherNotFoundException(id);
        }
        subjects.forEach(subject -> subjectRepository.saveSubjectTeacher(id, subject.getId()));
    }

    @Override
    public Collection<TeacherDTO> findTeachersBySubject(long subjectId) {
        return teacherRepository.findAllBySubjects_Id(subjectId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TeacherDTO convertToDTO(Teacher teacher){
        return new TeacherDTO(teacher);
    }

}
