package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.converter.EmployeeConverter;
import com.crudapi.example.crudemo.dtos.EmployeeDto;
import com.crudapi.example.crudemo.dtos.EmployeeResponseDto;
import com.crudapi.example.crudemo.entity.Employee;
import com.crudapi.example.crudemo.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class EmployeeRestController {

    private final EmployeeService employeeService;
    private final EmployeeConverter employeeConverter;

    @GetMapping("/employees")
    public List<EmployeeResponseDto> findALl() {
        return employeeConverter.toDtoList(employeeService.findAll());
    }

    @GetMapping("/employees/{employeeId}")
    public EmployeeResponseDto findById(@PathVariable int employeeId) {
        return employeeConverter.toResponseDto(employeeService.findById(employeeId).get());
    }

    @PostMapping("/employees")
    public EmployeeResponseDto addEmployee(@RequestBody Employee theEmployee) {

        return employeeConverter.toResponseDto(employeeService.save(theEmployee));
    }


    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@PathVariable int employeeId, @RequestBody EmployeeDto theEmployeedto) {
        Employee employee = employeeService.findById(employeeId).get();
        Employee employeeToUpdate = employeeConverter.toEntity(theEmployeedto, employee);
        employeeService.save(employeeToUpdate);
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
