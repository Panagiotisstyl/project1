package com.crudapi.example.crudemo.dao;

import com.crudapi.example.crudemo.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
}
