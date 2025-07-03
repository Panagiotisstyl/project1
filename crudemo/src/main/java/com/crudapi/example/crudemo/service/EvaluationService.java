package com.crudapi.example.crudemo.service;

import com.crudapi.example.crudemo.entity.Evaluation;

import java.util.List;

public interface EvaluationService {

    List<Evaluation> findAll(String sortBy, String direction);
    Evaluation save(Evaluation theEvaluation);
    boolean deleteById(int id);
    Evaluation getEvaluationById(int theId);

}
