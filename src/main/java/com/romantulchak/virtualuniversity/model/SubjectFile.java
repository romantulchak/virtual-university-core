package com.romantulchak.virtualuniversity.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Embeddable
public class SubjectFile {

    private String name;

    private String path;

    private LocalDateTime added;

    public SubjectFile(){

    }
    public SubjectFile(String path, LocalDateTime added, String name) {
        this.path = path;
        this.added = added;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getAdded() {
        return added;
    }

    public void setAdded(LocalDateTime added) {
        this.added = added;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
