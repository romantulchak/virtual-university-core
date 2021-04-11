package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.*;
import com.romantulchak.virtualuniversity.exception.GroupNotFoundException;
import com.romantulchak.virtualuniversity.exception.GroupWithNameAlreadyExistsException;
import com.romantulchak.virtualuniversity.exception.StudentAlreadyHasGroupException;
import com.romantulchak.virtualuniversity.exception.StudentNotFoundException;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.model.enumes.GradeStatus;
import com.romantulchak.virtualuniversity.projection.*;
import com.romantulchak.virtualuniversity.repository.StudentGroupGradeRepository;
import com.romantulchak.virtualuniversity.repository.StudentGroupRepository;
import com.romantulchak.virtualuniversity.repository.StudentRepository;
import com.romantulchak.virtualuniversity.repository.SubjectTeacherRepository;
import com.romantulchak.virtualuniversity.service.StudentGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;
    private final SubjectTeacherRepository subjectTeacherRepository;
    private final StudentGroupGradeRepository studentGroupGradeRepository;
    public final StudentRepository studentRepository;

    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository, SubjectTeacherRepository subjectTeacherRepository, StudentGroupGradeRepository studentGroupGradeRepository, StudentRepository studentRepository) {
        this.studentGroupRepository = studentGroupRepository;
        this.subjectTeacherRepository = subjectTeacherRepository;
        this.studentGroupGradeRepository = studentGroupGradeRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    @Override
    public void create(StudentGroup studentGroup) {
        if (!studentGroupRepository.isExistsByName(studentGroup.getName())) {
            StudentGroup studentGroupAfterSave = studentGroupRepository.saveAndFlush(studentGroup);
            setCurrentGroupForStudent(studentGroupAfterSave.getStudents(), studentGroup);
            studentGroup.getSubjectTeacherGroups().forEach(subjectTeacherGroup -> subjectTeacherGroup.setStudentGroup(studentGroupAfterSave));
            subjectTeacherRepository.saveAll(studentGroup.getSubjectTeacherGroups());
            createStudentGrades(studentGroup.getStudents(), studentGroupAfterSave.getSubjectTeacherGroups(), studentGroupAfterSave.getId());
        } else {
            throw new GroupWithNameAlreadyExistsException(studentGroup.getName());
        }
    }

    private void createStudentGrades(Collection<Student> students, Collection<SubjectTeacherGroup> subjectTeacherGroups, long studentGroupId) {
        for (Student student : students) {
            for (SubjectTeacherGroup subjectTeacherGroup : subjectTeacherGroups) {
                studentGroupGradeRepository.saveStudentGrade(student.getId(), studentGroupId, subjectTeacherGroup.getId(), GradeStatus.ACTIVE);
                updateStatusForGrade(student.getId(), GradeStatus.ACTIVE);
            }
        }
    }

    @Override
    public StudentGroupDTO findGroupForStudent(long id) {
        StudentGroup group = studentGroupRepository.findByStudents_Id(id).orElseThrow(() -> new GroupNotFoundException(id));
        int studentsCount = studentGroupRepository.groupStudentsCount(group.getId());
        return new StudentGroupDTO.Builder(group.getId(), group.getName())
                .withSpecialization(group.getSpecialization())
                .withSubjects(getSubjects(group))
                .withCounter(studentsCount)
                .build();

    }

    private List<SubjectTeacherGroupDTO> getSubjects(StudentGroup group) {
        return group.getSubjectTeacherGroups().stream().map(SubjectTeacherGroupDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addStudentToGroup(List<Student> students, long groupId) {
        StudentGroup group = studentGroupRepository.findGroupById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        List<Student> studentsWithGroup = getStudentsWithGroup(students, group);
        students.removeAll(studentsWithGroup);
        group.getStudents().addAll(students);
        studentGroupRepository.save(group);
        setCurrentGroupForStudent(students, group);
        setGrades(students, group);
        if (studentsWithGroup.size() != 0) {
            throw new StudentAlreadyHasGroupException(students);
        }
    }

    private void setGrades(List<Student> students, StudentGroup group) {
        for (Student student : students) {
            if (studentGroupGradeRepository.gradesAlreadyExists(student.getId(), group.getId())){
                updateStatusForGrade(student.getId(), GradeStatus.ACTIVE);
            }else {
                createStudentGrades(students, group.getSubjectTeacherGroups(), group.getId());
            }
        }
    }

    @Transactional
    void setCurrentGroupForStudent(Collection<Student> students, StudentGroup group) {
        for (Student student : students) {
            studentRepository.updateCurrentGroup(group.getId(), student.getId());
        }
    }

    private List<Student> getStudentsWithGroup(List<Student> students, StudentGroup group) {
        List<Student> studentsWithGroup = new ArrayList<>();
        group.getStudents().forEach(student -> {
            if (students.contains(student)) {
                studentsWithGroup.add(student);
            }
        });
        return studentsWithGroup;
    }

    @Override
    public Collection<StudentGroupDTO> findAllGroups() {
        return studentGroupRepository.allGroups()
                .stream()
                .map(group -> convertToDTO(group.getId(), group.getName(), group.getSemester()))
                .collect(Collectors.toList());
    }

    @Override
    public StudentGroupDTO findGroupDetailsById(long id) {
        StudentGroup studentGroup = studentGroupRepository.groupByIdWithSubjectsAndStudents(id).orElseThrow(() -> new GroupNotFoundException(id));
        return new StudentGroupDTO.Builder(studentGroup.getId(), studentGroup.getName())
                .withStudents(studentGroup.getStudents())
                .withSubjects(getSubjects(studentGroup))
                .withSpecialization(studentGroup.getSpecialization())
                .withCounter(studentGroup.getStudents().size())
                .build();
    }

    @Override
    public void addSubjectsToGroup(Collection<SubjectTeacherGroup> subjects, long groupId) {
        StudentGroup studentGroup = studentGroupRepository.groupByIdWithSubjectsAndStudents(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        subjects.forEach(x -> subjectTeacherRepository.saveSubjectTeacherGroup(studentGroup.getId(), x.getSubject().getId(), x.getTeacher().getId()));
    }

    @Override
    public void delete(long groupId) {
        studentGroupRepository.deleteById(groupId);
    }

    @Override
    public Collection<StudentGroupDTO> findGroupsForTeacher(long teacherId) {
        return studentGroupRepository.findGroupsForTeacher(teacherId).stream()
                .map(studentGroup -> convertToDTO(studentGroup.getId(), studentGroup.getName(), studentGroup.getSemester()))
                .sorted()
                .collect(Collectors.toList());

    }

    @Override
    public StudentGroupDTO findGroupSubjectsForTeacher(long groupId, long teacherId) {
        GroupStudentsLimited groupForTeacher = studentGroupRepository.groupDetailsForTeacher(groupId);
        Collection<SubjectTeacherGroupDTO> subjectTeacherGroups = getSubjectTeacherGroupDTOS(groupId, teacherId);

        return new StudentGroupDTO.Builder(groupForTeacher.getId(), groupForTeacher.getName())
                .withSubjects(subjectTeacherGroups)
                .withSpecialization(groupForTeacher.getSpecialization())
                .build();
    }
    @Transactional
    @Override
    public void deleteStudentFromGroup(long groupId, long studentId) {
        StudentGroup studentGroup = studentGroupRepository.findGroupById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        Student student = studentRepository.findStudentById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        updateStatusForGrade(studentId, GradeStatus.IN_ACTIVE);
        studentGroup.getStudents().remove(student);
        studentGroupRepository.save(studentGroup);
    }


    private void updateStatusForGrade(long studentId, GradeStatus status) {
        Collection<StudentGradeLimitedStudent> studentGradesForStudent = studentGroupGradeRepository.findStudentGradesForStudent(studentId);
        for (StudentGradeLimitedStudent studentGradeLimitedStudent : studentGradesForStudent) {
            studentGroupGradeRepository.setStatusForGrade(status,studentGradeLimitedStudent.getId());
        }
    }

    private Collection<SubjectTeacherGroupDTO> getSubjectTeacherGroupDTOS(long id, long teacherId) {
        return subjectTeacherRepository.subjectsInGroupForTeacher(id, teacherId).stream()
                .map(subject -> new SubjectTeacherGroupDTO(subject.getId(),
                        subject.getName(),
                        subject.getType()))
                .collect(Collectors.toList());
    }

    private StudentGroupDTO convertToDTO(long id, String name, Semester semester) {
        return new StudentGroupDTO.Builder(id, name)
                .withSemester(semester)
                .build();
    }


}
