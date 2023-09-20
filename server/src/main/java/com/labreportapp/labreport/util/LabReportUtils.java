package com.labreportapp.labreport.util;

import com.labreportapp.labreport.infrastructure.constant.MeetingPeriod;

/**
 * @author thangncph26123
 */
public class LabReportUtils {

    public static String convertMeetingPeriodToTime(int meetingPeriod) {
        String meetingPeriodStr = "";

        switch (meetingPeriod) {
            case 0:
                meetingPeriodStr = "7:15 - 9:15";
                break;
            case 1:
                meetingPeriodStr = "9:25 - 11:25";
                break;
            case 2:
                meetingPeriodStr = "12:00 - 14:00";
                break;
            case 3:
                meetingPeriodStr = "14:10 - 16:10";
                break;
            case 4:
                meetingPeriodStr = "16:20 - 18:20";
                break;
            case 5:
                meetingPeriodStr = "18:30 - 20:30";
                break;
            case 6:
                meetingPeriodStr = "20:40 - 22:40";
                break;
            case 7:
                meetingPeriodStr = "22:50 - 00:50";
                break;
            case 8:
                meetingPeriodStr = "01:00 - 3:00";
                break;
            case 9:
                meetingPeriodStr = "03:10 - 05:10";
                break;
            default:
                meetingPeriodStr = "";
        }

        return meetingPeriodStr;
    }

    public static Integer convertMeetingPeriodToInteger(MeetingPeriod meetingPeriod) {
        Integer meetingPeriodInt = null;

        switch (meetingPeriod) {
            case CA_1:
                meetingPeriodInt = 0;
                break;
            case CA_2:
                meetingPeriodInt = 1;
                break;
            case CA_3:
                meetingPeriodInt = 2;
                break;
            case CA_4:
                meetingPeriodInt = 3;
                break;
            case CA_5:
                meetingPeriodInt = 4;
                break;
            case CA_6:
                meetingPeriodInt = 5;
                break;
            case CA_7:
                meetingPeriodInt = 6;
                break;
            case CA_8:
                meetingPeriodInt = 7;
                break;
            case CA_9:
                meetingPeriodInt = 8;
                break;
            case CA_10:
                meetingPeriodInt = 9;
                break;
        }

        return meetingPeriodInt;
    }

}
