package com.crudapi.example.crudemo.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EvaluationDto {

    private int employeeId;
    private int jobId;
    private int yearsOfEmpl;
    private int score;

}
