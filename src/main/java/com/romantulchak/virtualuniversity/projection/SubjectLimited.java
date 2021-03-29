package com.romantulchak.virtualuniversity.projection;

import com.romantulchak.virtualuniversity.model.enumes.SubjectType;

public interface SubjectLimited {

    long getId();

    String getName();

    SubjectType getType();


}
