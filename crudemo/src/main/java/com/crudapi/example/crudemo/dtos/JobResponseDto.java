package com.crudapi.example.crudemo.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JobResponseDto {
    private int id;
    private String job_Desc;
}
