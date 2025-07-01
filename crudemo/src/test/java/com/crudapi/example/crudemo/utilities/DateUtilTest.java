package com.crudapi.example.crudemo.utilities;

import com.crudapi.example.crudemo.dao.EmployeeRepository;
import com.crudapi.example.crudemo.utilites.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;



@SpringBootTest
@AutoConfigureMockMvc
public class DateUtilTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DateUtil dUt;

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

    @Test
    public void toDateSrtingTest() throws Exception {

        String expected_date=dUt.toDateString(1751006429L);//random time stamp of the current day of the test

        String actual_date_str=LocalDate.now().format(formatter);

        assertThat(actual_date_str).isEqualTo(expected_date);


    }

    @Test
    public void toEpochTest() throws Exception {

        long expected_epoch=dUt.toEpoch("27-06-2025");//date today epoch will be the first 00:00:00

        long actual_epoch= 1750971600L;//give epoch at today's date, but at the very start(00:00:00)

        assertThat(actual_epoch).isEqualTo(expected_epoch);


    }
}

