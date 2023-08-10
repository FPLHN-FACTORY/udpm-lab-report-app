package com.labreportapptool;

import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Class;
import com.labreportapp.entity.Semester;
import com.labreportapp.entity.StudentClasses;
import com.labreportapp.entity.Team;
import com.labreportapp.infrastructure.constant.ClassPeriod;
import com.labreportapp.infrastructure.constant.Level;
import com.labreportapp.infrastructure.constant.RoleTeam;
import com.labreportapp.infrastructure.constant.StatusTeam;
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

//Team - class 1
        Team team1 = new Team();
        team1.setCode("TC1_1");
        team1.setName("Bees Shoess");
        team1.setSubjectName("Website bán giày Bee Shoes");
        team1.setClassId(class1.getId());
        team1.setId(teamRepository.save(team1).getId());

        Team team2 = new Team();
        team2.setCode("TC1_2");
        team2.setName("Bee Fly");
        team2.setSubjectName("Website camera fly");
        team2.setClassId(class1.getId());
        team2.setId(teamRepository.save(team2).getId());

        Team team3 = new Team();
        team3.setCode("TC1_3");
        team3.setName("Top one poly");
        team3.setSubjectName("Website bán quần áo Bee Poly");
        team3.setClassId(class1.getId());
        team3.setId(teamRepository.save(team3).getId());

        Team team4 = new Team();
        team4.setCode("TC1_4");
        team4.setName("Base Poly");
        team4.setSubjectName("Website bán nước hoa Base Poly");
        team3.setClassId(class1.getId());
        team3.setId(teamRepository.save(team3).getId());team4.setId(teamRepository.save(team4).getId());

        //Team - class 2
        Team team5 = new Team();
        team5.setCode("TC2_1");
        team5.setName("Hello Poly");
        team5.setSubjectName("Website bán giày Bee Shoes");
        team5.setClassId(class2.getId());
        team5.setId(teamRepository.save(team5).getId());

        Team team6 = new Team();
        team6.setCode("TC2_2");
        team6.setName("Hi Poly");
        team6.setSubjectName("Website camera HIPOLY");
        team6.setClassId(class2.getId());
        team6.setId(teamRepository.save(team6).getId());

        Team team7 = new Team();
        team7.setCode("TC2_3");
        team7.setName("ONE SHOES");
        team7.setSubjectName("Website bán áo ONESH Poly");
        team7.setClassId(class1.getId());
        team7.setId(teamRepository.save(team7).getId());

        Team team8 = new Team();
        team8.setCode("TC2_4");
        team8.setName("Case Happy Poly");
        team8.setSubjectName("Website bán quần hoa CHPPoly");
        team8.setClassId(class2.getId());
        team8.setId(teamRepository.save(team8).getId());

// student_ class
        StudentClasses studentClasses1 = new StudentClasses();
        studentClasses1.setStudentId("cdc1629a-d9bd-4a5f-be12-8737ec26df8f");
        studentClasses1.setClassId(class1.getId());
        studentClasses1.setTeamId(team2.getId());
        studentClasses1.setEmail("hieundph25894@fpt.edu.vn");
        studentClasses1.setRole(RoleTeam.LEADER);
        studentClasses1.setStatus(StatusTeam.ACTIVE);

    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }

}