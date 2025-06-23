package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.dtos.EvaluationCreationDTO;
import com.crudapi.example.crudemo.dtos.EvaluationDTO;
import com.crudapi.example.crudemo.entity.Evaluation;
import com.crudapi.example.crudemo.service.EvaluationService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("EvApi")
public class EvaluationRestController {

    private EvaluationService evaluationService;

    public EvaluationRestController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/evaluation")
    public List<EvaluationDTO> findAll() {

        return evaluationService.getAllEvaluations();

    }

    @GetMapping("/evaluation/{evaluationId}")
    public EvaluationDTO findById(@PathVariable int evaluationId) {

       return evaluationService.getEvaluationById(evaluationId);

    }

    @PostMapping("/evaluation")
    public Evaluation save(@RequestBody EvaluationCreationDTO dto) {
        return evaluationService.createEvaluation(dto);
    }

    @Transactional
    @PutMapping("/evaluation/{evaluationId}")
    public void updateEvaluation(@PathVariable int evaluationId, @RequestBody Evaluation theEvaluation) {

        Optional<Evaluation> evaluation = evaluationService.findById(evaluationId);

        if (evaluation.isPresent()) {

            Evaluation dbEvaluation = evaluation.get();
            dbEvaluation.setScore(theEvaluation.getScore());
            dbEvaluation.setYears_of_Empl(theEvaluation.getYears_of_Empl());
            dbEvaluation.setScore(theEvaluation.getScore());

        }else{
            throw new RuntimeException("Evaluation not found with id " + evaluationId);
        }
    }

    @DeleteMapping("/evaluation/{evaluationId}")
    public String deleteEvaluation(@PathVariable int evaluationId) {

        Optional<Evaluation> evaluation = evaluationService.findById(evaluationId);
        if(evaluation.isEmpty()){

            throw new RuntimeException("Evaluation not found with id " + evaluationId);
        }

        evaluationService.deleteById(evaluationId);

        return "Evaluation with id " + evaluationId + " deleted";
    }

    @GetMapping("/evaluation/byscore")
    public List<EvaluationDTO> getByScore() {

        return evaluationService.findByEvaluationScore();

    }
}