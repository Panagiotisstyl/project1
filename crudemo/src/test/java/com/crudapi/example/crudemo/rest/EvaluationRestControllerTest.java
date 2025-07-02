package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.converter.EvaluationConverter;
import com.crudapi.example.crudemo.dao.EvaluationRepository;
import com.crudapi.example.crudemo.dtos.EvaluationDto;
import com.crudapi.example.crudemo.dtos.EvaluationResponseDto;
import com.crudapi.example.crudemo.entity.Evaluation;
import com.crudapi.example.crudemo.factories.EvaluationFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EvaluationRestControllerTest extends ControllerTestHelper{

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationConverter evaluationConverter;

    @Autowired
    private EvaluationFactory evaluationFactory;


    @Test
    public void testAddEvaluation() throws Exception {


        EvaluationDto evaluationDto= evaluationFactory.createEvaluationDto(2,1);

        var result=performPost("/api/v1/evaluation",evaluationDto);


        var returnedEvaluation=readingValue(result, EvaluationResponseDto.class);

        assertThat(returnedEvaluation.getEmployeeId()).isEqualTo(evaluationDto.getEmployeeId());
        assertThat(returnedEvaluation.getJobId()).isEqualTo(evaluationDto.getJobId());
        assertThat(returnedEvaluation.getYearsOfEmpl()).isEqualTo(evaluationDto.getYearsOfEmpl());
        assertThat(returnedEvaluation.getScore()).isEqualTo(evaluationDto.getScore());

        assertThat(evaluationRepository.findAll()).hasSize(1);

    }

    @Test
    public void testFindAll() throws Exception {

        Evaluation ev1=evaluationRepository.save(evaluationFactory.createEvaluation(1,1));
        Evaluation ev2=evaluationRepository.save(evaluationFactory.createEvaluation(2,2));

        List<EvaluationResponseDto> actualEval=List.of(
            evaluationConverter.toResponseDto(ev1), evaluationConverter.toResponseDto(ev2)
        );

        var result=performGet("/api/v1/evaluation");

        List<EvaluationResponseDto> expectedEval =readingValue(result,new TypeReference<List<EvaluationResponseDto>>() {});

        assertThat(evaluationRepository.findAll()).hasSize(2);

        for(int i=0;i<2;i++){
            EvaluationResponseDto actualEvali=actualEval.get(i);
            EvaluationResponseDto expectedEvali=expectedEval.get(i);

            assertThat(actualEvali.getId()).isEqualTo(expectedEvali.getId());
            assertThat(actualEvali.getEmployeeId()).isEqualTo(expectedEvali.getEmployeeId());
            assertThat(actualEvali.getJobId()).isEqualTo(expectedEvali.getJobId());
            assertThat(actualEvali.getYearsOfEmpl()).isEqualTo(expectedEvali.getYearsOfEmpl());
            assertThat(actualEvali.getScore()).isEqualTo(expectedEvali.getScore());
        }

    }

    @Test
    public void testFindById() throws Exception {

        Evaluation ev1=evaluationRepository.save(evaluationFactory.createEvaluation(1,1));

       var result=performGet("/api/v1/evaluation/"+ev1.getId());

       var returnedEval =readingValue(result, EvaluationResponseDto.class);

       assertThat(returnedEval.getEmployeeId()).isEqualTo(ev1.getEmployee().getId());
       assertThat(returnedEval.getJobId()).isEqualTo(ev1.getJob().getId());
       assertThat(returnedEval.getScore()).isEqualTo(ev1.getScore());
       assertThat(returnedEval.getYearsOfEmpl()).isEqualTo(ev1.getYearsOfEmpl());

    }


    @Test
    public void testUpdateEvaluation() throws Exception {

        Evaluation ev1=evaluationRepository.save(evaluationFactory.createEvaluation(1,1));



        EvaluationDto evalDto=evaluationFactory.createEvaluationDto(4,3);

        performPut("/api/v1/evaluation/"+ev1.getId(),evalDto);

        Evaluation eval=evaluationRepository.findById(ev1.getId()).get();

        assertThat(eval.getEmployee().getId()).isEqualTo(evalDto.getEmployeeId());
        assertThat(eval.getJob().getId()).isEqualTo(evalDto.getJobId());
        assertThat(eval.getScore()).isEqualTo(evalDto.getScore());
        assertThat(eval.getYearsOfEmpl()).isEqualTo(evalDto.getYearsOfEmpl());

    }

    @Test
    public void testDeleteEvaluation() throws Exception {

        Evaluation ev1=evaluationRepository.save(evaluationFactory.createEvaluation(1,1));

        performDelete("/api/v1/evaluation/"+ev1.getId());

        assertThat(evaluationRepository.findById(ev1.getId())).isEmpty();

    }


}
