package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Lesson implements Comparable<Lesson>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private LocalDateTime dateStart;

    @NotNull
    private LocalDateTime dateEnd;

    @ManyToOne
    private SubjectTeacherGroup subjectTeacher;

    @ManyToOne
    private ScheduleDay scheduleDay;

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    @Enumerated(EnumType.STRING)
    private LessonStatus previousStatus;

    private String roomNumber;

    private String comment;

    @OneToMany(mappedBy = "lesson", cascade = {CascadeType.REMOVE})
    private Collection<ScheduleLessonRequest> scheduleLessonRequests;

    public Lesson() {
    }

    public Lesson(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Lesson o) {
        return Comparator.comparing(Lesson::getDateStart)
                .thenComparing(Lesson::getDateEnd)
                .compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return id == lesson.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
