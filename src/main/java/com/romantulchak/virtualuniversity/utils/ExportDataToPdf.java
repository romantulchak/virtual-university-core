package com.romantulchak.virtualuniversity.utils;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.projection.StudentDataLimited;
import com.romantulchak.virtualuniversity.projection.StudentGradeLimitedStudent;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public final class ExportDataToPdf {

    public static byte[] exportSchedule(Schedule schedule) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(baos));
        Document document = new Document(pdfDocument, PageSize.A4.rotate());
        createScheduleTable(schedule, document);
        document.close();
        return baos.toByteArray();
    }

    public static byte[] exportGradesForStudent(StudentDataLimited student, List<StudentGradeLimitedStudent> grades) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(baos));
        Document document = new Document(pdfDocument, PageSize.A4);
        createGradesTable(student, grades, document);
        document.close();
        return baos.toByteArray();
    }

    /**
     * Creates a paragraph from String argument strings using StringBuilder,
     * and adds empty space to and from the arg
     *
     * @param strings string for Paragraph
     * @return Paragraph which contains strings from the arguments
     */
    private static Paragraph generateParagraph(String... strings) {
        StringBuilder sb = new StringBuilder();
        for (String str : strings) {
            sb.append(str).append(" ");
        }
        return new Paragraph(sb.toString());
    }

    private static void createGradesTable(StudentDataLimited student, List<StudentGradeLimitedStudent> grades, Document document) {
        if (grades != null && !grades.isEmpty()) {
            StudentGroup studentGroup = grades.get(0).getSubjectTeacherGroup().getStudentGroup();
            Paragraph groupName = generateParagraph("Group", studentGroup.getName());
            Paragraph groupSemester = generateParagraph("Semester", studentGroup.getSemester().getName());
            Paragraph studentName = generateParagraph("Grades for:", student.getLastName(), student.getLastName(), "nr.", Objects.toString(student.getNumberIdentifier(), ""));
            groupName.setFontSize(18);
            document.add(groupName)
                    .add(groupSemester)
                    .add(studentName);
            Table table = getGradesTable(grades);
            document.add(table);
        }
    }

    private static Table getGradesTable(List<StudentGradeLimitedStudent> grades) {
        Table table = new Table(3)
                .useAllAvailableWidth()
                .addCell("Subject").setTextAlignment(TextAlignment.CENTER)
                .addCell("Teacher").setTextAlignment(TextAlignment.CENTER)
                .addCell("Grade").setTextAlignment(TextAlignment.CENTER);
        generateGrades(grades, table);
        return table;
    }

    private static void generateGrades(List<StudentGradeLimitedStudent> grades, Table table) {
        for (StudentGradeLimitedStudent grade : grades) {
            table.addCell(grade.getSubjectTeacherGroup().getSubject().getName())
                    .setTextAlignment(TextAlignment.LEFT);
            table.addCell(grade.getSubjectTeacherGroup().getTeacher().getFullName())
                    .setTextAlignment(TextAlignment.LEFT);
            table.addCell(grade.getGrade() == 0 ? "" : String.valueOf(grade.getGrade()))
                    .setTextAlignment(TextAlignment.LEFT);
        }
    }


    private static void createScheduleTable(Schedule schedule, Document document) {
        float[] columnWidths = {100, 100, 250, 225, 100};
        Table table = getScheduleTableHeader(schedule, columnWidths);
        document.add(table);
        for (ScheduleDay day : schedule.getDays()) {
            Table scheduleDay = getScheduleDayHeader(day);
            document.add(scheduleDay);
            Table lessonsForDay = getLessonsForDay(columnWidths, day);
            document.add(lessonsForDay);
        }
    }

    private static Table getLessonsForDay(float[] columnWidths, ScheduleDay day) {
        Table table = new Table(columnWidths)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(5);
        for (Lesson lesson : day.getLessons()) {
            table.addCell(lesson.getDateStart().format(DateTimeFormatter.ofPattern("HH:mm")));
            table.addCell(lesson.getDateEnd().format(DateTimeFormatter.ofPattern("HH:mm")));
            table.addCell(lesson.getSubjectTeacher().getSubject().getName());
            table.addCell(lesson.getSubjectTeacher().getTeacher().getFullName());
            table.addCell(Objects.toString(lesson.getRoomNumber(), ""));
        }
        return table;
    }

    private static Table getScheduleDayHeader(ScheduleDay day) {
        Table table = new Table(1)
                .useAllAvailableWidth();
        String dayByFormat = day.getDay().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Cell cell = new Cell(1, 6)
                .add(new Paragraph(Objects.toString(dayByFormat, "")))
                .setBackgroundColor(new DeviceRgb(242, 242, 242))
                .setTextAlignment(TextAlignment.LEFT)
                .setPadding(5);
        table.addCell(cell);
        return table;
    }

    private static Table getScheduleTableHeader(Schedule schedule, float[] columnWidths) {
        Table table = new Table(columnWidths)
                .setTextAlignment(TextAlignment.CENTER)
                .setMargin(0)
                .setMarginBottom(5);
        Cell tableHeader = new Cell(1, 5)
                .add(new Paragraph("Schedule for group: " + schedule.getStudentGroup().getName()));
        tableHeader.setTextAlignment(TextAlignment.CENTER)
                .setPadding(5)
                .setBackgroundColor(new DeviceRgb(221, 239, 233));
        table.addCell(tableHeader);
        table.addCell("Time start")
                .addCell("Time end")
                .addCell("Subject")
                .addCell("Teacher")
                .addCell("Room");
        return table;
    }
}
