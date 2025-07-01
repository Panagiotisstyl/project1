package com.crudapi.example.crudemo.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeResponseDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String date_joined;
}
