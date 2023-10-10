package com.labreportapp.labreport.core.teacher.cronjob;

import com.labreportapp.labreport.core.teacher.repository.TeMeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author hieundph25894
 */
@Component
public class TeCronJob {

    public static final String SOUND = "static/sounds/sound1.wav";

    @Autowired
    private TeMeetingRepository teMeetingRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void updateStatusMeetingJob() {
        int countUpdateMeeting = teMeetingRepository.updateStatusMeetingRest();
        if (countUpdateMeeting >= 1) {
            messagingTemplate.convertAndSend("/lesson/messageBE", "Do bạn không điểm danh buổi học " +
                    "nên các buổi học không điểm danh đã được chuyển thành buổi nghỉ !");
            // playCustomSound();
        } else {
            messagingTemplate.convertAndSend("/lesson/messageBE", "FAILD DIEM DANNh !");
        }
        // playCustomSound();
    }

    @Scheduled(cron = "15 9 * * * *")
    public void runJobPeriod1() {
        updateStatusMeetingJob();
    }

    @Scheduled(cron = "25 11 * * * *")
    public void runJobPeriod2() {
        updateStatusMeetingJob();
    }

    @Scheduled(cron = "0 14 * * * *")
    public void runJobPeriod3() {
        updateStatusMeetingJob();
    }

    @Scheduled(cron = "10 16 * * * *")
    public void runJobPeriod4() {
        updateStatusMeetingJob();
    }

    @Scheduled(cron = "20 18 * * * *")
    public void runJobPeriod5() {
        updateStatusMeetingJob();
    }

    @Scheduled(cron = "30 20 * * * *")
    public void runJobPeriod6() {
        updateStatusMeetingJob();
    }

    @Scheduled(cron = "40 22 * * * *")
    public void runJobPeriod7() {
        updateStatusMeetingJob();
    }

    @Scheduled(cron = "50 0 * * * *")
    public void runJobPeriod8() {
        updateStatusMeetingJob();
    }

    @Scheduled(cron = "0 3 * * * *")
    public void runJobPeriod9() {
        updateStatusMeetingJob();
    }

    @Scheduled(cron = "10 5 * * * *")
    public void runJobPeriod10() {
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
