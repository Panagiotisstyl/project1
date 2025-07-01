package com.crudapi.example.crudemo.rest;



import com.crudapi.example.crudemo.dao.EmployeeRepository;
import com.crudapi.example.crudemo.dtos.EmployeeDto;
import com.crudapi.example.crudemo.dtos.EmployeeResponseDto;
import com.crudapi.example.crudemo.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@SpringBootTest
@AutoConfigureMockMvc

public class EmployeeRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("EmployeeRestController"));

    }


    @BeforeEach
    public void clearDb() {
        employeeRepository.deleteAll();
    }

   /* @Test
    public void testAddEmployee() throws Exception {

        Employee employee = Employee.builder()
                .firstName("Pan")
                .lastName("Styl")
                .email("panstyl@email.com")
                .build();

        mockMvc.perform(post("/api/v1/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employee)))
                .andDo(print())
                .andExpect(status().isOk())
                //ngfbgfd;object mapper extract object from mvc
                .andExpect(jsonPath("$.firstName").value("Pan"))
                .andExpect(jsonPath("$.lastName").value("Styl"))
                .andExpect(jsonPath("$.email").value("panstyl@email.com"));

                objectMapper.readValue()


        assertThat(employeeRepository.findAll()).hasSize(1);

    }*/

    @Test
    public void testAddEmployee() throws Exception {

        Employee employee = Employee.builder()
                .firstName("Pan")
                .lastName("Styl")
                .email("panstyl@email.com")
                .build();

        var result = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andReturn();


        //EmployeeRepsonseDto object that we get from the response
        //returnedEmployee holds the data that the controller returned
        var returnedEmployee = objectMapper.readValue(result.getResponse().getContentAsString(), EmployeeResponseDto.class);


        assertThat(returnedEmployee.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(returnedEmployee.getLastName()).isEqualTo(employee.getLastName());
        assertThat(returnedEmployee.getEmail()).isEqualTo(employee.getEmail());

        assertThat(employeeRepository.findAll()).hasSize(1);

    }
}
