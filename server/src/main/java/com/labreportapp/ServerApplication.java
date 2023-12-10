package com.labreportapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
public class ServerApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SpringApplication.run(ServerApplication.class, args);
        Timer timer = new Timer();
        timer.schedule(new CustomTask(), 0, 60000);
    }

    static class CustomTask extends TimerTask {
        @Override
        public void run() {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            ZoneId vietnamZone = ZoneId.of("Asia/Ho_Chi_Minh");
            String formattedTime = currentTime.atZone(vietnamZone).format(formatter);
            System.err.println("Ngày giờ hiện tại (Việt Nam):........ " + formattedTime);
        }
    }
}
