package com.crudapi.example.crudemo.utilites;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Component
public class DateUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public String toDateString(long epochdate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(new Date(epochdate*1000));

    }

    public Long toEpoch(String dateString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate date = LocalDate.parse(dateString, formatter);

        return date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();


    }

    public static void validateDateFormat(String dateString) {
        try{
            LocalDate.parse(dateString, formatter);
        }catch(DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date format");
        }
    }

}
