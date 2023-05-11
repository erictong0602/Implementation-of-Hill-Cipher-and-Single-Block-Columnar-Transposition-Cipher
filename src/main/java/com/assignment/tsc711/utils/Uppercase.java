package com.assignment.tsc711.utils;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.assignment.tsc711.models.SampleModel;

@Component
public class Uppercase implements Function<SampleModel, SampleModel> {
    @Override 
    public SampleModel apply(SampleModel model) {
        String upperCase = model.getValue().toUpperCase();
        model.setValue(upperCase);
        return model;
    }
}
