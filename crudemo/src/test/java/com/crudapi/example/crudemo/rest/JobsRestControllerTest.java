package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.dao.JobsRepository;
import com.crudapi.example.crudemo.dtos.JobDto;
import com.crudapi.example.crudemo.dtos.JobResponseDto;
import com.crudapi.example.crudemo.entity.Jobs;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class JobsRestControllerTest extends ControllerTestHelper{



    @Autowired
    private JobsRepository jobsRepository;


    @BeforeEach
    public void clearDb(){
        jobsRepository.deleteAll();
    }

    @Test
    public void testAddJob()  throws Exception {

        JobDto jobDto= JobDto.builder()
                .job_Desc("BackEnd Engineer")
                .build();

        var result = mockMvc.perform(post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobDto)))
                .andExpect(status().isOk())
                .andReturn();

        var returnedJob=readingValue(result,JobResponseDto.class);


        assertThat(returnedJob.getJob_Desc()).isEqualTo(jobDto.getJob_Desc());

        assertThat(jobsRepository.findAll()).hasSize(1);

    }

    @Test
    public void testFindAll() throws Exception {

        Jobs job1=jobsRepository.save(Jobs.builder().job_Desc("BackEnd Engineer").build());

        Jobs job2=jobsRepository.save(Jobs.builder().job_Desc("FrontEnd Engineer").build());

        List<JobResponseDto> actualJobs=List.of(
                JobResponseDto.builder()
                        .id(job1.getId())
                        .job_Desc("BackEnd Engineer")
                        .build(),

                JobResponseDto.builder()
                        .id(job2.getId())
                        .job_Desc("FrontEnd Engineer")
                        .build()
        );

        var result = mockMvc.perform(get("/api/v1/jobs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<JobResponseDto> expectedJob = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<JobResponseDto>>(){}
        );



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

        Jobs pan=jobsRepository.save(Jobs.builder().job_Desc("BackEnd Engineer").build());

        JobResponseDto actualJob=JobResponseDto.builder()
                .id(pan.getId())
                .job_Desc(pan.getJob_Desc())
                .build();

        String url="/api/v1/jobs/"+pan.getId();

        var result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();



        var returnedjob=readingValue(result,JobResponseDto.class);

        assertThat(returnedjob.getJob_Desc()).isEqualTo(actualJob.getJob_Desc());

    }

    @Test
    public void testUpdateJob()  throws Exception {

        Jobs pan=jobsRepository.save(Jobs.builder().job_Desc("BackEnd Engineer").build());

        JobDto jobDto= JobDto.builder()
                .job_Desc("BackEnd Engineer")
                .build();

        String url="/api/v1/jobs/"+pan.getId();

        var result = mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobDto)))
                .andExpect(status().isOk())
                .andReturn();

        Jobs job= jobsRepository.findById(pan.getId()).get();

        assertThat(job.getJob_Desc()).isEqualTo(jobDto.getJob_Desc());

    }

    @Test
    public void testDeleteJob()  throws Exception {
        Jobs job=jobsRepository.save(Jobs.builder().job_Desc("BackEnd Engineer").build());

        String url="/api/v1/jobs/"+job.getId();

        var result = mockMvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(jobsRepository.findById(job.getId())).isEmpty();

    }

}
