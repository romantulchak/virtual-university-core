package com.romantulchak.virtualuniversity.utils;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.ScheduleDay;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public final class ExportScheduleToPdf {
    private static String FILE = "/home/romantulchak/test.pdf";
//    private static final Font font = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
//    private static final Font greenColor = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.GREEN);
//    private static final Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
//    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);




    public static Document export(Schedule schedule) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(FILE));
        Document document = new Document(pdfDocument, PageSize.A4.rotate());
        createTable(schedule, document);
        document.close();
        return null;
    }


    private static void createTable(Schedule schedule, Document document) throws IOException {
        float[] columnWidths = {100, 100, 250, 225, 100};
        Table table = new Table(columnWidths);
        table.setMargin(0);
        Cell tableHeader = new Cell(1, 5).add(new Paragraph("Schedule for group: " + schedule.getStudentGroup().getName()));
        tableHeader.setTextAlignment(TextAlignment.CENTER);
        tableHeader.setPadding(5);
        tableHeader.setBackgroundColor(new DeviceRgb(221, 239,233));
        table.addCell(tableHeader);
        table.addCell("Time start");
        table.addCell("Time end");
        table.addCell("Subject");
        table.addCell("Teacher");
        table.addCell("Room");
        table.setMarginBottom(5);
        table.setTextAlignment(TextAlignment.CENTER);
        document.add(table);
        for (ScheduleDay day : schedule.getDays()) {
            Table table1 = new Table(1).useAllAvailableWidth();
            Cell cell = new Cell(1, 6).add(new Paragraph(Objects.toString(day.getDay(), "")));
            cell.setBackgroundColor(new DeviceRgb(242, 242,242));
            cell.setTextAlignment(TextAlignment.LEFT);
            cell.setPadding(5);
            table1.addCell(cell);
            document.add(table1);
            Table table2 = new Table(columnWidths);
            table2.setTextAlignment(TextAlignment.CENTER);
            table2.setMarginBottom(5);
            for (Lesson lesson : day.getLessons()) {
                table2.addCell(lesson.getDateStart().format(DateTimeFormatter.ofPattern("HH:mm")));
                table2.addCell(lesson.getDateEnd().format(DateTimeFormatter.ofPattern("HH:mm")));
                table2.addCell(lesson.getSubjectTeacher().getSubject().getName());
                table2.addCell(lesson.getSubjectTeacher().getTeacher().getFullName());
                table2.addCell(Objects.toString(lesson.getRoomNumber(), ""));
            }
            document.add(table2);
        }
    }
}
