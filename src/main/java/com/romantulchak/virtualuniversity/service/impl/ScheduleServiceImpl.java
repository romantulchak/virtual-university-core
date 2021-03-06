package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.ScheduleDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleDayDTO;
import com.romantulchak.virtualuniversity.dto.StudentGroupDTO;
import com.romantulchak.virtualuniversity.exception.*;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.ScheduleDay;
import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.repository.*;
import com.romantulchak.virtualuniversity.service.ScheduleService;
import com.romantulchak.virtualuniversity.utils.ExportDataToPdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.romantulchak.virtualuniversity.utils.ScheduleConvertorUtility.convertScheduleDayToDTO;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleDayRepository scheduleDayRepository;
    private final LessonRepository lessonRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final TeacherRepository teacherRepository;
    private final ScheduleDayServiceImpl scheduleDayService;
    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleDayRepository scheduleDayRepository, LessonRepository lessonRepository,
                               StudentGroupRepository studentGroupRepository, TeacherRepository teacherRepository, ScheduleDayServiceImpl scheduleDayService) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleDayRepository = scheduleDayRepository;
        this.lessonRepository = lessonRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.teacherRepository = teacherRepository;
        this.scheduleDayService = scheduleDayService;
    }

    @Transactional
    @Override
    public void create(Schedule schedule) {
        if (schedule != null) {
            Schedule scheduleFromDb = checkIfScheduleExists(schedule);
            if (schedule.getDays() != null && !schedule.getDays().isEmpty()) {
                saveDays(scheduleFromDb);
            }
        } else {
            throw new ScheduleIsNullException();
        }

    }

    private Schedule checkIfScheduleExists(Schedule schedule) {
        Schedule scheduleFromDb = scheduleRepository.findById(schedule.getId()).orElse(null);
        if (scheduleFromDb == null) {
            scheduleFromDb = scheduleRepository.save(schedule);
        } else {
            scheduleFromDb.getDays().addAll(schedule.getDays());
        }
        return scheduleFromDb;
    }

    @Override
    public ScheduleDTO findScheduleForGroup(long semesterId) {
        return scheduleRepository.findScheduleBySemesterId(semesterId)
                .map(schedule -> new ScheduleDTO(schedule.getId(), convertScheduleDayToDTO(schedule.getDays())))
                .orElseThrow(ScheduleNotFoundException::new);


    }

    private void saveDays(Schedule schedule) {
        for (ScheduleDay day : schedule.getDays()) {
            day.setSchedule(schedule);
            day.setSemester(schedule.getSemester());
            scheduleDayRepository.save(day);
            saveLessons(day);
        }
    }

    private void saveLessons(ScheduleDay day) {
        for (Lesson lesson : day.getLessons()) {
            lesson.setScheduleDay(day);
            lessonRepository.save(lesson);
        }
    }

    @Override
    public long findScheduleIdForGroup(long semesterId) {
        return scheduleRepository.findScheduleIdBySemesterId(semesterId)
                .orElseThrow(ScheduleNotFoundException::new);
    }

    @Override
    public ScheduleDTO findScheduleForTeacherByGroup(long teacherId, long groupId, long semesterId) {
        if (teacherRepository.existsById(teacherId)) {
            Collection<ScheduleDay> days = getAllDaysWithLessonsForTeacher(teacherId, semesterId)
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());
            return scheduleDTOForTeacher(days, semesterId, groupId);
        }
        throw new TeacherNotFoundException(teacherId);
    }

    @Override
    public ScheduleDTO findScheduleForTeacherByWeek(long teacherId, long groupId, long semesterId) {
        if (teacherRepository.existsById(teacherId)){
            Collection<ScheduleDay> days = getDaysWithLessonsForTeacherByWeek(teacherId, semesterId);
            return scheduleDTOForTeacher(days, semesterId, groupId);
        }
        throw new TeacherNotFoundException(teacherId);
    }

    private ScheduleDTO scheduleDTOForTeacher(Collection<ScheduleDay> days, long semesterId, long groupId){
        long scheduleId = scheduleRepository.findScheduleIdBySemesterId(semesterId).orElseThrow(ScheduleNotFoundException::new);
        StudentGroup studentGroup = studentGroupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO.Builder(studentGroup.getId(), studentGroup.getName())
                .withSemester(studentGroup.getSemester())
                .build();
        return new ScheduleDTO(scheduleId, convertScheduleDayToDTO(days), studentGroupDTO);
    }

    @Override
    public byte[] exportFullScheduleAsPDF(long scheduleId, long semesterId) {
        if (scheduleRepository.existsByIdAndSemester(scheduleId, semesterId)) {
            Collection<ScheduleDay> days = scheduleDayRepository.findAllDaysForScheduleAndSemester(scheduleId, semesterId);
            try {
                if (days != null && !days.isEmpty()) {
                    return ExportDataToPdf.exportSchedule(convertScheduleDayToDTO(days));
                }
            } catch (IOException e) {
                throw new ExportToPdfFailedException();
            }
        } else {
            throw new ScheduleNotFoundException();
        }
        throw new ExportToPdfFailedException();
    }

    @Override
    public byte[] exportScheduleForWeekPDF(long scheduleId, long semesterId) {
        if (scheduleRepository.existsByIdAndSemester(scheduleId, semesterId)) {
            Collection<ScheduleDayDTO> days = scheduleDayService.findDaysForWeek(semesterId);
            try {
                return ExportDataToPdf.exportSchedule(days);
            } catch (IOException e) {
                throw new ExportToPdfFailedException();
            }
        } else {
            throw new ScheduleNotFoundException();
        }
    }

    private Collection<ScheduleDay> getAllDaysWithLessonsForTeacher(long teacherId, long semesterId) {
        Collection<ScheduleDay> days = scheduleDayRepository.findAllScheduleDayForTeacher(teacherId, semesterId);
        setTeacherLessonsToDay(teacherId, days);
        return days;
    }
    private Collection<ScheduleDay> getDaysWithLessonsForTeacherByWeek(long teacherId, long semesterId) {
        Collection<ScheduleDay> days = scheduleDayRepository.findScheduleDayForTeacherByWeek(teacherId, semesterId, LocalDate.now(), LocalDate.now().plusWeeks(1));
        setTeacherLessonsToDay(teacherId, days);
        return days;
    }

    private void setTeacherLessonsToDay(long teacherId, Collection<ScheduleDay> days) {
        for (ScheduleDay day : days) {
            day.setLessons(lessonRepository.findLessonsForTeacherByDay(day.getId(), teacherId));
        }
    }

}
