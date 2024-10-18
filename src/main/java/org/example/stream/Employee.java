package org.example.stream;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Employee {

    private String name;
    private int age;
    private JobTitle jobTitle;

    public enum JobTitle {
        ИНЖЕНЕР,
        ЧАБАН,
        ФОТОГРАФ_ФОТОГРАФИЙ_И_ФОТООБОРУДОВАНИЯ
    }
}
