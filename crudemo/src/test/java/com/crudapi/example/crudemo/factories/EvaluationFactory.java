package com.crudapi.example.crudemo.factories;

import com.crudapi.example.crudemo.dao.EmployeeRepository;
import com.crudapi.example.crudemo.dao.JobsRepository;
import com.crudapi.example.crudemo.dtos.EvaluationDto;
import com.crudapi.example.crudemo.entity.Evaluation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EvaluationFactory {


   private final EmployeeRepository employeeRepository;
   private final JobsRepository jobsRepository;

    public  Evaluation createEvaluation(int score, int yearsOfEmpl){

            return Evaluation.builder()
                    .employee(employeeRepository.save(EmployeeFactory.createEmployee("Pan","Styl")))
                    .job(jobsRepository.save(JobFactory.createJob("Job1")))
                    .yearsOfEmpl(yearsOfEmpl)
                    .score(score)
                    .build();

    }

    public EvaluationDto createEvaluationDto(int score, int yearsOfEmpl){

        return EvaluationDto.builder()
                .employeeId(employeeRepository.save(EmployeeFactory.createEmployee("Pan","Styl")).getId())
                .jobId(jobsRepository.save(JobFactory.createJob("Job1")).getId())
                .score(score)
                .yearsOfEmpl(yearsOfEmpl)
                .build();

    }
}
