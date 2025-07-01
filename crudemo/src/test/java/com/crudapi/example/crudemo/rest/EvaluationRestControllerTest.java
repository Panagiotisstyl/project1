package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.converter.EvaluationConverter;
import com.crudapi.example.crudemo.dao.EvaluationRepository;
import com.crudapi.example.crudemo.dtos.*;
import com.crudapi.example.crudemo.entity.Employee;
import com.crudapi.example.crudemo.entity.Evaluation;
import com.crudapi.example.crudemo.entity.Jobs;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EvaluationRestControllerTest extends ControllerTestHelper{

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationConverter evaluationConverter;

    private Employee em1,em2;

    private Jobs job1,job2;

    private Evaluation ev1,ev2;

    @AfterEach
    public void clearDb() {
        evaluationRepository.deleteAll();
        jobsRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {

         em1=employeeRepository.save(Employee.builder()
                .firstName("Pan")
                .lastName("Styl")
                .email("panstyl@email.com")
                .dateJoined(1748811600L).build());

         em2=employeeRepository.save(Employee.builder()
                .firstName("Pana")
                .lastName("Stylian")
                .email("panstylian@email.com")
                .dateJoined(1748811600L).build());

         job1=jobsRepository.save(Jobs.builder().job_Desc("BackEnd Engineer").build());

         job2=jobsRepository.save(Jobs.builder().job_Desc("FrontEnd Engineer").build());

         ev1=evaluationRepository.save(Evaluation.builder()
                .employee(em1)
                .job(job1)
                .years_of_Empl(6)
                .score(7)
                .build());

         ev2=evaluationRepository.save(Evaluation.builder()
                .employee(em2)
                .job(job2)
                .years_of_Empl(3)
                .score(6)
                .build());



    }

    @Test
    public void testAddEvaluation() throws Exception {

        Employee em1=employeeRepository.save(Employee.builder()
                .firstName("Pan")
                .lastName("Styl")
                .email("panstyl@email.com")
                .dateJoined(1748811600L).build());



        Jobs job1=jobsRepository.save(Jobs.builder()
                .job_Desc("Internship")
                .build());

        EvaluationDto evaluationDto=EvaluationDto.builder()
                .employeeId(em1.getId())
                .jobId(job1.getId())
                .years_of_empl(1)
                .score(2)
                .build();

        var result = mockMvc.perform(post("/api/v1/evaluation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evaluationDto)))
                .andExpect(status().isOk())
                .andReturn();

        var returnedevaluation=readingValue(result, EvaluationResponseDto.class);

        assertThat(returnedevaluation.getEmployeeId()).isEqualTo(em1.getId());
        assertThat(returnedevaluation.getJobId()).isEqualTo(job1.getId());
        assertThat(returnedevaluation.getYears_of_empl()).isEqualTo(evaluationDto.getYears_of_empl());
        assertThat(returnedevaluation.getScore()).isEqualTo(evaluationDto.getScore());

        assertThat(evaluationRepository.findAll()).hasSize(3);

    }

    @Test
    public void testFindAll() throws Exception {

        List<EvaluationResponseDto> actualEval=List.of(

                EvaluationResponseDto.builder()
                        .id(ev1.getId())
                        .employeeId(em1.getId())
                        .jobId(job1.getId())
                        .years_of_empl(ev1.getYears_of_Empl())
                        .score(ev1.getScore())
                        .build(),

                EvaluationResponseDto.builder()
                        .id(ev2.getId())
                        .employeeId(em2.getId())
                        .jobId(job2.getId())
                        .years_of_empl(ev2.getYears_of_Empl())
                        .score(ev2.getScore())
                        .build()


        );

        var result = mockMvc.perform(get("/api/v1/evaluation"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<EvaluationResponseDto> expectedEval = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<EvaluationResponseDto>>() {}
        );

        assertThat(evaluationRepository.findAll()).hasSize(2);

        for(int i=0;i<2;i++){
            EvaluationResponseDto actualEvali=actualEval.get(i);
            EvaluationResponseDto expectedEvali=expectedEval.get(i);

            assertThat(actualEvali.getId()).isEqualTo(expectedEvali.getId());
            assertThat(actualEvali.getEmployeeId()).isEqualTo(expectedEvali.getEmployeeId());
            assertThat(actualEvali.getJobId()).isEqualTo(expectedEvali.getJobId());
            assertThat(actualEvali.getYears_of_empl()).isEqualTo(expectedEvali.getYears_of_empl());
            assertThat(actualEvali.getScore()).isEqualTo(expectedEvali.getScore());
        }

    }

    @Test
    public void testFindById() throws Exception {

        String url="/api/v1/evaluation/"+ev1.getId();

        var result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
       var returnedeval=readingValue(result, EvaluationResponseDto.class);

       assertThat(returnedeval.getEmployeeId()).isEqualTo(em1.getId());
       assertThat(returnedeval.getJobId()).isEqualTo(job1.getId());
       assertThat(returnedeval.getScore()).isEqualTo(ev1.getScore());
       assertThat(returnedeval.getYears_of_empl()).isEqualTo(ev1.getYears_of_Empl());

    }


    @Test
    public void testUpdateEvaluation() throws Exception {
        EvaluationDto evalDto=EvaluationDto.builder()
                .employeeId(em1.getId())
                .jobId(job1.getId())
                .score(1)
                .years_of_empl(1)
                .build();

        String url="/api/v1/evaluation/"+ev1.getId();

        var result = mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evalDto)))
                .andExpect(status().isOk())
                .andReturn();

        Evaluation eval=evaluationRepository.findById(ev1.getId()).get();

        assertThat(eval.getEmployee().getId()).isEqualTo(evalDto.getEmployeeId());
        assertThat(eval.getJob().getId()).isEqualTo(evalDto.getJobId());
        assertThat(eval.getScore()).isEqualTo(evalDto.getScore());
        assertThat(eval.getYears_of_Empl()).isEqualTo(evalDto.getYears_of_empl());

    }

    @Test
    public void testDeleteEvaluation() throws Exception {

        String url="/api/v1/evaluation/"+ev1.getId();

        var result = mockMvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(evaluationRepository.findById(ev1.getId())).isEmpty();

    }

    @Test
    public void testGetByScore() throws Exception {

    }
}
