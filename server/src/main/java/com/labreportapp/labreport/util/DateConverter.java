package com.labreportapp.labreport.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author thangncph26123
 */
public class DateConverter {

    public static String convertDateToString(long dateInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd 'at' HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String formattedDate = sdf.format(new Date(dateInMillis));
        return formattedDate;
    }

    public static Long convertDateToLongOneHourOneMinutes(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateToStringMail(long dateInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm aa");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String formattedDate = sdf.format(new Date(dateInMillis));
        return formattedDate;
    }

    public static String convertDateToStringNotTime(long dateInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String formattedDate = sdf.format(new Date(dateInMillis));
        return formattedDate;
    }

    public static String convertDateToStringTodo(Long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
        String formattedTime = dateFormat.format(date);
        return formattedTime;
    }

    public static String formatTime(int hour, int minute) {
        String formattedHour = hour < 10 ? "0" + hour : String.valueOf(hour);
        String formattedMinute = minute < 10 ? "0" + minute : String.valueOf(minute);
        return formattedHour + ":" + formattedMinute;
    }

    public static String convertHourAndMinuteToString(Integer startHour, Integer startMinute, Integer endHour, Integer endMinute) {
        String startTime = formatTime(startHour, startMinute);
        String endTime = formatTime(endHour, endMinute);
        return startTime + " : " + endTime;
    }

    public static void main(String[] args) {
        long dateInMillis = new Date().getTime();
        String formattedDate = DateConverter.convertDateToString(dateInMillis);
    }
}
