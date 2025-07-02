package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.converter.EmployeeConverter;
import com.crudapi.example.crudemo.dtos.EmployeeDto;
import com.crudapi.example.crudemo.dtos.EmployeeResponseDto;
import com.crudapi.example.crudemo.entity.Employee;
import com.crudapi.example.crudemo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeResponseDto> findALl() {
        return EmployeeConverter.toDtoList(employeeService.findAll());
    }

    @GetMapping("/employees/{employeeId}")
    public EmployeeResponseDto findById(@PathVariable int employeeId) {
        return EmployeeConverter.toResponseDto(employeeService.findById(employeeId));
    }

    @PostMapping("/employees")
    public EmployeeResponseDto addEmployee(@RequestBody EmployeeDto theEmployeedto) {

        return EmployeeConverter.toResponseDto(employeeService.save(EmployeeConverter.toEntity(theEmployeedto)));
    }


    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@PathVariable int employeeId, @RequestBody EmployeeDto theEmployeedto) {
        Employee employee = employeeService.findById(employeeId);
        Employee employeeToUpdate = EmployeeConverter.toEntity(theEmployeedto, employee);
        employeeService.save(employeeToUpdate);
    }


    //TODO: There is a way when you call the repository.deleteById method, it can return you something that you can understand that it actually deleted something or not. And then throw the exception that the employee was not found
    //TODO: instead of returning String return Void
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable int employeeId) {
       employeeService.findById(employeeId);
       employeeService.deleteById(employeeId);
    }


}
