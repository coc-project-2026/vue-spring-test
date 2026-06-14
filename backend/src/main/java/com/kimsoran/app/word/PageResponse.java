package com.kimsoran.app.word;
import java.util.List;

public class PageResponse<T> {

    private List<T> content;
    private int totalPages;

    public PageResponse(List<T> content, int totalPages) {
        this.content = content;
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }
}