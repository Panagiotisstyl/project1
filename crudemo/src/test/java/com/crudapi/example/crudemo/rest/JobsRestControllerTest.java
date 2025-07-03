package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.converter.JobConverter;
import com.crudapi.example.crudemo.dao.JobsRepository;
import com.crudapi.example.crudemo.dtos.JobDto;
import com.crudapi.example.crudemo.dtos.JobResponseDto;
import com.crudapi.example.crudemo.entity.Jobs;
import com.crudapi.example.crudemo.factories.JobFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class JobsRestControllerTest extends ControllerTestHelper{



    @Autowired
    private JobsRepository jobsRepository;


    @Test
    public void testAddJob()  throws Exception {

        JobDto jobDto= JobFactory.createJobDto("BackEnd Engineer");

        var result=performPost("/api/v1/jobs",jobDto);


        var returnedJob=readingValue(result,JobResponseDto.class);


        assertThat(returnedJob.getJob_Desc()).isEqualTo(jobDto.getJob_Desc());

        assertThat(jobsRepository.findAll()).hasSize(1);

    }

    @Test
    public void testFindAll() throws Exception {


        Jobs job1=jobsRepository.save(JobFactory.createJob("BackEnd Engineer"));

        Jobs job2=jobsRepository.save(JobFactory.createJob("FrontEnd Engineer"));

        List<JobResponseDto> actualJobs=List.of(
                JobConverter.toResponseDto(job1),
                JobConverter.toResponseDto(job2)
        );


        var result=performGet("/api/v1/jobs");


        List<JobResponseDto> expectedJob=readingValue(result,new TypeReference<List<JobResponseDto>>(){});

        assertThat(jobsRepository.findAll()).hasSize(2);

        for(int i=0;i<2;i++){

            JobResponseDto actualJobi=actualJobs.get(i);
            JobResponseDto expectedJobi=expectedJob.get(i);

            assertThat(actualJobi.getId()).isEqualTo(expectedJobi.getId());
            assertThat(actualJobi.getJob_Desc()).isEqualTo(expectedJobi.getJob_Desc());

        }
    }

    @Test
    public void testFindById() throws Exception {

        Jobs pan=jobsRepository.save(JobFactory.createJob("BackEnd Engineer"));

        JobResponseDto actualJob=JobConverter.toResponseDto(pan);


        var result=performGet("/api/v1/jobs/"+pan.getId());

        var returnedjob=readingValue(result,JobResponseDto.class);

        assertThat(returnedjob.getJob_Desc()).isEqualTo(actualJob.getJob_Desc());

        //HANDLING EXCEPTION

        mockMvc.perform(get("/api/v1/jobs/1231"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("Job not found"));

    }

    @Test
    public void testUpdateJob()  throws Exception {

        Jobs pan=jobsRepository.save(JobFactory.createJob("BackEnd Engineer"));

        JobDto jobDto= JobFactory.createJobDto("FrontEnd Engineer");


        performPut("/api/v1/jobs/"+pan.getId(),jobDto);

        Jobs job= jobsRepository.findById(pan.getId()).get();

        assertThat(job.getJob_Desc()).isEqualTo(jobDto.getJob_Desc());

    }

    @Test
    public void testDeleteJob()  throws Exception {

        //SUCCESSFUL DELETION

        Jobs job=jobsRepository.save(JobFactory.createJob("BackEnd Engineer"));

        performDelete("/api/v1/jobs/"+job.getId());

        assertThat(jobsRepository.findById(job.getId())).isEmpty();

        //THROWING EXCEPTION, HANDLER CATCHING IT

        mockMvc.perform(delete("/api/v1/jobs/12"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("Job not found"));

    }

}
