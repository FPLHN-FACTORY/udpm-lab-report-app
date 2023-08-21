package com.labreportapptool;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class ConvertDateToLong {
//    public static void main(String[] args) {
////    System.out.println("Thang truoc");
////      LocalDateTime localDateTime = LocalDateTime.of(2023, 8, 18, 0, 0);
////       long timestamp = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
////        System.out.println("Timestamp: " + timestamp);
////        System.out.println("Thang sau");
////        LocalDateTime localDateTimee = LocalDateTime.of(2023, 8, 19, 0, 0);
////        long timestampp = localDateTimee.toInstant(ZoneOffset.UTC).toEpochMilli();
////        System.out.println("Timestamp: " + timestampp);
//
//        LocalDateTime currentTime = LocalDateTime.now(); // Lấy thời điểm hiện tại
//        long nextDayTimestamps = currentTime.toInstant(ZoneOffset.UTC).toEpochMilli();
//        LocalDateTime nextDay = currentTime.plusDays(1); // Thêm 1 ngày
//        System.out.println("push day "+nextDayTimestamps);
//        long nextDayTimestamp = nextDay.toInstant(ZoneOffset.UTC).toEpochMilli(); // Chuyển đổi thành kiểu Long
//        System.out.println("Next day timestamp: " + nextDayTimestamp);
//    }
public static void main(String[] args) {
    // Giá trị Long (timestamp)
    long timestamp = 1679856000000L; // Đây là timestamp tương ứng với ngày 2023-08-17

    // Bước 1: Chuyển đổi Long thành Date
    Date date = new Date(timestamp);

    // Bước 2: Sử dụng Calendar để thêm 1 ngày
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, 1);

    // Bước 3: Chuyển đối tượng Date mới thành Long (timestamp)
    long newTimestamp = calendar.getTimeInMillis();
long a = newTimestamp - timestamp;
    System.out.println("----------------------"+a);
    System.out.println("Ngày gốc: " + new Date(timestamp));
    System.out.println("Ngày sau khi cộng 1 ngày: " + new Date(newTimestamp));
}
}
