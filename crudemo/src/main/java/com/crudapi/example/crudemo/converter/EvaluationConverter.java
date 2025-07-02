package com.crudapi.example.crudemo.converter;

import com.crudapi.example.crudemo.dao.EmployeeRepository;
import com.crudapi.example.crudemo.dao.JobsRepository;
import com.crudapi.example.crudemo.dtos.EvaluationDto;
import com.crudapi.example.crudemo.dtos.EvaluationResponseDto;
import com.crudapi.example.crudemo.entity.Employee;
import com.crudapi.example.crudemo.entity.Evaluation;
import com.crudapi.example.crudemo.entity.Jobs;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EvaluationConverter {

    @Autowired //TODO: remove autowired, make it final, have service here instead of repository class
  private  EmployeeRepository employeeRepository;
    @Autowired //TODO: remove autowired, make it final, have service here instead of repository class
  private  JobsRepository jobsRepository;

    public Evaluation toEntity(EvaluationDto evaluationDto) {
        return toEntity(evaluationDto, null);
    }

    public Evaluation toEntity(EvaluationDto dto, @Nullable Evaluation existing) {
        var builder = Evaluation.builder();

        if(existing != null) {
            builder.id(existing.getId());
        }

        Employee em=employeeRepository.findById(dto.getEmployeeId()).orElseThrow(()->new RuntimeException("Employee not found"));

        Jobs job=jobsRepository.findById(dto.getJobId()).orElseThrow(()->new RuntimeException("Job not found"));

        return builder
                .employee(em)
                .job(job)
                .score(dto.getScore())
                .years_of_Empl(dto.getYears_of_empl()).build();

    }

    public EvaluationResponseDto toResponseDto(Evaluation entity) {
        return EvaluationResponseDto.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployee().getId())
                .jobId(entity.getJob().getId())
                .years_of_empl(entity.getYears_of_Empl())
                .score(entity.getScore()).build();
    }

    public List<EvaluationResponseDto> toDtoList(List<Evaluation> evaluations) {
        return evaluations.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }



}
