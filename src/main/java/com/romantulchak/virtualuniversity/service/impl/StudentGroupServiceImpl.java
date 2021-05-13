package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.*;
import com.romantulchak.virtualuniversity.exception.*;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.model.enumes.GradeStatus;
import com.romantulchak.virtualuniversity.projection.*;
import com.romantulchak.virtualuniversity.repository.*;
import com.romantulchak.virtualuniversity.service.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;
    private final SubjectTeacherRepository subjectTeacherRepository;
    private final StudentGroupGradeRepository studentGroupGradeRepository;
    private final StudentRepository studentRepository;
    private final ScheduleServiceImpl scheduleService;
    private final SemesterRepository semesterRepository;

    @Autowired
    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository,
                                   SubjectTeacherRepository subjectTeacherRepository,
                                   StudentGroupGradeRepository studentGroupGradeRepository,
                                   StudentRepository studentRepository,
                                   ScheduleServiceImpl scheduleService,
                                   SemesterRepository semesterRepository) {
        this.studentGroupRepository = studentGroupRepository;
        this.subjectTeacherRepository = subjectTeacherRepository;
        this.studentGroupGradeRepository = studentGroupGradeRepository;
        this.studentRepository = studentRepository;
        this.scheduleService = scheduleService;
        this.semesterRepository = semesterRepository;
    }
    //TODO: move semester creation here
    @Transactional
    @Override
    public void create(StudentGroup studentGroup) {
        if (!studentGroupRepository.isExistsByName(studentGroup.getName())) {
            addSemesterToGroupSemesters(studentGroup);
            StudentGroup studentGroupAfterSave = studentGroupRepository.saveAndFlush(studentGroup);
            setCurrentGroupForStudent(studentGroupAfterSave.getStudents(), studentGroup);
            studentGroup.getSubjectTeacherGroups().forEach(subjectTeacherGroup -> {
                subjectTeacherGroup.setStudentGroup(studentGroupAfterSave);
                subjectTeacherGroup.setSemester(studentGroupAfterSave.getSemester());
            });
            subjectTeacherRepository.saveAll(studentGroup.getSubjectTeacherGroups());
            createStudentGrades(studentGroup.getStudents(), studentGroupAfterSave.getSubjectTeacherGroups(), studentGroupAfterSave.getId());
            createSchedule(studentGroupAfterSave);
        } else {
            throw new GroupWithNameAlreadyExistsException(studentGroup.getName());
        }
    }

    private void addSemesterToGroupSemesters(StudentGroup group) {
        if (group.getSemesters() == null) {
            group.setSemesters(new ArrayList<>());
        }
        group.getSemesters().add(group.getSemester());
    }

    private void createSchedule(StudentGroup studentGroup) {
        Schedule schedule = new Schedule();
//        schedule.setGroup(studentGroup);
        schedule.setSemester(studentGroup.getSemester());
        scheduleService.create(schedule);
    }

    private void createStudentGrades(Collection<Student> students, Collection<SubjectTeacherGroup> subjectTeacherGroups, long studentGroupId) {
        for (Student student : students) {
            for (SubjectTeacherGroup subjectTeacherGroup : subjectTeacherGroups) {
                studentGroupGradeRepository.saveStudentGrade(student.getId(), studentGroupId, subjectTeacherGroup.getId(), GradeStatus.ACTIVE, subjectTeacherGroup.getStudentGroup().getSemester().getId());
                updateStatusForGrade(student.getId(), GradeStatus.ACTIVE);
            }
        }
    }

    @Override
    public void addSubjectsToGroup(Collection<SubjectTeacherGroup> subjects, long groupId) {
        StudentGroup studentGroup = studentGroupRepository
                .groupByIdWithSubjectsAndStudents(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        addSubjectsToGroup(subjects, groupId, studentGroup);
    }

    private void addSubjectsToGroup(Collection<SubjectTeacherGroup> subjects, long groupId, StudentGroup studentGroup) {
        Collection<SubjectTeacherGroup> subjectsAfterSave = new ArrayList<>();
        subjects.forEach(x -> {
            subjectTeacherRepository.saveSubjectTeacherGroup(studentGroup.getId(), x.getSubject().getId(), x.getTeacher().getId(), studentGroup.getSemester().getId());
            //TODO: fix it
            subjectsAfterSave.add(subjectTeacherRepository.findBySubject_IdAndTeacher_IdAndStudentGroup_IdAndSemester_Id(x.getSubject().getId(), x.getTeacher().getId(), groupId, studentGroup.getSemester().getId()));
        });
        createStudentGrades(studentGroup.getStudents(), subjectsAfterSave, groupId);
    }

    @Override
    public StudentGroupDTO findGroupForStudent(long id) {
        StudentGroup group = studentGroupRepository.findStudentGroupByStudentId(id).orElseThrow(GroupNotFoundException::new);
        int studentsCount = studentGroupRepository.groupStudentsCount(group.getId());
        return new StudentGroupDTO.Builder(group.getId(), group.getName())
                .withSpecialization(group.getSpecialization())
                .withCounter(studentsCount)
                .withSemester(group.getSemester())
                .build();

    }

    private List<SubjectTeacherGroupDTO> getSubjects(StudentGroup group) {
        return group.getSubjectTeacherGroups()
                .stream()
                .map(SubjectTeacherGroupDTO::new)
                .collect(Collectors.toList());
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
            if (studentGroupGradeRepository.gradesAlreadyExists(student.getId(), group.getId())) {
                updateStatusForGrade(student.getId(), GradeStatus.ACTIVE);
            } else {
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

    //TODO: fix
    @Override
    public Collection<StudentGroupDTO> findAllGroups() {
        return studentGroupRepository.findAll().stream()
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
                .withSemester(studentGroup.getSemester())
                .build();
    }


    //TODO: fix delete
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
        Collection<SubjectTeacherGroupDTO> subjectTeacherGroups = getSubjectTeacherGroupDTOS(groupId, teacherId, groupForTeacher.getSemester().getId());

        return new StudentGroupDTO.Builder(groupForTeacher.getId(), groupForTeacher.getName())
                .withSubjects(subjectTeacherGroups)
                .withSpecialization(groupForTeacher.getSpecialization())
                .withSemester(groupForTeacher.getSemester())
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

    @Transactional
    @Override
    public void changeGroupSemester(long groupId, long semesterId, List<SubjectTeacherGroup> subjects) {
        StudentGroup group = studentGroupRepository.findGroupById(groupId).orElseThrow(GroupNotFoundException::new);
        Semester semester = semesterRepository.findById(semesterId).orElseThrow(SemesterNotFoundException::new);
        if (group.getSemester().equals(semester) || group.getSemesters().contains(semester)) {
            throw new SemesterAlreadyExistsException(semester.getName());
        }
        group.getSemesters().add(semester);
        group.setSemester(semester);
        addSubjectsToGroup(subjects, group.getId(), group);
        StudentGroup groupAfterSave = studentGroupRepository.save(group);
        createSchedule(groupAfterSave);
    }


    private void updateStatusForGrade(long studentId, GradeStatus status) {
        Collection<StudentGradeLimitedStudent> studentGradesForStudent = studentGroupGradeRepository.findGradesForStudent(studentId);
        for (StudentGradeLimitedStudent studentGradeLimitedStudent : studentGradesForStudent) {
            studentGroupGradeRepository.setStatusForGrade(status, studentGradeLimitedStudent.getId());
        }
    }

    private Collection<SubjectTeacherGroupDTO> getSubjectTeacherGroupDTOS(long id, long teacherId, long semesterId) {
        return subjectTeacherRepository.subjectsInGroupForTeacher(id, teacherId, semesterId).stream()
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
