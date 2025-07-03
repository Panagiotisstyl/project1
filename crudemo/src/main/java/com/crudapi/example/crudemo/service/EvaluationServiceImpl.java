package com.crudapi.example.crudemo.service;

import com.crudapi.example.crudemo.dao.EvaluationRepository;
import com.crudapi.example.crudemo.entity.Evaluation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;


    @Override
    public List<Evaluation> findAll(String sortBy, String direction) {

        List<String> allowedFields=List.of("score","yearsOfEmpl","id");
        if(!allowedFields.contains(sortBy)){
            throw new IllegalArgumentException("sort is not allowed");
        }

        Sort.Direction sortDirection;

        try{
            sortDirection = Sort.Direction.valueOf(direction.toUpperCase());
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("direction is not allowed");
        }

        return evaluationRepository.findAll(Sort.by(sortDirection,sortBy));
    }

    @Override
    public Evaluation save(Evaluation theEvaluation) {
        return evaluationRepository.save(theEvaluation);
    }

    @Override
    public boolean deleteById(int id) {
        try{
            evaluationRepository.deleteById(id);
            return true;
        }catch(EmptyResultDataAccessException e){
            return false;
        }
    }

    @Override
    public Evaluation getEvaluationById(int theId) {

        return evaluationRepository.findById(theId).orElseThrow(() -> new RuntimeException("Evaluation not found"));
    }


}
