package com.crudapi.example.crudemo.service;


import com.crudapi.example.crudemo.entity.Jobs;

import java.util.List;
import java.util.Optional;

public interface JobsService {

    List<Jobs> findAll();
    Optional<Jobs> findById(int theId);
    Jobs save(Jobs theJob);
    void deleteById(int theId);

}
