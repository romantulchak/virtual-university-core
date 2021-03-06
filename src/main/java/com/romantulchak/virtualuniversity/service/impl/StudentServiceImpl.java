package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.StudentDTO;
import com.romantulchak.virtualuniversity.exception.PasswordNotMatchesException;
import com.romantulchak.virtualuniversity.exception.RoleNotFoundException;
import com.romantulchak.virtualuniversity.exception.StudentNotFoundException;
import com.romantulchak.virtualuniversity.exception.StudentWithSameLoginAlreadyExistsException;
import com.romantulchak.virtualuniversity.model.NotificationBox;
import com.romantulchak.virtualuniversity.model.Role;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.enumes.RoleType;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;
import com.romantulchak.virtualuniversity.projection.StudentDataLimited;
import com.romantulchak.virtualuniversity.repository.NotificationBoxRepository;
import com.romantulchak.virtualuniversity.repository.RoleRepository;
import com.romantulchak.virtualuniversity.repository.StudentRepository;
import com.romantulchak.virtualuniversity.service.RoleService;
import com.romantulchak.virtualuniversity.service.StudentService;
import com.romantulchak.virtualuniversity.utils.AlbumNumberGenerator;
import com.romantulchak.virtualuniversity.utils.EmailSender;
import com.romantulchak.virtualuniversity.utils.PasswordGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    private final NotificationBoxRepository notificationBoxRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              PasswordEncoder passwordEncoder,
                              EmailSender emailSender,
                              NotificationBoxRepository notificationBoxRepository,
                              RoleRepository roleRepository){
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
        this.notificationBoxRepository = notificationBoxRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void create(Student student) {
        if(studentRepository.existsByLogin(student.getLogin())) {
            throw new StudentWithSameLoginAlreadyExistsException(student.getLogin());
        }
        String password = PasswordGeneratorUtil.generate();
        student.setPassword(passwordEncoder.encode(password));
        student.setNumberIdentifier(AlbumNumberGenerator.generateAlbumNumber(student.getFirstName(), student.getLastName()));
        NotificationBox notificationBox = notificationBoxRepository.save(new NotificationBox());
        student.setNotificationBox(notificationBox);
        Role role = roleRepository.findByName(RoleType.ROLE_STUDENT).orElseThrow(() -> new RoleNotFoundException(RoleType.ROLE_STUDENT.name()));
        if (student.getRoles() == null){
            student.setRoles(new HashSet<>());
        }
        student.getRoles().add(role);
        studentRepository.save(student);
        emailSender.sendMail(student.getEmail(), "Your data", String.format("Your login: %s Your password: %s", student.getLogin(), password));
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

    @Override
    public Collection<StudentDTO> findStudentByName(String firstName, String lastName) {
        return studentRepository.findStudentByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public Collection<StudentDTO> findStudentsWithoutGroup() {
        return studentRepository.findStudentsWithoutGroup()
                .stream()
                .map(student-> new StudentDTO(student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getNumberIdentifier()))
                .collect(Collectors.toList());
    }

    @Override
    public int getCurrentStudentSemester(long id) {
        if(studentRepository.existsById(id)) {
            return studentRepository.getCurrentStudentSemester(id);
        }
        throw new StudentNotFoundException(id);
    }

    private StudentDTO convertToDTO(Student student){
        return new StudentDTO(student);
    }

}
