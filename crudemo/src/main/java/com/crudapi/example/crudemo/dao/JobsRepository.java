package com.crudapi.example.crudemo.dao;


import com.crudapi.example.crudemo.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, Integer> {
}
