package com.crudapi.example.crudemo.converter;

import com.crudapi.example.crudemo.dtos.EmployeeDto;
import com.crudapi.example.crudemo.dtos.EmployeeResponseDto;
import com.crudapi.example.crudemo.entity.Employee;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeConverter {

    public Employee toEntity(EmployeeDto employeeDto) {
        return toEntity(employeeDto,null);
    }

    public Employee toEntity(EmployeeDto employeeDto, @Nullable Employee existing ) {
        var builder = Employee.builder();

        if(existing != null) {
            builder.id(existing.getId());
        }

        return builder
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail()).build();
    }

    public EmployeeResponseDto toResponseDto(Employee employee) {
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
    }

    public List<EmployeeResponseDto> toDtoList(List<Employee> employees) {
        return employees.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
