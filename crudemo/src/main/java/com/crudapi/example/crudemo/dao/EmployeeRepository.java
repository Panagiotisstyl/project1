package com.crudapi.example.crudemo.dao;

import com.crudapi.example.crudemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
