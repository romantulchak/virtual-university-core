package com.romantulchak.virtualuniversity.projection;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import com.romantulchak.virtualuniversity.model.Views;

public interface GroupDetailsLimited {
    @JsonView(Views.StudentGroupView.class)
    String getName();

    @JsonView(Views.StudentGroupView.class)
    SubjectTeacherGroup getSubjects();
}
