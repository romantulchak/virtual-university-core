package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.exception.StudentWithSameLoginAlreadyExists;
import com.romantulchak.virtualuniversity.exception.TeacherWithSameLoginAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class AdvisorController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(StudentWithSameLoginAlreadyExists.class)
    public ResponseEntity<?> handleStudentWithSameLoginAlreadyExists(StudentWithSameLoginAlreadyExists ex, WebRequest webRequest){
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TeacherWithSameLoginAlreadyExists.class)
    public ResponseEntity<?> handleTeacherWithSameLoginAlreadyExists(TeacherWithSameLoginAlreadyExists ex, WebRequest webRequest){
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getBody(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return body;
    }
}
