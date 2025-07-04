package com.crudapi.example.crudemo.service;

import com.crudapi.example.crudemo.dao.JobsRepository;
import com.crudapi.example.crudemo.entity.Jobs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JobsServiceImpl implements JobsService {

    private final JobsRepository jobRepository;


    @Override
    public List<Jobs> findAll() {

        return jobRepository.findAll();

    }

    @Override
    public Jobs findById(int Id) {

        return jobRepository.findById(Id).orElseThrow(()->new RuntimeException("Job not found"));

    }

    @Override
    public Jobs save(Jobs theJob) {

        return jobRepository.save(theJob);

    }

    @Override
    public boolean deleteById(int Id) {
        if(!jobRepository.existsById(Id))
            return false;
        jobRepository.deleteById(Id);
        return true;
    }
}
