package com.crudapi.example.crudemo.service;


import com.crudapi.example.crudemo.entity.Jobs;

import java.util.List;

public interface JobsService {

    List<Jobs> findAll();
    Jobs findById(int Id);
    Jobs save(Jobs theJob);
    boolean deleteById(int Id);

}
