package com.romantulchak.virtualuniversity.dto.pageable;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Views;

public class PageableDTO<T>{
    @JsonView({Views.TeacherSubjectView.class, Views.TeacherStudentGrades.class})
    private int currentPage;
    @JsonView({Views.TeacherSubjectView.class, Views.TeacherStudentGrades.class})
    private int totalPages;
    @JsonView({Views.TeacherSubjectView.class, Views.TeacherStudentGrades.class})
    private T data;
    @JsonView({Views.TeacherSubjectView.class, Views.TeacherStudentGrades.class})
    private long totalElements;

    public PageableDTO(int currentPage, int totalPages, T data, long totalElements) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.data = data;
        this.totalElements = totalElements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
