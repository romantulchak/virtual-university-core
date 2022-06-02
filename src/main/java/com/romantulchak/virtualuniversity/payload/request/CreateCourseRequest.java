package com.romantulchak.virtualuniversity.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateCourseRequest {
    @NotBlank(message = "course.name.empty.or.null")
    @Size(min = 3, max = 25, message = "course.name.size")
    String name;
}
