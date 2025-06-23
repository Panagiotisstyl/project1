package com.crudapi.example.crudemo.service;

import com.crudapi.example.crudemo.dao.EvaluationRepository;
import com.crudapi.example.crudemo.entity.Evaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationServiceImpl(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
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
    public List<Evaluation> findByEvaluationScore() {

        return evaluationRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));

    }
}
