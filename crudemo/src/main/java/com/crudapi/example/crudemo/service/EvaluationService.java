package com.crudapi.example.crudemo.service;

import com.crudapi.example.crudemo.entity.Evaluation;

import java.util.List;
import java.util.Optional;

public interface EvaluationService {
    List<Evaluation> findAll();
    Optional<Evaluation> findById(int theId);
    Evaluation save(Evaluation theEvaluation);
    void deleteById(int id);
    List<Evaluation> findByEvaluationScore();

}
