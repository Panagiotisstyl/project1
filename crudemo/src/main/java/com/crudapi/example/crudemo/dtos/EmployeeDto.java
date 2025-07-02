package com.crudapi.example.crudemo.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeDto {

    private String firstName;
    private String lastName;
    private String email;
    private String dateJoined; //TODO: change this to LocalDate

}
