package com.crudapi.example.crudemo.service;

import com.crudapi.example.crudemo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();
    Employee findById(int theId);
    Employee save(Employee theEmployee);
    boolean deleteById(int theId);
}
