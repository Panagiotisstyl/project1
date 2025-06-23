package com.crudapi.example.crudemo.rest;



import com.crudapi.example.crudemo.entity.Jobs;
import com.crudapi.example.crudemo.service.JobsService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/JApi")
public class JobsRestController {

    private JobsService jobsService;

    public JobsRestController(JobsService jobsService) {
        this.jobsService = jobsService;
    }

    @GetMapping("jobs")
    public List<Jobs> findAll() {

        return jobsService.findAll();

    }

    @GetMapping("jobs/{jobId}")
    public Jobs findById(@PathVariable int jobId) {

        Optional<Jobs> job = jobsService.findById(jobId);
        return job.orElseThrow(()->new RuntimeException("Job id not found"));

    }

    @PostMapping("jobs")
    public Jobs addJob(@RequestBody Jobs theJob) {

        return jobsService.save(theJob);

    }

    @Transactional
    @PutMapping("/jobs/{jobsId}")
    public void updateJob(@PathVariable int jobsId, @RequestBody Jobs theJob) {

        Optional<Jobs> job = jobsService.findById(jobsId);
        if (job.isPresent()) {

            Jobs dbJob = job.get();
            dbJob.setJob_Desc(theJob.getJob_Desc());

        } else {
            throw new RuntimeException("Job id not found - " + jobsId  );
        }
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
