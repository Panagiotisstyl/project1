package com.crudapi.example.crudemo.converter;

import com.crudapi.example.crudemo.dtos.EmployeeDto;
import com.crudapi.example.crudemo.dtos.EmployeeResponseDto;
import com.crudapi.example.crudemo.entity.Employee;
import com.crudapi.example.crudemo.utilites.DateUtil;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeConverter {


    public static Employee toEntity(EmployeeDto employeeDto) {
        return toEntity(employeeDto,null);
    }

    public static Employee toEntity(EmployeeDto employeeDto, @Nullable Employee existing ) {
        var builder = Employee.builder();

        if(existing != null) {
            builder.id(existing.getId());
        }

        return builder
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .dateJoined(DateUtil.toEpoch(employeeDto.getDateJoined()))
                .build();
    }

    public static EmployeeResponseDto toResponseDto(Employee employee) {
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .dateJoined(DateUtil.toDateString(employee.getDateJoined()))
                .build();
    }

    public static List<EmployeeResponseDto> toDtoList(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeConverter::toResponseDto)
                .collect(Collectors.toList());
    }
}
