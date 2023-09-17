package com.labreportapp.labreport.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * @author thangncph26123
 */

public class DateTimeUtil {

    public static Long convertDateToTimeStampSecond() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    public static Long getCurrentDateInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static Long getPreviousDayInMillis() {
        long currentMillis = DateTimeUtil.getCurrentDateInMillis();
        long oneDayMillis = 24 * 60 * 60 * 1000;
        return currentMillis - oneDayMillis;
    }
}
