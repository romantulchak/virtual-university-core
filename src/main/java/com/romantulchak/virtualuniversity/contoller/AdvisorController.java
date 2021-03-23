package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.exception.*;
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
    @ExceptionHandler(StudentWithSameLoginAlreadyExistsException.class)
    public ResponseEntity<?> handleStudentWithSameLoginAlreadyExists(StudentWithSameLoginAlreadyExistsException ex, WebRequest webRequest){
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TeacherWithSameLoginAlreadyExistsException.class)
    public ResponseEntity<?> handleTeacherWithSameLoginAlreadyExists(TeacherWithSameLoginAlreadyExistsException ex, WebRequest webRequest){
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<?> handleStudentNotFoundException(StudentNotFoundException ex, WebRequest webRequest){
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PasswordNotMatchesException.class)
    public ResponseEntity<?> handlePasswordNotMatchesException(PasswordNotMatchesException ex, WebRequest webRequest){
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SpecializationIsNullException.class)
    public ResponseEntity<?> handlePSpecializationIsNullException(SpecializationIsNullException ex, WebRequest webRequest){
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SemesterIsNullException.class)
    public ResponseEntity<?> handleSemesterIsNullException(SemesterIsNullException ex, WebRequest webRequest){
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SemesterNotFoundException.class)
    public ResponseEntity<?> handleSemesterNotFound(SemesterNotFoundException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(GradeNotFoundException.class)
    public ResponseEntity<?> handleGradeNotFoundException(GradeNotFoundException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StudentGradeNotFoundException.class)
    public ResponseEntity<?> handleStudentGradeNotFoundException(StudentGradeNotFoundException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SpecializationSemesterAlreadyExists.class)
    public ResponseEntity<?> handleSpecializationSemesterAlreadyExistsException(SpecializationSemesterAlreadyExists ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CourseWithNameAlreadyExists.class)
    public ResponseEntity<?> handleCourseWithNameAlreadyExists(CourseWithNameAlreadyExists ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CourseIsNullException.class)
    public ResponseEntity<?> handleCourseIsNullException(CourseIsNullException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(GroupWithNameAlreadyExistsException.class)
    public ResponseEntity<?> handleGroupWithNameAlreadyExistsException(GroupWithNameAlreadyExistsException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(GroupForStudentNotFoundException.class)
    public ResponseEntity<?> handleGroupForStudentNotFoundException(GroupForStudentNotFoundException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    private Map<String, Object> getBody(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return body;
    }
}
