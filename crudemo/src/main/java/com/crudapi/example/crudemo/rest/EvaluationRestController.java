package com.crudapi.example.crudemo.rest;

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
    public List<Evaluation> findAll() {

        return evaluationService.findAll();

    }

    @GetMapping("/evaluation/{evaluationId}")
    public Evaluation findById(@PathVariable int evaluationId) {

        Optional<Evaluation> evaluation = evaluationService.findById(evaluationId);
        return evaluation.orElseThrow(() -> new RuntimeException("Evaluation not found with id " + evaluationId));
    }

    @PostMapping("/evaluation")
    public Evaluation save(@RequestBody Evaluation evaluation) {

        return evaluationService.save(evaluation);

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
    public List<Evaluation> getByScore() {

        return evaluationService.findByEvaluationScore();

    }
}