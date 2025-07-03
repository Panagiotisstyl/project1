package com.crudapi.example.crudemo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class EmployeeDto {

    private String firstName;
    private String lastName;
    private String email;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dateJoined;

}
