package com.crudapi.example.crudemo.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JobDto {
    private String job_Desc;
}
