package com.crudapi.example.crudemo.service;

import com.crudapi.example.crudemo.dao.JobsRepository;
import com.crudapi.example.crudemo.entity.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobsServiceImpl implements JobsService {

    private JobsRepository jobRepository;

    @Autowired
    public JobsServiceImpl(JobsRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Jobs> findAll() {

        return jobRepository.findAll();

    }

    @Override
    public Optional<Jobs> findById(int theId) {

        return jobRepository.findById(theId);

    }

    @Override
    public Jobs save(Jobs theJob) {

        return jobRepository.save(theJob);

    }

    @Override
    public void deleteById(int theId) {

        jobRepository.deleteById(theId);

    }
}
