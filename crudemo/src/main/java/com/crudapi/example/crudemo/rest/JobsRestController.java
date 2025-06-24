package com.crudapi.example.crudemo.rest;



import com.crudapi.example.crudemo.converter.JobConverter;
import com.crudapi.example.crudemo.dtos.JobDto;
import com.crudapi.example.crudemo.dtos.JobResponseDto;
import com.crudapi.example.crudemo.entity.Jobs;
import com.crudapi.example.crudemo.service.JobsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class JobsRestController {


    private final JobsService jobsService;
    private final JobConverter jobConverter;


    @GetMapping("jobs")
    public List<JobResponseDto> findAll() {

        return jobConverter.toDtoList(jobsService.findAll());

    }

    @GetMapping("jobs/{jobId}")
    public JobResponseDto findById(@PathVariable int jobId) {

      return jobConverter.toResponseDto(jobsService.findById(jobId).get());

    }

    @PostMapping("jobs")
    public JobResponseDto addJob(@RequestBody Jobs theJob) {

        return jobConverter.toResponseDto(jobsService.save(theJob));

    }


    @PutMapping("/jobs/{jobsId}")
    public void updateJob(@PathVariable int jobsId, @RequestBody JobDto theJob) {
        Jobs job = jobsService.findById(jobsId).get();
        Jobs jobtoUpdate=jobConverter.toEntity(theJob,job);
        jobsService.save(jobtoUpdate);
    }

    @DeleteMapping("/jobs/{jobsId}")
    public String deleteJob(@PathVariable int jobsId) {

        Optional<Jobs> job = jobsService.findById(jobsId);


        if(job.isEmpty()) {
            throw new RuntimeException("Job id not found - " + jobsId);
        }

        jobsService.deleteById(jobsId);

        return "Deleted job id: " + jobsId;
    }
}
