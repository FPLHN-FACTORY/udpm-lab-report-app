package com.labreportapptool;

import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Class;
import com.labreportapp.entity.Semester;
import com.labreportapp.infrastructure.constant.ClassPeriod;
import com.labreportapp.infrastructure.constant.Level;
import com.labreportapp.repository.ActivityRepository;
import com.labreportapp.repository.AttendanceRepository;
import com.labreportapp.repository.ClassRepository;
import com.labreportapp.repository.HomeWorkRepository;
import com.labreportapp.repository.MeetingRepository;
import com.labreportapp.repository.NoteRepository;
import com.labreportapp.repository.SemesterRepository;
import com.labreportapp.repository.StudentClassesRepository;
import com.labreportapp.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

/**
 * @author thangncph26123
 */

@SpringBootApplication
@EnableJpaRepositories(
        basePackages = "com.labreportapp.repository"
)
public class DBGenerator implements CommandLineRunner {

    private final boolean IS_RELEASE = false;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private HomeWorkRepository homeWorkRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private StudentClassesRepository studentClassesRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void run(String... args) throws Exception {
        Semester semester = new Semester();
        semester.setName("FALL 2023");
        semester.setStartTime(new Date().getTime());
        semester.setEndTime(new Date().getTime() + 2678400000L);
        semester.setId(semesterRepository.save(semester).getId());

        Activity activity1 = new Activity();
        activity1.setName("Xây dựng cho sinh viên quy trình làm việc với dự án, thực hành làm việc với website");
        activity1.setStartTime(new Date().getTime() + 10000);
        activity1.setEndTime(new Date().getTime() + 10000 + 2678400000L);
        activity1.setSemesterId(semester.getId());
        activity1.setLevel(Level.LEVEL_1);
        activity1.setId(activityRepository.save(activity1).getId());

        Activity activity2 = new Activity();
        activity2.setName("Xây dựng nhóm đồ án làm trước đồ án tốt nghiệp");
        activity2.setStartTime(new Date().getTime() + 10000);
        activity2.setEndTime(new Date().getTime() + 10000 + 2678400000L);
        activity2.setSemesterId(semester.getId());
        activity2.setLevel(Level.LEVEL_2);
        activity2.setId(activityRepository.save(activity2).getId());

        Class class1 = new Class();
        class1.setCode("J6_NGUYENVV4_123");
        class1.setName("Lớp Java 6 thầy Nguyên");
        class1.setClassPeriod(ClassPeriod.CA_3);
        class1.setStartTime(new Date().getTime() + 50000);
        class1.setClassSize(0);
        class1.setPassword("123456");
        class1.setActivityId(activity2.getId());
        class1.setTeacherId("6f0e60a6-a3a8-45d3-b6e6-d7632eb64c1a");
        class1.setDescriptions("Lớp làm trước đồ án tốt nghiệp bán hàng");
        class1.setId(classRepository.save(class1).getId());

        Class class2 = new Class();
        class2.setCode("J3_HANGNT169_123");
        class2.setName("Lớp Java 3 cô Hằng");
        class2.setClassPeriod(ClassPeriod.CA_5);
        class2.setStartTime(new Date().getTime() + 50000);
        class2.setClassSize(0);
        class2.setPassword("123456");
        class2.setActivityId(activity1.getId());
        class2.setTeacherId("99b84d22-2edb-4ede-a5c4-ec78f4791fee");
        class2.setDescriptions("Lớp làm trước dự án 1 bán hàng");
        class2.setId(classRepository.save(class2).getId());
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }

}