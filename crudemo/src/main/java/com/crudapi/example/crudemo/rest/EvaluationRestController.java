package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.converter.EvaluationConverter;
import com.crudapi.example.crudemo.dtos.EvaluationDto;
import com.crudapi.example.crudemo.dtos.EvaluationResponseDto;
import com.crudapi.example.crudemo.entity.Evaluation;
import com.crudapi.example.crudemo.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class EvaluationRestController {

    private final EvaluationConverter evaluationConverter;
    private final EvaluationService evaluationService;



    @GetMapping("/evaluation")
    public List<EvaluationResponseDto> findAll() {
        return evaluationConverter.toDtoList(evaluationService.findAll());
    }

    @GetMapping("/evaluation/{evaluationId}")
    public EvaluationResponseDto findById(@PathVariable int evaluationId) {
       return evaluationConverter.toResponseDto(evaluationService.getEvaluationById(evaluationId));

    }

    @PostMapping("/evaluation")
    public EvaluationResponseDto save(@RequestBody EvaluationDto dto) {
        return evaluationConverter.toResponseDto(evaluationService.save(evaluationConverter.toEntity(dto)));
    }

    @PutMapping("/evaluation/{evaluationId}")
    public void updateEvaluation(@PathVariable int evaluationId, @RequestBody EvaluationDto dto) {
        Evaluation em=evaluationService.findById(evaluationId).get();
        Evaluation emupdted=evaluationConverter.toEntity(dto,em);
        evaluationService.save(emupdted);
    }

    @DeleteMapping("/evaluation/{evaluationId}")
    public String deleteEvaluation(@PathVariable int evaluationId) {
        //TODO: change this just like Employee

        Optional<Evaluation> evaluation = evaluationService.findById(evaluationId);
        if(evaluation.isEmpty()){

            throw new RuntimeException("Evaluation not found with id " + evaluationId);
        }

        evaluationService.deleteById(evaluationId);

        return "Evaluation with id " + evaluationId + " deleted";
    }

    //TODO: you dont need a second API. use the findAll API but enhance it to accept SortDirection and Sort Attribute
    @GetMapping("/evaluation/byscore")
    public List<EvaluationResponseDto> getByScore() {
        return evaluationConverter.toDtoList(evaluationService.findByEvaluationScore());
    }
}