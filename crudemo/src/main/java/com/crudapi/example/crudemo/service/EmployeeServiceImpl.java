package com.crudapi.example.crudemo.service;

import com.crudapi.example.crudemo.dao.EmployeeRepository;
import com.crudapi.example.crudemo.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;


    @Override
    public List<Employee> findAll() {

        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int Id) {

       return employeeRepository.findById(Id).orElseThrow(()->new RuntimeException("Employee not found"));

    }

    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepository.save(theEmployee);
    }

    @Override
    public boolean deleteById(int Id) {

        if(!employeeRepository.existsById(Id))
            return false;
        employeeRepository.deleteById(Id);
        return true;
    }

}
