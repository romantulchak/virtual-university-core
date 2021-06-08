package com.romantulchak.virtualuniversity.dto.pageable;

public class PageableDTO<T>{
    private int currentPage;
    private int totalPages;
    private T data;

    public PageableDTO(int currentPage, int totalPages, T data) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.data = data;
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
}
