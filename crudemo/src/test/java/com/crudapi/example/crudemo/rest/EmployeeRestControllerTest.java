package com.crudapi.example.crudemo.rest;


import com.crudapi.example.crudemo.converter.EmployeeConverter;
import com.crudapi.example.crudemo.dao.EmployeeRepository;
import com.crudapi.example.crudemo.dtos.EmployeeDto;
import com.crudapi.example.crudemo.dtos.EmployeeResponseDto;
import com.crudapi.example.crudemo.entity.Employee;
import com.crudapi.example.crudemo.utilites.DateUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




public class EmployeeRestControllerTest extends ControllerTestHelper{



    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeConverter employeeConverter;


    @BeforeEach
    public void clearDb() {
        employeeRepository.deleteAll();
    }


    @Test
    public void testAddEmployee() throws Exception {

        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Pan")
                .lastName("Styl")
                .email("panstyl@email.com")
                .dateJoined("01-07-2025")
                .build();

        var result = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andReturn();


        //EmployeeRepsonseDto object that we get from the response
        //returnedEmployee holds the data that the controller returned

        var returnedEmployee=readingValue(result, EmployeeResponseDto.class);

        assertThat(returnedEmployee.getFirstName()).isEqualTo(employeeDto.getFirstName());
        assertThat(returnedEmployee.getLastName()).isEqualTo(employeeDto.getLastName());
        assertThat(returnedEmployee.getEmail()).isEqualTo(employeeDto.getEmail());
        assertThat(returnedEmployee.getDateJoined()).isEqualTo(employeeDto.getDateJoined());

        assertThat(employeeRepository.findAll()).hasSize(1);

    }

    @Test
    public void testFindAll()  throws Exception {

        Employee pan1=employeeRepository.save(Employee.builder()
                .firstName("Pan")
                .lastName("Styl")
                .email("panstyl@email.com")
                .dateJoined(1748811600L).build());

        Employee pan2=employeeRepository.save(Employee.builder()
                .firstName("Panay")
                .lastName("Stylian")
                .email("panstylian@email.com")
                .dateJoined(1751317200L).build());

        List<EmployeeResponseDto> actualEmpl=List.of(
                EmployeeResponseDto.builder()
                        .id(pan1.getId())
                        .firstName("Pan")
                        .lastName("Styl")
                        .email("panstyl@email.com")
                        .dateJoined("02-06-2025").build(),
                EmployeeResponseDto.builder()
                        .id(pan2.getId())
                        .firstName("Panay")
                        .lastName("Stylian")
                        .email("panstylian@email.com")
                        .dateJoined("01-07-2025").build()

        );

        var result = mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<EmployeeResponseDto> expectedEmpl = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<EmployeeResponseDto>>() {}
        );

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

        Employee pan=employeeRepository.save(Employee.builder()
                .firstName("Pan")
                .lastName("Styl")
                .email("panstyl@email.com")
                .dateJoined(1748811600L).build());

        EmployeeResponseDto employeeDto=employeeConverter.toResponseDto(pan);

        String url="/api/v1/employees/"+pan.getId();

        var result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();



        var returnedEmployee=readingValue(result, EmployeeResponseDto.class);

        assertThat(returnedEmployee.getFirstName()).isEqualTo(employeeDto.getFirstName());
        assertThat(returnedEmployee.getLastName()).isEqualTo(employeeDto.getLastName());
        assertThat(returnedEmployee.getEmail()).isEqualTo(employeeDto.getEmail());

    }

    @Test
    public void testUpdateEmployee()  throws Exception {

        Employee pan=employeeRepository.save(Employee.builder()
                .firstName("Pan")
                .lastName("Styl")
                .email("panstyl@email.com")
                .dateJoined(1748811600L).build());


        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Panay")
                .lastName("Stylian")
                .email("panstylian@email.com")
                .dateJoined("02-06-2025")
                .build();

        String url="/api/v1/employees/"+pan.getId();

        var result = mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andReturn();



        Employee employee=employeeRepository.findById(pan.getId()).get();


        assertThat(employee.getFirstName()).isEqualTo(employeeDto.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(employeeDto.getLastName());
        assertThat(employee.getEmail()).isEqualTo(employeeDto.getEmail());
        assertThat(DateUtil.toDateString(employee.getDateJoined())).isEqualTo(employeeDto.getDateJoined());

    }

    @Test
    public void testDeleteEmployee()  throws Exception {

        Employee pan=employeeRepository.save(Employee.builder()
                .firstName("Pan")
                .lastName("Styl")
                .email("panstyl@email.com")
                .dateJoined(1748811600L).build());


        String url="/api/v1/employees/"+pan.getId();

        var result = mockMvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(employeeRepository.findById(pan.getId())).isEmpty();
    }
}
