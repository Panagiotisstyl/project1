package com.crudapi.example.crudemo.factories;

import com.crudapi.example.crudemo.dtos.EmployeeDto;
import com.crudapi.example.crudemo.entity.Employee;

public class EmployeeFactory {

    public static Employee createEmployee(String firstName, String lastName){

        return Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email("panstyl@email.com")
                .dateJoined(1748811600L).build();
    }

    public static EmployeeDto createEmployeeDto(String firstName, String lastName){
        return EmployeeDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email("panstyl@email.com")
                .dateJoined("01-07-2025")
                .build();
    }
}
