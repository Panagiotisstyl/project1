package com.crudapi.example.crudemo.rest;

import com.crudapi.example.crudemo.dao.EmployeeRepository;
import com.crudapi.example.crudemo.dao.EvaluationRepository;
import com.crudapi.example.crudemo.dao.JobsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@SpringBootTest
@AutoConfigureMockMvc
public abstract class  ControllerTestHelper {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    EvaluationRepository evaluationRepository;

    public <T> T readingValue(MvcResult result, Class<T> responseType) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(),responseType);
    }
}
