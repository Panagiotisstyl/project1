package com.crudapi.example.crudemo.utilities;

import com.crudapi.example.crudemo.utilites.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;



@AutoConfigureMockMvc
public class DateUtilTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    @Test
    public void toDateSrtingTest() throws Exception {

        String expected_date=DateUtil.toDateString(1751350311L);//random time stamp of the current day of the test

        String actual_date_str=LocalDate.now().format(formatter);

        assertThat(actual_date_str).isEqualTo(expected_date);


    }

    @Test
    public void toEpochTest() throws Exception {

        long expected_epoch=DateUtil.toEpoch("01-07-2025");//date today epoch will be the first 00:00:00

        long actual_epoch=1751317200L;//give epoch at today's date, but at the very start(00:00:00)

        assertThat(actual_epoch).isEqualTo(expected_epoch);


    }
}

