package com.crudapi.example.crudemo.rest;


import com.crudapi.example.crudemo.converter.EmployeeConverter;
import com.crudapi.example.crudemo.dao.EmployeeRepository;
import com.crudapi.example.crudemo.dtos.EmployeeDto;
import com.crudapi.example.crudemo.dtos.EmployeeResponseDto;
import com.crudapi.example.crudemo.entity.Employee;
import com.crudapi.example.crudemo.factories.EmployeeFactory;
import com.crudapi.example.crudemo.utilites.DateUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class EmployeeRestControllerTest extends ControllerTestHelper{



    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    public void testAddEmployee() throws Exception {

        EmployeeDto employeeDto=EmployeeFactory.createEmployeeDto("Pan","Styl");

        var result=performPost("/api/v1/employees",employeeDto);

        var returnedEmployee=readingValue(result, EmployeeResponseDto.class);

        assertThat(returnedEmployee.getFirstName()).isEqualTo(employeeDto.getFirstName());
        assertThat(returnedEmployee.getLastName()).isEqualTo(employeeDto.getLastName());
        assertThat(returnedEmployee.getEmail()).isEqualTo(employeeDto.getEmail());
        assertThat(returnedEmployee.getDateJoined()).isEqualTo(DateUtil.toDateString((DateUtil.toEpoch(employeeDto.getDateJoined()))));

        assertThat(employeeRepository.findAll()).hasSize(1);



    }

    @Test
    public void testFindAll()  throws Exception {

        Employee pan1= employeeRepository.save(EmployeeFactory.createEmployee("Pan", "Styl"));

        Employee pan2= employeeRepository.save(EmployeeFactory.createEmployee("Panay", "Stylian"));


        List<EmployeeResponseDto> actualEmpl=List.of(
                EmployeeConverter.toResponseDto(pan1),
                EmployeeConverter.toResponseDto(pan2)
        );

        var result=performGet("/api/v1/employees");

        List<EmployeeResponseDto> expectedEmpl=readingValue(result, new TypeReference<List<EmployeeResponseDto>>() {});

        assertThat(employeeRepository.findAll()).hasSize(2);

        for(int i=0;i<2;i++){

            EmployeeResponseDto actualEmpli = actualEmpl.get(i);
            EmployeeResponseDto expectedEmpli = expectedEmpl.get(i);

            assertThat(actualEmpli.getId()).isEqualTo(expectedEmpli.getId());
            assertThat(actualEmpli.getFirstName()).isEqualTo(expectedEmpli.getFirstName());
            assertThat(actualEmpli.getLastName()).isEqualTo(expectedEmpli.getLastName());
            assertThat(actualEmpli.getEmail()).isEqualTo(expectedEmpli.getEmail());
            assertThat(actualEmpli.getDateJoined()).isEqualTo(expectedEmpli.getDateJoined());


        }



    }


    @Test
    public void testFindById()  throws Exception {

        Employee pan=employeeRepository.save(EmployeeFactory.createEmployee("Pan", "Styl"));

        EmployeeResponseDto employeeDto=EmployeeConverter.toResponseDto(pan);

        var result=performGet("/api/v1/employees/"+pan.getId());

        var returnedEmployee=readingValue(result, EmployeeResponseDto.class);

        assertThat(returnedEmployee.getFirstName()).isEqualTo(employeeDto.getFirstName());
        assertThat(returnedEmployee.getLastName()).isEqualTo(employeeDto.getLastName());
        assertThat(returnedEmployee.getEmail()).isEqualTo(employeeDto.getEmail());

        //HANDLING EXCEPTION

        mockMvc.perform(get("/api/v1/employees/1231"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Employee not found"));
    }

    @Test
    public void testUpdateEmployee()  throws Exception {

        Employee pan=employeeRepository.save(EmployeeFactory.createEmployee("Pan", "Styl"));


        EmployeeDto employeeDto=EmployeeFactory.createEmployeeDto("Panay", "Stylian");

        performPut("/api/v1/employees/"+pan.getId(),employeeDto);


        Employee employee=employeeRepository.findById(pan.getId()).get();


        assertThat(employee.getFirstName()).isEqualTo(employeeDto.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(employeeDto.getLastName());
        assertThat(employee.getEmail()).isEqualTo(employeeDto.getEmail());
        assertThat(DateUtil.toDateString(employee.getDateJoined())).isEqualTo(DateUtil.toDateString((DateUtil.toEpoch(employeeDto.getDateJoined()))));

    }

    @Test
    public void testDeleteEmployee()  throws Exception {

        //SUCCESSFUL DELETION

        Employee pan=employeeRepository.save(EmployeeFactory.createEmployee("Pan", "Styl"));

        performDelete("/api/v1/employees/"+pan.getId());

        assertThat(employeeRepository.findById(pan.getId())).isEmpty();

        //THROWING EXCEPTION, HANDLER CATCHING IT

        mockMvc.perform(delete("/api/v1/employees/123"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Employee not found"));

    }
}
