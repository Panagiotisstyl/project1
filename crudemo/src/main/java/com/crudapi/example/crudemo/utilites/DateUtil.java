package com.crudapi.example.crudemo.utilites;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class DateUtil {



    public static String toDateString(long epochdate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(new Date(epochdate*1000));

    }

    public static Long toEpoch(LocalDate date) {


        return date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();


    }


}
