package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.entity.Employee;
import com.crudapi.example.crudemo.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findALl() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee findById(@PathVariable int employeeId) {

        Optional <Employee> employee = employeeService.findById(employeeId);
        return employee.orElseThrow(() -> new RuntimeException("Employee id not found"));

    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee) {

        return employeeService.save(theEmployee);
    }

    @Transactional
    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@PathVariable int employeeId, @RequestBody Employee theEmployee) {

        Optional<Employee> employee = employeeService.findById(employeeId);
        if (employee.isPresent()) {
            Employee dbEmployee = employee.get();

            dbEmployee.setFirstName(theEmployee.getFirstName());
            dbEmployee.setLastName(theEmployee.getLastName());
            dbEmployee.setEmail(theEmployee.getEmail());

        } else {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
    }


    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {

       Optional<Employee> employee = employeeService.findById(employeeId);


       if(employee.isEmpty()) {
           throw new RuntimeException("Employee id not found - " + employeeId);
       }

       employeeService.deleteById(employeeId);

       return "Deleted employee id: " + employeeId;
    }
}
