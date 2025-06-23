package com.crudapi.example.crudemo.dao;


import com.crudapi.example.crudemo.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepository extends JpaRepository<Jobs, Integer> {
}
