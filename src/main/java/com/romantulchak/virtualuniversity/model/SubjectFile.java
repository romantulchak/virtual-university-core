package com.romantulchak.virtualuniversity.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
public class SubjectFile {

    @Column(unique = true)
    @JsonView(Views.FileView.class)
    private String name;

    private String path;

    @JsonView(Views.FileView.class)
    private LocalDateTime added;

    private String localPath;

    public SubjectFile(){

    }

    public SubjectFile(String name, LocalDateTime added) {
        this.name = name;
        this.added = added;
    }

    public SubjectFile(String path, LocalDateTime added, String name, String localPath) {
        this.path = path;
        this.added = added;
        this.name = name;
        this.localPath = localPath;
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

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
