package com.labreportapp.labreport.core.teacher.cronjob;

import com.labreportapp.labreport.core.teacher.repository.TeMeetingPeriodRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.infrastructure.constant.StatusMeeting;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author hieundph25894
 */
@Component
public class TeCronJob {

    public static final String SOUND = "static/sounds/sound1.wav";

    @Autowired
    private TeMeetingRepository teMeetingRepository;

    @Autowired
    private LabReportAppSession labReportAppSession;

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private TeMeetingPeriodRepository teMeetingPeriodRepository;

    @Autowired
    public TeCronJob(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public boolean isCurrentTimeWithinRange(int startHour, int startMinute, int endHour, int endMinute) {
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalTime endTime = LocalTime.of(endHour, endMinute);
        return currentTime.isAfter(endTime);
    }

    @Async
    @Transactional
    public void updateStatusMeetingJob() {
        List<Meeting> listMeeting = teMeetingRepository.findMeetingToDayUpdate(new Date().getTime());
        if (listMeeting.size() == 0) {
            return;
        }
        List<MeetingPeriod> listMeetingPeriod = teMeetingPeriodRepository.findAll();
        if (listMeetingPeriod.size() == 0) {
            return;
        }
        List<Meeting> listUp = new ArrayList<>();
        listMeeting.forEach(item -> {
            if (!item.getMeetingPeriod().equals("") || item.getMeetingPeriod() != null) {
                listMeetingPeriod.forEach(period -> {
                    if (period.getId().equals(item.getMeetingPeriod())) {
                        boolean checkBetweenPeriod = isCurrentTimeWithinRange(period.getStartHour(),
                                period.getStartMinute(), period.getEndHour(), period.getEndMinute());
                        if (checkBetweenPeriod) {
                            item.setStatusMeeting(StatusMeeting.BUOI_NGHI);
                            listUp.add(item);
                        }
                    }
                });
                if (DateUtils.truncate(new Date(item.getMeetingDate()), Calendar.DATE).getTime() < DateUtils.truncate(new Date(), Calendar.DATE).getTime()) {
                    item.setStatusMeeting(StatusMeeting.BUOI_NGHI);
                    listUp.add(item);
                }
            }
        });
        List<Meeting> savedMeetings = teMeetingRepository.saveAll(listUp);
        if (savedMeetings.size() >= 1) {
            CompletableFuture<Void> sendNotificationFuture = CompletableFuture.runAsync(() -> {
                messagingTemplate.convertAndSend("/portal-projects/update-meeting", "Do giảng viên không điểm danh buổi học hôm nay " +
                        "nên buổi học đã được tự động chuyển thành buổi nghỉ !");
            });
            //sendNotificationFuture.thenRun(this::playCustomSound);
        }
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void runJobUpdateStatus() {
        updateStatusMeetingJob();
    }

    private void playCustomSound() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SOUND);
            if (inputStream != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
                AudioFormat audioFormat = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
                sourceDataLine.open(audioFormat);
                sourceDataLine.start();
                int bufferSize = 128000;
                byte[] buffer = new byte[bufferSize];
                int bytesRead = 0;
                while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                    sourceDataLine.write(buffer, 0, bytesRead);
                }
                sourceDataLine.drain();
                sourceDataLine.close();
                audioInputStream.close();
            } else {
                System.err.println("Không tồn tại âm thanh");
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
