package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.ScheduleDTO;
import com.romantulchak.virtualuniversity.dto.StudentGroupDTO;
import com.romantulchak.virtualuniversity.exception.GroupNotFoundException;
import com.romantulchak.virtualuniversity.exception.ScheduleIsNullException;
import com.romantulchak.virtualuniversity.exception.ScheduleNotFoundException;
import com.romantulchak.virtualuniversity.exception.TeacherNotFoundException;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.ScheduleDay;
import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.repository.*;
import com.romantulchak.virtualuniversity.service.ScheduleService;
import com.romantulchak.virtualuniversity.utils.ExportScheduleToPdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

    @Transactional
    @Override
    public void create(Schedule schedule) {
        if (schedule != null) {
            scheduleRepository.save(schedule);
            if (schedule.getDays() != null && schedule.getDays().size() > 0) {
                saveDays(schedule);
            }
        } else {
            throw new ScheduleIsNullException();
        }

    }

    //TODO: fix it
    @Override
    public ScheduleDTO findScheduleForGroup(long groupId) {
        long scheduleId = scheduleRepository.findScheduleIdByGroupId(groupId).orElseThrow(ScheduleNotFoundException::new);
        return scheduleRepository.findScheduleByGroupId(groupId)
                .map(schedule -> new ScheduleDTO(schedule.getId(), convertScheduleDayToDTO(schedule.getDays()), getStudentGroupDTO(schedule.getStudentGroup())))
                .orElse(new ScheduleDTO(scheduleId));


    }

    private StudentGroupDTO getStudentGroupDTO(StudentGroup studentGroup) {
        return new StudentGroupDTO.Builder(studentGroup.getId(), studentGroup.getName()).build();
    }

    private void saveDays(Schedule schedule) {
        for (ScheduleDay day : schedule.getDays()) {
            day.setSchedule(schedule);
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
    public long findScheduleIdForGroup(long groupId) {
        return scheduleRepository.findScheduleIdByGroupId(groupId)
                .orElseThrow(ScheduleNotFoundException::new);
    }

    @Override
    public ScheduleDTO findScheduleForTeacherBeGroup(long teacherId, long groupId) {
        if (teacherRepository.existsById(teacherId)) {
            long scheduleId = scheduleRepository.findScheduleIdByGroupId(groupId).orElseThrow(ScheduleNotFoundException::new);
            StudentGroup studentGroup = studentGroupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
            Collection<ScheduleDay> days = getDaysWithLessonsForTeacher(teacherId, groupId);
            StudentGroupDTO studentGroupDTO = new StudentGroupDTO.Builder(studentGroup.getId(), studentGroup.getName())
                    .withSemester(studentGroup.getSemester())
                    .build();
            return new ScheduleDTO(scheduleId, convertScheduleDayToDTO(days), studentGroupDTO);
        }
        throw new TeacherNotFoundException(teacherId);
    }

    @Override
    public ResponseEntity<Resource> exportScheduleAsPDF(long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdWithDays(scheduleId).orElseThrow(ScheduleNotFoundException::new);
        try {
           ExportScheduleToPdf.export(schedule);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Collection<ScheduleDay> getDaysWithLessonsForTeacher(long teacherId, long groupId) {
        Collection<ScheduleDay> days = scheduleDayRepository.findScheduleDayForTeacherByGroup(teacherId, groupId);
        for (ScheduleDay day : days) {
            day.setLessons(lessonRepository.findLessonsForTeacherByDay(day.getId(), teacherId));
        }
        return days;
    }
}
