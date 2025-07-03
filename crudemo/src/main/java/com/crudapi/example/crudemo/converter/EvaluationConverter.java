package com.crudapi.example.crudemo.converter;

import com.crudapi.example.crudemo.dtos.EvaluationDto;
import com.crudapi.example.crudemo.dtos.EvaluationResponseDto;
import com.crudapi.example.crudemo.entity.Evaluation;
import com.crudapi.example.crudemo.service.EmployeeService;
import com.crudapi.example.crudemo.service.JobsService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class EvaluationConverter {

    private  final EmployeeService employeeService;

    private final JobsService jobsService;

    public Evaluation toEntity(EvaluationDto evaluationDto) {
        return toEntity(evaluationDto, null);
    }

    public Evaluation toEntity(EvaluationDto dto, @Nullable Evaluation existing) {
        var builder = Evaluation.builder();

        if(existing != null) {
            builder.id(existing.getId());
        }

        return builder
                .employee(employeeService.findById(dto.getEmployeeId()))
                .job(jobsService.findById(dto.getJobId()))
                .score(dto.getScore())
                .yearsOfEmpl(dto.getYearsOfEmpl()).build();

    }

    public EvaluationResponseDto toResponseDto(Evaluation entity) {
        return EvaluationResponseDto.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployee().getId())
                .jobId(entity.getJob().getId())
                .yearsOfEmpl(entity.getYearsOfEmpl())
                .score(entity.getScore()).build();
    }

    public List<EvaluationResponseDto> toDtoList(List<Evaluation> evaluations) {
        return evaluations.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }



}
