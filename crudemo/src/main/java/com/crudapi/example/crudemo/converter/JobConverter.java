package com.crudapi.example.crudemo.converter;


import com.crudapi.example.crudemo.dtos.JobDto;
import com.crudapi.example.crudemo.dtos.JobResponseDto;
import com.crudapi.example.crudemo.entity.Jobs;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class JobConverter {

    public static Jobs toEntity(JobDto jobDto){
        return toEntity(jobDto,null);
    }

    public static Jobs toEntity(JobDto jobDto, @Nullable Jobs existing){
        var builder = Jobs.builder();

        if(jobDto != null){
            builder.id(existing.getId());
        }
        return builder
                .job_Desc(jobDto.getJob_Desc())
                .build();
    }

    public static JobResponseDto toResponseDto(Jobs jobs){
        return JobResponseDto.builder()
                .id(jobs.getId())
                .job_Desc(jobs.getJob_Desc())
                .build();
    }

    public static List<JobResponseDto> toDtoList(List<Jobs> jobs) {
        return jobs.stream()
                .map(JobConverter::toResponseDto)
                .collect(Collectors.toList());
    }
}
