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

        Semester semester2 = new Semester();
        semester2.setName("SPRING 2023");
        semester2.setStartTime(1682874000000L);
        semester2.setEndTime(1682874000000L + 2678400000L);
        semester2.setId(semesterRepository.save(semester2).getId());

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
        team3.setId(teamRepository.save(team3).getId());
        team4.setId(teamRepository.save(team4).getId());

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
        //class 1 - team 1 ( 6-10)
        StudentClasses studentClasses6 = new StudentClasses();
        studentClasses6.setStudentId("ad51426d-7545-4f31-896d-cad398cac2c5");
        studentClasses6.setClassId(class1.getId());
        studentClasses6.setTeamId(team1.getId());
        studentClasses6.setEmail("bndph24003@fpt.edu.vn");
        studentClasses6.setRole(RoleTeam.LEADER);
        studentClasses6.setStatus(StatusTeam.ACTIVE);
        studentClasses6.setId(studentClassesRepository.save(studentClasses6).getId());

        StudentClasses studentClasses7 = new StudentClasses();
        studentClasses7.setStudentId("698b1fba-dffe-4a0d-884a-984b14d8dca7");
        studentClasses7.setClassId(class1.getId());
        studentClasses7.setTeamId(team1.getId());
        studentClasses7.setEmail("cndph24002@fpt.edu.vn");
        studentClasses7.setRole(RoleTeam.MEMBER);
        studentClasses7.setStatus(StatusTeam.ACTIVE);
        studentClasses7.setId(studentClassesRepository.save(studentClasses7).getId());

        StudentClasses studentClasses8 = new StudentClasses();
        studentClasses8.setStudentId("e015e9dc-b01a-4516-b9a9-70a09e0b5408");
        studentClasses8.setClassId(class1.getId());
        studentClasses8.setTeamId(team1.getId());
        studentClasses8.setEmail("dndph24001@fpt.edu.vn");
        studentClasses8.setRole(RoleTeam.MEMBER);
        studentClasses8.setStatus(StatusTeam.ACTIVE);
        studentClasses8.setId(studentClassesRepository.save(studentClasses8).getId());

        StudentClasses studentClasses9 = new StudentClasses();
        studentClasses9.setStudentId("732e7e24-6c0a-420f-96fc-31046410b057");
        studentClasses9.setClassId(class1.getId());
        studentClasses9.setTeamId(team1.getId());
        studentClasses9.setEmail("endph24004@fpt.edu.vn");
        studentClasses9.setRole(RoleTeam.MEMBER);
        studentClasses9.setStatus(StatusTeam.ACTIVE);
        studentClasses9.setId(studentClassesRepository.save(studentClasses9).getId());

        StudentClasses studentClasses10 = new StudentClasses();
        studentClasses10.setStudentId("69308521-e690-4b5f-8756-52bedb630951");
        studentClasses10.setClassId(class1.getId());
        studentClasses10.setTeamId(team1.getId());
        studentClasses10.setEmail("fndph24005@fpt.edu.vn");
        studentClasses10.setRole(RoleTeam.LEADER);
        studentClasses10.setStatus(StatusTeam.ACTIVE);
        studentClasses10.setId(studentClassesRepository.save(studentClasses10).getId());

        // class 1- team 2 (1-5)
        StudentClasses studentClasses1 = new StudentClasses();
        studentClasses1.setStudentId("cdc1629a-d9bd-4a5f-be12-8737ec26df8f");
        studentClasses1.setClassId(class1.getId());
        studentClasses1.setTeamId(team2.getId());
        studentClasses1.setEmail("hieundph25894@fpt.edu.vn");
        studentClasses1.setRole(RoleTeam.LEADER);
        studentClasses1.setStatus(StatusTeam.ACTIVE);
        studentClasses1.setId(studentClassesRepository.save(studentClasses1).getId());

        StudentClasses studentClasses2 = new StudentClasses();
        studentClasses2.setStudentId("874bffc6-38ce-467e-9458-47868e1e2f52");
        studentClasses2.setClassId(class1.getId());
        studentClasses2.setTeamId(team2.getId());
        studentClasses2.setEmail("thangncph26123@fpt.edu.vn");
        studentClasses2.setRole(RoleTeam.MEMBER);
        studentClasses2.setStatus(StatusTeam.ACTIVE);
        studentClasses2.setId(studentClassesRepository.save(studentClasses2).getId());

        StudentClasses studentClasses3 = new StudentClasses();
        studentClasses3.setStudentId("f5d1e3ab-e544-4d77-8bd3-830c904f0e93");
        studentClasses3.setClassId(class1.getId());
        studentClasses3.setTeamId(team2.getId());
        studentClasses3.setEmail("vanntph26001@fpt.edu.vn");
        studentClasses3.setRole(RoleTeam.MEMBER);
        studentClasses3.setStatus(StatusTeam.ACTIVE);
        studentClasses3.setId(studentClassesRepository.save(studentClasses3).getId());

        StudentClasses studentClasses4 = new StudentClasses();
        studentClasses4.setStudentId("aa47cd2b-ac99-4277-905b-ac2bb59886e9");
        studentClasses4.setClassId(class1.getId());
        studentClasses4.setTeamId(team2.getId());
        studentClasses4.setEmail("quynhncph26002@fpt.edu.vn");
        studentClasses4.setRole(RoleTeam.MEMBER);
        studentClasses4.setStatus(StatusTeam.ACTIVE);
        studentClasses4.setId(studentClassesRepository.save(studentClasses4).getId());

        StudentClasses studentClasses5 = new StudentClasses();
        studentClasses5.setStudentId("8559e6b3-8465-48e8-92a4-0f6147dc568d");
        studentClasses5.setClassId(class1.getId());
        studentClasses5.setTeamId(team2.getId());
        studentClasses5.setEmail("hanhlt27000@fpt.edu.vn");
        studentClasses5.setRole(RoleTeam.MEMBER);
        studentClasses5.setStatus(StatusTeam.ACTIVE);
        studentClasses5.setId(studentClassesRepository.save(studentClasses5).getId());

        // class 1- team 3 (11-15)
        StudentClasses studentClasses11 = new StudentClasses();
        studentClasses11.setStudentId("01fefe14-dbf8-4275-99b3-04b4bd96a5bc");
        studentClasses11.setClassId(class1.getId());
        studentClasses11.setTeamId(team3.getId());
        studentClasses11.setEmail("gndph24006@fpt.edu.vn");
        studentClasses11.setRole(RoleTeam.LEADER);
        studentClasses11.setStatus(StatusTeam.ACTIVE);
        studentClasses11.setId(studentClassesRepository.save(studentClasses11).getId());

        StudentClasses studentClasses12 = new StudentClasses();
        studentClasses12.setStudentId("0af2514a-ef53-48c5-945a-65248c890c7f");
        studentClasses12.setClassId(class1.getId());
        studentClasses12.setTeamId(team3.getId());
        studentClasses12.setEmail("hndph24007@fpt.edu.vn");
        studentClasses12.setRole(RoleTeam.MEMBER);
        studentClasses12.setStatus(StatusTeam.ACTIVE);
        studentClasses12.setId(studentClassesRepository.save(studentClasses12).getId());

        StudentClasses studentClasses13 = new StudentClasses();
        studentClasses13.setStudentId("941359a8-c58f-4355-ac0d-673cc78f0b32");
        studentClasses13.setClassId(class1.getId());
        studentClasses13.setTeamId(team3.getId());
        studentClasses13.setEmail("kndph24008@fpt.edu.vn");
        studentClasses13.setRole(RoleTeam.MEMBER);
        studentClasses13.setStatus(StatusTeam.ACTIVE);
        studentClasses13.setId(studentClassesRepository.save(studentClasses13).getId());

        StudentClasses studentClasses14 = new StudentClasses();
        studentClasses14.setStudentId("195d63ae-9de2-447d-9f62-54488070dd48");
        studentClasses14.setClassId(class1.getId());
        studentClasses14.setTeamId(team3.getId());
        studentClasses14.setEmail("jndph24009@fpt.edu.vn");
        studentClasses14.setRole(RoleTeam.MEMBER);
        studentClasses14.setStatus(StatusTeam.ACTIVE);
        studentClasses14.setId(studentClassesRepository.save(studentClasses14).getId());

        StudentClasses studentClasses15 = new StudentClasses();
        studentClasses15.setStudentId("7749a5e9-b674-4aca-9e2d-cd36535de3fa");
        studentClasses15.setClassId(class1.getId());
        studentClasses15.setTeamId(team3.getId());
        studentClasses15.setEmail("lndph24010@fpt.edu.vn");
        studentClasses15.setRole(RoleTeam.MEMBER);
        studentClasses15.setStatus(StatusTeam.ACTIVE);
        studentClasses15.setId(studentClassesRepository.save(studentClasses15).getId());

    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }

}