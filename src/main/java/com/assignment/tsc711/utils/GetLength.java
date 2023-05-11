package com.assignment.tsc711.utils;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class GetLength implements Function<String, Integer> {
    @Override
    public Integer apply(String value) {
        return value.length();
    }
}