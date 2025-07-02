package com.crudapi.example.crudemo.dtos;

import com.crudapi.example.crudemo.entity.Evaluation;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EvaluationResponseDto {

    private int id;
    private int employeeId;
    private int jobId;
    private int yearsOfEmpl;
    private int score;


}
