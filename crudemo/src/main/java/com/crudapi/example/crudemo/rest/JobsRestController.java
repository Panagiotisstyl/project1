package com.crudapi.example.crudemo.rest;


import com.crudapi.example.crudemo.converter.JobConverter;
import com.crudapi.example.crudemo.dtos.JobDto;
import com.crudapi.example.crudemo.dtos.JobResponseDto;
import com.crudapi.example.crudemo.entity.Jobs;
import com.crudapi.example.crudemo.service.JobsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class JobsRestController {


    private final JobsService jobsService;


    @GetMapping("jobs")
    public List<JobResponseDto> findAll() {
        return JobConverter.toDtoList(jobsService.findAll());
    }

    @GetMapping("jobs/{jobId}")
    public JobResponseDto findById(@PathVariable int jobId) {
      return JobConverter.toResponseDto(jobsService.findById(jobId));
    }

    @PostMapping("jobs")
    public JobResponseDto addJob(@RequestBody Jobs theJob) {
        return JobConverter.toResponseDto(jobsService.save(theJob));
    }


    @PutMapping("/jobs/{jobsId}")
    public void updateJob(@PathVariable int jobsId, @RequestBody JobDto theJob) {
        Jobs job = jobsService.findById(jobsId);
        Jobs jobtoUpdate=JobConverter.toEntity(theJob,job);
        jobsService.save(jobtoUpdate);
    }

    @DeleteMapping("/jobs/{jobsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable int jobsId) {

        if(!jobsService.deleteById(jobsId))
            throw new RuntimeException("Job not found");

    }
}
