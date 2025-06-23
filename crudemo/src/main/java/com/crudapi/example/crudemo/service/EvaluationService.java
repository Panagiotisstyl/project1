package com.crudapi.example.crudemo.service;

import com.crudapi.example.crudemo.dtos.EvaluationCreationDTO;
import com.crudapi.example.crudemo.dtos.EvaluationDTO;
import com.crudapi.example.crudemo.entity.Evaluation;

import java.util.List;
import java.util.Optional;

public interface EvaluationService {
    List<Evaluation> findAll();
    Optional<Evaluation> findById(int theId);
    Evaluation save(Evaluation theEvaluation);
    void deleteById(int id);
    List<EvaluationDTO> findByEvaluationScore();
    List<EvaluationDTO> getAllEvaluations();
    public EvaluationDTO getEvaluationById(int theId);
    public Evaluation createEvaluation(EvaluationCreationDTO dto);
}
