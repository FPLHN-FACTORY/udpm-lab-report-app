package com.labreportapp.labreport.util;

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

}
