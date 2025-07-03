package com.crudapi.example.crudemo.factories;

import com.crudapi.example.crudemo.dtos.JobDto;
import com.crudapi.example.crudemo.entity.Jobs;

public class JobFactory {

    public static Jobs createJob(String jobDesc) {
        return Jobs.builder().job_Desc(jobDesc).build();
    }

    public static JobDto createJobDto(String jobDesc) {
        return JobDto.builder().job_Desc(jobDesc).build();
    }
}
