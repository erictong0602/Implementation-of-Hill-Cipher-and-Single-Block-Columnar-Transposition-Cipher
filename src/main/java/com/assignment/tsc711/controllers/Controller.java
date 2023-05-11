package com.assignment.tsc711.controllers;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.assignment.tsc711.models.SampleModel;

@Component
public class Controller {

    @Bean
    public Function<String, String> function() {
        return input -> input;
    }

    @Bean
    public Consumer<String> consume() {
        return input -> {
            String result = "Input-" + input;
            System.err.println(result);
        };
    }

    @Bean
    public Supplier<String> supply() {
        return () -> "Hello from Supplier";
    }

    @Bean
    public Function<SampleModel, SampleModel> reverseString() {
        return model -> {
            String reverseString = new StringBuilder(model.getValue()).reverse().toString();
            model.setValue(reverseString);
            return model;
        };
    }
}