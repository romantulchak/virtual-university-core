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
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.ScheduleDay;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public final class ExportScheduleToPdf {

    public static byte[] export(Schedule schedule) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(baos));
        Document document = new Document(pdfDocument, PageSize.A4.rotate());
        createTable(schedule, document);
        document.close();
        return baos.toByteArray();
    }


    private static void createTable(Schedule schedule, Document document) {
        float[] columnWidths = {100, 100, 250, 225, 100};
        Table table = getTableHeader(schedule, columnWidths);
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

    private static Table getTableHeader(Schedule schedule, float[] columnWidths) {
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
