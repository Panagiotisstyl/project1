package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.converter.EvaluationConverter;
import com.crudapi.example.crudemo.dtos.EvaluationDto;
import com.crudapi.example.crudemo.dtos.EvaluationResponseDto;
import com.crudapi.example.crudemo.entity.Evaluation;
import com.crudapi.example.crudemo.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class EvaluationRestController {

    private final EvaluationConverter evaluationConverter;
    private final EvaluationService evaluationService;



    @GetMapping("/evaluation")
    public List<EvaluationResponseDto> findAll(@RequestParam(defaultValue="id") String sortBy, @RequestParam(defaultValue = "ASC") String direction) {

        return evaluationConverter.toDtoList(evaluationService.findAll(sortBy, direction));

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
        Evaluation em=evaluationService.getEvaluationById(evaluationId);
        Evaluation emupdted=evaluationConverter.toEntity(dto,em);
        evaluationService.save(emupdted);
    }

    @DeleteMapping("/evaluation/{evaluationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvaluation(@PathVariable int evaluationId) {
        if(!evaluationService.deleteById(evaluationId))
            throw new RuntimeException("Evaluation not found");

    }

}