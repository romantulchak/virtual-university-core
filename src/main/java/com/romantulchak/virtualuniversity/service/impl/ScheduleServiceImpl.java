package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.ScheduleDTO;
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

import static com.romantulchak.virtualuniversity.utils.ScheduleConvertorUtility.convertScheduleDayToDTO;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleDayRepository scheduleDayRepository;
    private final LessonRepository lessonRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final TeacherRepository teacherRepository;
    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleDayRepository scheduleDayRepository, LessonRepository lessonRepository,
                               StudentGroupRepository studentGroupRepository, TeacherRepository teacherRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleDayRepository = scheduleDayRepository;
        this.lessonRepository = lessonRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.teacherRepository = teacherRepository;
    }
    //TODO: get scheduleId in arguments
    @Transactional
    @Override
    public void create(Schedule schedule) {
        if (schedule != null) {
            Schedule scheduleFromDb = checkIfScheduleExists(schedule);
            if (schedule.getDays() != null && schedule.getDays().size() > 0) {
                saveDays(scheduleFromDb);
            }
        } else {
            throw new ScheduleIsNullException();
        }

    }

    private Schedule checkIfScheduleExists(Schedule schedule) {
        Schedule scheduleFromDb = scheduleRepository.findById(schedule.getId()).orElse(null);
        if (scheduleFromDb == null){
           scheduleFromDb = scheduleRepository.save(schedule);
        }else {
            scheduleFromDb.getDays().addAll(schedule.getDays());
        }
        return scheduleFromDb;
    }

    //TODO: fix it
    @Override
    public ScheduleDTO findScheduleForGroup(long semesterId) {
        long scheduleId = scheduleRepository.findScheduleIdBySemesterId(semesterId).orElseThrow(ScheduleNotFoundException::new);
        return scheduleRepository.findScheduleBySemesterId(semesterId)
                .map(schedule -> new ScheduleDTO(schedule.getId(), convertScheduleDayToDTO(schedule.getDays())))
                .orElse(new ScheduleDTO(scheduleId));


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
            long scheduleId = scheduleRepository.findScheduleIdBySemesterId(semesterId).orElseThrow(ScheduleNotFoundException::new);
            StudentGroup studentGroup = studentGroupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
            Collection<ScheduleDay> days = getDaysWithLessonsForTeacher(teacherId, semesterId);
            StudentGroupDTO studentGroupDTO = new StudentGroupDTO.Builder(studentGroup.getId(), studentGroup.getName())
                    .withSemester(studentGroup.getSemester())
                    .build();
            return new ScheduleDTO(scheduleId, convertScheduleDayToDTO(days), studentGroupDTO);
        }
        throw new TeacherNotFoundException(teacherId);
    }

    @Override
    public byte[] exportFullScheduleAsPDF(long scheduleId, long semesterId) {
        Schedule schedule = scheduleRepository.findByIdWithDays(scheduleId, semesterId).orElseThrow(ScheduleNotFoundException::new);
        try {
            if (schedule.getDays() != null && !schedule.getDays().isEmpty()) {
                return ExportDataToPdf.exportSchedule(schedule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ExportToPdfFailedException();
    }

    //TODO: fix export
    @Override
    public byte[] exportScheduleForWeekPDF(long scheduleId, long semesterId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(ScheduleNotFoundException::new);
        Collection<ScheduleDay> days = scheduleDayRepository.findScheduleDaysForWeek(semesterId, LocalDate.now(), LocalDate.now().plusDays(7));
        schedule.setDays(days);
        try {
            return ExportDataToPdf.exportSchedule(schedule);
        } catch (IOException e) {
            throw new ExportToPdfFailedException();
        }
    }

    private Collection<ScheduleDay> getDaysWithLessonsForTeacher(long teacherId, long semesterId) {
        Collection<ScheduleDay> days = scheduleDayRepository.findScheduleDayForTeacherByGroup(teacherId, semesterId);
        for (ScheduleDay day : days) {
            day.setLessons(lessonRepository.findLessonsForTeacherByDay(day.getId(), teacherId));
        }
        return days;
    }
}
