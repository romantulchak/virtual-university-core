package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.exception.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class AdvisorController extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        String message = result.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .map(value -> messageSource.getMessage(value, null, request.getLocale()))
                .collect(Collectors.joining("\n"));
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

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
    @ExceptionHandler(CourseWithNameAlreadyExistsException.class)
    public ResponseEntity<?> handleCourseWithNameAlreadyExists(CourseWithNameAlreadyExistsException ex, WebRequest webRequest){
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
    @ExceptionHandler(LessonAtThatTimeAlreadyExistsException.class)
    public ResponseEntity<?> handleLessonAtThatTimeAlreadyExistsException(LessonAtThatTimeAlreadyExistsException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TimeNotCorrectException.class)
    public ResponseEntity<?> handleTimeNotCorrectException(TimeNotCorrectException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<?> handleScheduleNotFoundException(ScheduleNotFoundException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(LessonNotFoundException.class)
    public ResponseEntity<?> handleLessonNotFoundException(LessonNotFoundException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ScheduleDayNotFoundException.class)
    public ResponseEntity<?> handleScheduleDayNotFoundException(ScheduleDayNotFoundException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<?> handleGroupNotFoundException(GroupNotFoundException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SubjectAlreadyExistsException.class)
    public ResponseEntity<?> handleSubjectAlreadyExistsException(SubjectAlreadyExistsException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SubjectIsNullException.class)
    public ResponseEntity<?> handleSubjectIsNullException(SubjectIsNullException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(LessonStatusNotChangedException.class)
    public ResponseEntity<?> handleLessonStatusNotChangedException(LessonStatusNotChangedException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserIsNullException.class)
    public ResponseEntity<?> handleUserIsNullException(UserIsNullException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotificationNotFound.class)
    public ResponseEntity<?> handleNotificationNotFound(NotificationNotFound ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException ex, WebRequest webRequest){
        Map<String ,Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<?> handleFileSizeLimitExceededException(FileSizeLimitExceededException ex, WebRequest webRequest){
        Map<String ,Object> body = getBodyAsString(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<?> handleSizeLimitExceededException(SizeLimitExceededException ex, WebRequest webRequest){
        Map<String, Object> body = getBodyAsString(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException ex, WebRequest webRequest){
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getBodyAsString(String message) {
        Map<String ,Object> body = new HashMap<>();
        body.put("message", message);
        body.put("timestamp", LocalDateTime.now());
        return body;
    }

    private Map<String, Object> getBody(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return body;
    }
}
