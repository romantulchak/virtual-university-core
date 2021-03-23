package com.romantulchak.virtualuniversity.projection;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.model.Views;

public interface GroupLimited {
    @JsonView(Views.StudentGroupView.class)
    long getId();

    @JsonView(Views.StudentGroupView.class)
    String getName();

    @JsonView(Views.StudentGroupView.class)
    Semester getSemester();
}
