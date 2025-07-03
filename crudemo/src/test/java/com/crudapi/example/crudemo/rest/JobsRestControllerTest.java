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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


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

        Jobs job=jobsRepository.save(JobFactory.createJob("BackEnd Engineer"));

        performDelete("/api/v1/jobs/"+job.getId());

        assertThat(jobsRepository.findById(job.getId())).isEmpty();

    }

}
