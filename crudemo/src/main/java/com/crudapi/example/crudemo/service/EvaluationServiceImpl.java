package com.crudapi.example.crudemo.service;

import com.crudapi.example.crudemo.dao.EmployeeRepository;
import com.crudapi.example.crudemo.dao.EvaluationRepository;
import com.crudapi.example.crudemo.dao.JobsRepository;
import com.crudapi.example.crudemo.dtos.EvaluationCreationDTO;
import com.crudapi.example.crudemo.dtos.EvaluationDTO;
import com.crudapi.example.crudemo.entity.Employee;
import com.crudapi.example.crudemo.entity.Evaluation;
import com.crudapi.example.crudemo.entity.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final EmployeeRepository employeeRepository;
    private final JobsRepository jobsRepository;
    private EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationServiceImpl(EvaluationRepository evaluationRepository, EmployeeRepository employeeRepository, JobsRepository jobsRepository) {
        this.evaluationRepository = evaluationRepository;
        this.employeeRepository = employeeRepository;
        this.jobsRepository = jobsRepository;
    }

    @Override
    public List<Evaluation> findAll() {

        return evaluationRepository.findAll();

    }

    @Override
    public Optional<Evaluation> findById(int theId) {

        return evaluationRepository.findById(theId);

    }

    @Override
    public Evaluation save(Evaluation theEvaluation) {

        return evaluationRepository.save(theEvaluation);

    }

    @Override
    public void deleteById(int id) {

        evaluationRepository.deleteById(id);

    }

    @Override
    public List<EvaluationDTO> findByEvaluationScore() {
        return evaluationRepository.findAll(Sort.by(Sort.Direction.DESC, "score")).stream().map(EvaluationDTO::new).collect(Collectors.toList());

    }


    @Override
    public List<EvaluationDTO> getAllEvaluations() {
        return evaluationRepository.findAll().stream().map(EvaluationDTO::new).collect(Collectors.toList());
    }

    @Override
    public EvaluationDTO getEvaluationById(int theId) {
        return new EvaluationDTO(evaluationRepository.findById(theId).orElseThrow(() -> new RuntimeException("Evaluation not found with id " + theId)));
    }

    @Override
    public Evaluation createEvaluation(EvaluationCreationDTO dto) {
        Employee employee=employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found "));

        Jobs job=jobsRepository.findById(dto.getJobId()).orElseThrow(() -> new RuntimeException("Job not found "));

        Evaluation evaluation=new Evaluation();
        evaluation.setEmployee(employee);
        evaluation.setJob(job);
        evaluation.setScore(dto.getScore());
        evaluation.setYears_of_Empl(dto.getYears_of_empl());

    return evaluationRepository.save(evaluation);

    }

}
