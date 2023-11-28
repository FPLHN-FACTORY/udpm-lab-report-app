package com.labreportapp.portalprojects.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author thangncph26123
 */
public class ConvertDateToLong {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = simpleDateFormat.parse("20-04-2023");
        long longDate = (long) date.getTime();
    }
}
