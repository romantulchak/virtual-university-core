package com.romantulchak.virtualuniversity.utils;

import com.romantulchak.virtualuniversity.model.enumes.GradeRating;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GradeConverter implements AttributeConverter<GradeRating, Double> {


    @Override
    public Double convertToDatabaseColumn(GradeRating attribute) {
        return attribute.getValue();
    }

    @Override
    public GradeRating convertToEntityAttribute(Double dbData) {
        return GradeRating.getByValue(dbData);
    }
}
