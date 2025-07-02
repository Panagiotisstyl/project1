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

    //TODO: make this method return Employee and not Optional<Employee> (hint u can throw exception from optional)
    @Override
    public Employee findById(int theId) {

       return employeeRepository.findById(theId).orElseThrow(()->new RuntimeException("Employee not found"));

    }

    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepository.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        employeeRepository.deleteById(theId);
    }

}
