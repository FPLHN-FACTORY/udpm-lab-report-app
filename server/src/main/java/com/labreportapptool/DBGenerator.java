package com.labreportapptool;

import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Class;
import com.labreportapp.entity.HomeWork;
import com.labreportapp.entity.Meeting;
import com.labreportapp.entity.Note;
import com.labreportapp.entity.Point;
import com.labreportapp.entity.Post;
import com.labreportapp.entity.Semester;
import com.labreportapp.entity.StudentClasses;
import com.labreportapp.entity.Team;
import com.labreportapp.infrastructure.constant.ClassPeriod;
import com.labreportapp.infrastructure.constant.Level;
import com.labreportapp.infrastructure.constant.MeetingPeriod;
import com.labreportapp.infrastructure.constant.RoleTeam;
import com.labreportapp.infrastructure.constant.StatusTeam;
import com.labreportapp.infrastructure.constant.TypeMeeting;
import com.labreportapp.repository.ActivityRepository;
import com.labreportapp.repository.AttendanceRepository;
import com.labreportapp.repository.ClassRepository;
import com.labreportapp.repository.HomeWorkRepository;
import com.labreportapp.repository.MeetingRepository;
import com.labreportapp.repository.NoteRepository;
import com.labreportapp.repository.PointRepository;
import com.labreportapp.repository.PostRepository;
import com.labreportapp.repository.SemesterRepository;
import com.labreportapp.repository.StudentClassesRepository;
import com.labreportapp.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PointRepository pointRepository;

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
        activity1.setLevel(Level.LEVEL_3);
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
        class1.setClassPeriod(ClassPeriod.CA_3);
        class1.setStartTime(new Date().getTime() + 50000);
        class1.setClassSize(19);
        class1.setPassword("123456");
        class1.setActivityId(activity1.getId());
        class1.setTeacherId("6f0e60a6-a3a8-45d3-b6e6-d7632eb64c1a");
        class1.setDescriptions("Lớp làm trước đồ án tốt nghiệp bán hàng");
        class1.setId(classRepository.save(class1).getId());

        Class class3 = new Class();
        class3.setCode("J5_NGUYENVV4_001");
        class3.setClassPeriod(ClassPeriod.CA_5);
        class3.setStartTime(new Date().getTime() + 50000);
        class3.setClassSize(0);
        class3.setPassword("000000");
        class3.setActivityId(activity2.getId());
        class3.setTeacherId("6f0e60a6-a3a8-45d3-b6e6-d7632eb64c1a");
        class3.setDescriptions("Lớp làm đồ án tốt nghiệp web bán hàng");
        class3.setId(classRepository.save(class3).getId());

        Class class2 = new Class();
        class2.setCode("J3_HANGNT169_123");
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
        team4.setClassId(class1.getId());
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
        team7.setClassId(class2.getId());
        team7.setId(teamRepository.save(team7).getId());

        Team team8 = new Team();
        team8.setCode("TC2_4");
        team8.setName("Case Happy Poly");
        team8.setSubjectName("Website bán quần hoa CHPPoly");
        team8.setClassId(class2.getId());
        team8.setId(teamRepository.save(team8).getId());

// student_ class
        // class 1- team 2 (1-5)
        StudentClasses studentClasses1 = new StudentClasses();
        studentClasses1.setStudentId("cdc1629a-d9bd-4a5f-be12-8737ec26df8f");
        studentClasses1.setClassId(class1.getId());
        studentClasses1.setTeamId(team2.getId());
        studentClasses1.setEmail("hieundph25894@fpt.edu.vn");
        studentClasses1.setRole(RoleTeam.MEMBER);
        studentClasses1.setStatus(StatusTeam.ACTIVE);
        studentClasses1.setId(studentClassesRepository.save(studentClasses1).getId());

        StudentClasses studentClasses2 = new StudentClasses();
        studentClasses2.setStudentId("874bffc6-38ce-467e-9458-47868e1e2f52");
        studentClasses2.setClassId(class1.getId());
        studentClasses2.setTeamId(team2.getId());
        studentClasses2.setEmail("thangncph26123@fpt.edu.vn");
        studentClasses2.setRole(RoleTeam.LEADER);
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
        studentClasses10.setRole(RoleTeam.MEMBER);
        studentClasses10.setStatus(StatusTeam.ACTIVE);
        studentClasses10.setId(studentClassesRepository.save(studentClasses10).getId());
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

        StudentClasses studentClasses16 = new StudentClasses();
        studentClasses16.setStudentId("5000cd3b-ad08-4abe-9b71-c85c98aa07ed");
        studentClasses16.setClassId(class1.getId());
        studentClasses16.setTeamId(null);
        studentClasses16.setEmail("undph24012@fpt.edu.vn");
        studentClasses16.setRole(RoleTeam.MEMBER);
        studentClasses16.setStatus(StatusTeam.ACTIVE);
        studentClasses16.setId(studentClassesRepository.save(studentClasses16).getId());

        StudentClasses studentClasses17 = new StudentClasses();
        studentClasses17.setStudentId("dac59af0-6bdb-4b26-ad8a-b5effa44875d");
        studentClasses17.setClassId(class1.getId());
        studentClasses17.setTeamId(null);
        studentClasses17.setEmail("endph24013@fpt.edu.vn");
        studentClasses17.setRole(RoleTeam.MEMBER);
        studentClasses17.setStatus(StatusTeam.ACTIVE);
        studentClasses17.setId(studentClassesRepository.save(studentClasses17).getId());

        StudentClasses studentClasses18 = new StudentClasses();
        studentClasses18.setStudentId("ad2a384f-f20f-430d-a378-e856018f3338");
        studentClasses18.setClassId(class1.getId());
        studentClasses18.setTeamId(null);
        studentClasses18.setEmail("rndph24014@fpt.edu.vn");
        studentClasses18.setRole(RoleTeam.MEMBER);
        studentClasses18.setStatus(StatusTeam.ACTIVE);
        studentClasses18.setId(studentClassesRepository.save(studentClasses18).getId());

        StudentClasses studentClasses19 = new StudentClasses();
        studentClasses19.setStudentId("c9c3ecbc-4dc1-46a3-9585-f3ee7550a97c");
        studentClasses19.setClassId(class1.getId());
        studentClasses19.setTeamId(null);
        studentClasses19.setEmail("tndph24015@fpt.edu.vn");
        studentClasses19.setRole(RoleTeam.MEMBER);
        studentClasses19.setStatus(StatusTeam.ACTIVE);
        studentClasses19.setId(studentClassesRepository.save(studentClasses19).getId());

        // class 2 - team 1
        StudentClasses studentClasses20 = new StudentClasses();
        studentClasses20.setStudentId("6178966a-c08b-45f6-98aa-35b8ac243ede");
        studentClasses20.setClassId(class2.getId());
        studentClasses20.setTeamId(team1.getId());
        studentClasses20.setEmail("qndph24011@fpt.edu.vn");
        studentClasses20.setRole(RoleTeam.MEMBER);
        studentClasses20.setStatus(StatusTeam.ACTIVE);
        studentClasses20.setId(studentClassesRepository.save(studentClasses20).getId());

        // class 3 - team 1
        StudentClasses studentClasses21 = new StudentClasses();
        studentClasses21.setStudentId("de60e713-56cc-4964-ac6c-f58dcee3dcab");
        studentClasses21.setClassId(class3.getId());
        studentClasses21.setTeamId(team1.getId());
        studentClasses21.setEmail("hieundph25894@fpt.edu.vn");
        studentClasses21.setRole(RoleTeam.LEADER);
        studentClasses21.setStatus(StatusTeam.ACTIVE);
        studentClasses21.setId(studentClassesRepository.save(studentClasses21).getId());

// Meeting
        Meeting meeting1 = new Meeting();
        meeting1.setName("Buổi 1 buổi đầu làm quen giới thiệu bản thân");
        meeting1.setMeetingDate(new Date().getTime() - 86400000);
        meeting1.setMeetingPeriod(MeetingPeriod.CA_1);
        meeting1.setDescriptions("Học tập và làm theo tấm gương đạo đức HỒ CHÍ MINH");
        meeting1.setClassId(class1.getId());
        meeting1.setTypeMeeting(TypeMeeting.ONLINE);
        meeting1.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting1.setId(meetingRepository.save(meeting1).getId());

        Meeting meeting2 = new Meeting();
        meeting2.setName("Buổi 2");
        meeting2.setMeetingDate(new Date().getTime());
        meeting2.setMeetingPeriod(MeetingPeriod.CA_2);
        meeting2.setDescriptions("Yêu Tổ quốc, yêu đồng bào Học tập tốt, lao động tốt Đoàn kết tốt, kỷ luật tốt Giữ gìn vệ sinh thật tốt Khiêm tốn, thật thà, dũng cảm");
        meeting2.setClassId(class1.getId());
        meeting2.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting2.setAddress("");
        meeting2.setId(meetingRepository.save(meeting2).getId());

        Meeting meeting3 = new Meeting();
        meeting3.setName("Buổi 3");
        meeting3.setMeetingDate(new Date().getTime() + 3);
        meeting3.setMeetingPeriod(MeetingPeriod.CA_6);
        meeting3.setDescriptions("Câu chuyện về 5 điều Bác Hồ dạy cũng nhắc nhở chúng ta, không chỉ các cơ quan báo chí mà nhiều ngành, nhiều lĩnh vực khác hiện nay cũng đang đơn giản, dễ dãi trong dùng từ ");
        meeting3.setClassId(class1.getId());
        meeting3.setTypeMeeting(TypeMeeting.ONLINE);
        meeting3.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting3.setId(meetingRepository.save(meeting3).getId());

        Meeting meeting4 = new Meeting();
        meeting4.setName("Buổi 4");
        meeting4.setMeetingDate(new Date().getTime() + 4 * 86400000);
        meeting4.setMeetingPeriod(MeetingPeriod.CA_2);
        meeting4.setDescriptions("5 Điều Bác Hồ Dạy Thiếu niên, Nhi đồng");
        meeting4.setClassId(class1.getId());
        meeting4.setTypeMeeting(TypeMeeting.ONLINE);
        meeting4.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting4.setId(meetingRepository.save(meeting4).getId());

        Meeting meeting5 = new Meeting();
        meeting5.setName("Buổi 5");
        meeting5.setMeetingDate(new Date().getTime() + 5 * 86400000);
        meeting5.setMeetingPeriod(MeetingPeriod.CA_3);
        meeting5.setDescriptions("");
        meeting5.setClassId(class1.getId());
        meeting5.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting5.setAddress("");
        meeting5.setId(meetingRepository.save(meeting5).getId());

        Meeting meeting6 = new Meeting();
        meeting6.setName("Buổi 6");
        meeting6.setMeetingDate(new Date().getTime() + 6 * 86400000);
        meeting6.setMeetingPeriod(MeetingPeriod.CA_4);
        meeting6.setDescriptions("Xét từ góc độ chủ thể sáng tạo và phát triển (ai làm nên nó): là hệ thống quan điểm và học thuyết đó được sáng lập bởi C. Mác, Ph. Ăngghen và sự phát triển, vận dụng vào thực tiễn của V.I. Lênin");
        meeting6.setClassId(class1.getId());
        meeting6.setTypeMeeting(TypeMeeting.ONLINE);
        meeting6.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting6.setId(meetingRepository.save(meeting6).getId());

        Meeting meeting7 = new Meeting();
        meeting7.setName("Buổi 7  ");
        meeting7.setMeetingDate(new Date().getTime() + 7 * 86400000);
        meeting7.setMeetingPeriod(MeetingPeriod.CA_1);
        meeting7.setDescriptions("Xét từ góc độ cấu tạo (nó gồm có những cái gì): Chủ nghĩa Mác - Lênin có ba bộ phận lý luận cơ bản hợp thành");
        meeting7.setClassId(class1.getId());
        meeting7.setTypeMeeting(TypeMeeting.ONLINE);
        meeting7.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting7.setId(meetingRepository.save(meeting7).getId());

        Meeting meeting8 = new Meeting();
        meeting8.setName("Buổi 8");
        meeting8.setMeetingDate(new Date().getTime() + 8 * 86400000);
        meeting8.setMeetingPeriod(MeetingPeriod.CA_1);
        meeting8.setDescriptions("Triết học là bộ phận lý luận nghiên cứu những quy luật vận động, phát triển chung nhất của tự nhiên, xã hội và tư duy");
        meeting8.setClassId(class1.getId());
        meeting8.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting8.setAddress("Xưởng");
        meeting8.setId(meetingRepository.save(meeting8).getId());

        Meeting meeting9 = new Meeting();
        meeting9.setName("Buổi 9");
        meeting9.setMeetingDate(new Date().getTime() + 9 * 86400000);
        meeting9.setMeetingPeriod(MeetingPeriod.CA_1);
        meeting9.setDescriptions("Triết học là bộ phận lý luận nghiên cứu những quy luật vận động, phát triển chung nhất của tự nhiên, xã hội và tư duy");
        meeting9.setClassId(class1.getId());
        meeting9.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting9.setAddress("");
        meeting9.setId(meetingRepository.save(meeting9).getId());

        Meeting meeting10 = new Meeting();
        meeting10.setName("Buổi 10 ");
        meeting10.setMeetingDate(new Date().getTime() + 10 * 86400000);
        meeting10.setMeetingPeriod(MeetingPeriod.CA_1);
        meeting10.setDescriptions("Triết học là bộ phận lý luận nghiên cứu những quy luật vận động, phát triển chung nhất của tự nhiên, xã hội và tư duy");
        meeting10.setClassId(class1.getId());
        meeting10.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting10.setAddress("");
        meeting10.setId(meetingRepository.save(meeting10).getId());
// homework
        //class 1- team 2
        HomeWork homeWork1 = new HomeWork();
        homeWork1.setName("Bài tập về nhà buổi 1 team bee fly");
        homeWork1.setDescriptions("Tạo entity và mapping dữ liệu - BEE FLY");
        homeWork1.setMeetingId(meeting1.getId());
        homeWork1.setTeamId(team2.getId());
        homeWork1.setId(homeWorkRepository.save(homeWork1).getId());

        HomeWork homeWork2 = new HomeWork();
        homeWork2.setName("Bài tập về nhà buổi 2");
        homeWork2.setDescriptions("Tạo repository sử dụng spring jpa - BEE FLY");
        homeWork2.setMeetingId(meeting2.getId());
        homeWork2.setTeamId(team2.getId());
        homeWork2.setId(homeWorkRepository.save(homeWork2).getId());

        HomeWork homeWork3 = new HomeWork();
        homeWork3.setName("Bài tập về nhà buổi 3");
        homeWork3.setDescriptions("Tạo serice và service implements - BEE FLY");
        homeWork3.setMeetingId(meeting3.getId());
        homeWork3.setTeamId(team2.getId());
        homeWork3.setId(homeWorkRepository.save(homeWork3).getId());

        HomeWork homeWork4 = new HomeWork();
        homeWork4.setName("Bài tập về nhà buổi 4");
        homeWork4.setDescriptions("Tạo model chứa các request và responese - BEE FLY");
        homeWork4.setMeetingId(meeting4.getId());
        homeWork4.setTeamId(team2.getId());
        homeWork4.setId(homeWorkRepository.save(homeWork4).getId());

        HomeWork homeWork5 = new HomeWork();
        homeWork5.setName("Bài tập về nhà buổi 5");
        homeWork5.setDescriptions("Thêm config vào file application.properties - BEE FLY");
        homeWork5.setMeetingId(meeting5.getId());
        homeWork5.setTeamId(team2.getId());
        homeWork5.setId(homeWorkRepository.save(homeWork5).getId());

        HomeWork homeWork6 = new HomeWork();
        homeWork6.setName("Bài tập về nhà buổi 6");
        homeWork6.setDescriptions("Tạo file config message.properties chứa các key validations - BEE FLY");
        homeWork6.setMeetingId(meeting6.getId());
        homeWork6.setTeamId(team2.getId());
        homeWork6.setId(homeWorkRepository.save(homeWork6).getId());

        HomeWork homeWork7 = new HomeWork();
        homeWork7.setName("Bài tập về nhà buổi 7");
        homeWork7.setDescriptions("Thêm 1 số chức năng vào service - BEE FLY");
        homeWork7.setMeetingId(meeting7.getId());
        homeWork7.setTeamId(team2.getId());
        homeWork7.setId(homeWorkRepository.save(homeWork7).getId());

        HomeWork homeWork8 = new HomeWork();
        homeWork8.setName("Bài tập về nhà buổi 8");
        homeWork8.setDescriptions("Tạo package Controller sử dụng SPRINGBOOT - BEE FLY");
        homeWork8.setMeetingId(meeting8.getId());
        homeWork8.setTeamId(team2.getId());
        homeWork8.setId(homeWorkRepository.save(homeWork8).getId());

        HomeWork homeWork9 = new HomeWork();
        homeWork9.setName("Bài tập về nhà buổi 9");
        homeWork9.setDescriptions("Sử dụng @RestController API và Sercurity ADMIN - BEE FLY");
        homeWork9.setMeetingId(meeting9.getId());
        homeWork9.setTeamId(team2.getId());
        homeWork9.setId(homeWorkRepository.save(homeWork9).getId());
        //note -team 2

        Note note1 = new Note();
        note1.setName("Note buổi 1 team 2 Bee fly");
        note1.setMeetingId(meeting1.getId());
        note1.setTeamId(team2.getId());
        note1.setDescriptions("team 2");
        note1.setId(noteRepository.save(note1).getId());

        Note note3 = new Note();
        note3.setName("Note buổi 3 team 2");
        note3.setMeetingId(meeting3.getId());
        note3.setTeamId(team2.getId());
        note3.setDescriptions("Service ok");
        note3.setId(noteRepository.save(note3).getId());

        Note note4 = new Note();
        note4.setName("Note buổi 4 team 2");
        note4.setMeetingId(meeting4.getId());
        note4.setTeamId(team2.getId());
        note4.setDescriptions("Model view ok");
        note4.setId(noteRepository.save(note4).getId());

        Note note5 = new Note();
        note5.setName("Note buổi 5 team 2");
        note5.setMeetingId(meeting5.getId());
        note5.setTeamId(team2.getId());
        note5.setDescriptions("Config ok");
        note5.setId(noteRepository.save(note5).getId());

        Note note6 = new Note();
        note6.setName("Note buổi 6 team 2");
        note6.setMeetingId(meeting6.getId());
        note6.setTeamId(team2.getId());
        note6.setDescriptions("Message config ok");
        note6.setId(noteRepository.save(note6).getId());

        Note note7 = new Note();
        note7.setName("Note buổi 7 team 2");
        note7.setMeetingId(meeting7.getId());
        note7.setTeamId(team2.getId());
        note7.setDescriptions("Chức năng bee fly ok");
        note7.setId(noteRepository.save(note7).getId());

        Note note8 = new Note();
        note8.setName("Note buổi 1 team 2");
        note8.setMeetingId(meeting8.getId());
        note8.setTeamId(team2.getId());
        note8.setDescriptions("");
        note8.setId(noteRepository.save(note8).getId());
//post - class 1
        Post post1 = new Post();
        post1.setDescriptions("""
                🔥🔥🔥HOT HOT HOT🔥🔥🔥
                🔥🔥🔥Một sự kiện không thể bỏ lỡ dành cho các bạn sinh viên Java 3-4-5 & Dự Án Mẫu- Dự Án 1 - SEMINIAR TẤT TẦN TẬT CÁC CÁCH LÀM VIỆC GIỮA JAVA VỚI CSDL đang chờ đón các bạn đây:🔥🔥🔥
                                
                💥Nội dung chính của seminar bao gồm:
                ⁉️ HIỂU Lợi ích của việc kết nối với CSDL
                ⁉️ BIẾT Các cách kết nối với CSDL như là JDBC, Hibernate, JPA
                ⁉️ HIỂU Cơ chế của mỗi cách kết nối và cách hoạt động của từng loại
                                
                💥 Chắn chắn rằng SEMINIAR sẽ giúp các bạn có thể giúp các bạn hiểu rõ hơn về 3 khái niệm JDBC/Hibernate/JPA . Nó là nền tảng giúp các bạn sau học lên Java 4,5,6 một cách dễ dàng hơn ✅
                🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡
                🕘 Thời gian: 20h30- 22h30
                🗓 Ngày: 27-07-2023
                                
                🧡🧡🧡Giảng viên tham gia:🧡🧡🧡
                🥰 GV1: Nguyễn Thúy Hằng
                🥰 GV2: Vũ Văn Nguyên
                🥰 GV3: Nguyễn Hoàng Tiến
                🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡
                                
                👋👋Hãy nhanh tay đăng ký tham gia sự kiện
                LINK ĐĂNG KÝ: https://forms.gle/qER6gWqWfGDrrDoP9
                """);
        post1.setTeacherId(class1.getTeacherId());
        post1.setClassId(class1.getId());
        post1.setCreatedDate(new Date().getTime() - 3 * 86400000);
        post1.setId(postRepository.save(post1).getId());

        Post post2 = new Post();
        post2.setDescriptions("Hôm nay được nghỉ nhé mọi người, quẫy đii 2 !");
        post2.setTeacherId(class1.getTeacherId());
        post2.setClassId(class1.getId());
        post2.setCreatedDate(new Date().getTime() - 2 * 86400020);
        post2.setId(postRepository.save(post2).getId());

        Post post3 = new Post();
        post3.setDescriptions("Mai đi học kiểm tra nhé, nhắc toàn thể các bạn 3 !");
        post3.setTeacherId(class1.getTeacherId());
        post3.setClassId(class1.getId());
        post3.setCreatedDate(new Date().getTime() - 1 * 86409000);
        post3.setId(postRepository.save(post3).getId());

        Post post4 = new Post();
        post4.setDescriptions("Giá trị đức tính “Cần, Kiệm, Liêm, Chính” trong tư tưởng Hồ Chí Minh về xây dựng đạo đức cách mạng của đội ngũ cán bộ, đảng viên hiện nay !");
        post4.setTeacherId(class1.getTeacherId());
        post4.setClassId(class1.getId());
        post4.setCreatedDate(new Date().getTime());
        post4.setId(postRepository.save(post4).getId());

        Post post5 = new Post();
        post5.setDescriptions("Tất cả sinh viên hôm nay học offline trên xưởng nhé, lưu ý giúp tôi 5 !");
        post5.setTeacherId(class1.getTeacherId());
        post5.setClassId(class1.getId());
        post5.setCreatedDate(new Date().getTime() - 1 * 86400000);
        post5.setId(postRepository.save(post5).getId());

        Post post6 = new Post();
        post6.setDescriptions("Tất cả sinh viên hôm nay học offline trên xưởng nhé, lưu ý giúp tôi 6 !");
        post6.setTeacherId(class1.getTeacherId());
        post6.setClassId(class1.getId());
        post6.setCreatedDate(new Date().getTime() - 2 * 86400000);
        post6.setId(postRepository.save(post6).getId());

        Post post7 = new Post();
        post7.setDescriptions("<p><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;\"><em>Để</em></span><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"> dựng nước và giữ nước tồn tại trong suốt lịch sử của dân tộc. Đồng thời, trong quá trình dựng nước, giữ nước,<span>&nbsp;</span></span><em style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">tinh thần đoàn kết và ý thức dân chủ</em><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>cũng xuất hiện. T</span><em style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">inh thần đoàn kết và ý thức dân chủ</em><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>làm cho mối quan hệ Cá nhân - Gia đình - Làng - Nước ngày càng trở nên bền chặt và nương tựa vào nhau để sinh tồn và phát triển. Giá trị truyền thống của người Việt là<span>&nbsp;</span></span><em style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">dũng cảm, cần cù, dẻo dai</em><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>trong lao động sản xuất, chiến đấu để sinh tồn và phát triển trước thiên nhiên và kẻ thù xâm lược. Nhưng đồng thời, trong quá trình đó, dân tộc Việt Nam cũng tiếp nhận những giá trị văn hóa, văn minh của nhân loại. Người Việt có tư duy mở và mềm dẻo khiến họ dễ dàng tiếp nhận những tư tưởng bên ngoài. Trong khi là đảng viên<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/%C4%90%E1%BA%A3ng_X%C3%A3_h%E1%BB%99i_(Ph%C3%A1p)\" title=\"Đảng Xã hội (Pháp)\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">Đảng xã hội Pháp</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\">, Hồ Chí Minh đã tiếp nhận<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/Ch%E1%BB%A7_ngh%C4%A9a_Lenin\" title=\"Chủ nghĩa Lenin\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">chủ nghĩa Lenin</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>với mục tiêu giải phóng dân tộc trong lúc nhiều đồng chí Pháp của ông chọn con đường<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/D%C3%A2n_ch%E1%BB%A7_x%C3%A3_h%E1%BB%99i\" title=\"Dân chủ xã hội\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">dân chủ xã hội</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>với chủ trương cải cách xã hội nhưng chấp nhận nền dân chủ.<span>&nbsp;</span></span><em style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">Chủ nghĩa yêu nước là điểm xuất phát, là cơ sở để Hồ Chí Minh tiếp nhận<span>&nbsp;</span><a href=\"https://vi.wikipedia.org/wiki/Ch%E1%BB%A7_ngh%C4%A9a_Marx_-_Lenin\" class=\"mw-redirect\" title=\"Chủ nghĩa Marx - Lenin\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none; overflow-wrap: break-word;\">chủ nghĩa Marx - Lenin</a>;</em><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>là một trong những nguồn gốc chủ yếu hình thành tư tưởng Hồ Chí Minh.</span><br></p>");
        post7.setTeacherId(class1.getTeacherId());
        post7.setClassId(class1.getId());
        post7.setCreatedDate(new Date().getTime());
        post7.setId(postRepository.save(post7).getId());

        Post post8 = new Post();
        post8.setDescriptions("<p><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\">Trong suốt quá trình thực dân Pháp cai trị, từ giữa thế kỷ XIX đến đầu thế kỷ XX, đã có nhiều cuộc khởi nghĩa nổi dậy chống<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/Th%E1%BB%B1c_d%C3%A2n_Ph%C3%A1p\" class=\"mw-redirect\" title=\"Thực dân Pháp\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">thực dân Pháp</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\">. Các cuộc khởi nghĩa vũ trang dưới khẩu hiệu \"Cần vương\" do các văn thân, sĩ phu lãnh đạo cuối cùng cũng thất bại. Các cuộc khởi nghĩa vũ trang này mang đậm tinh thần yêu nước và đầy dũng khí trước quân thù, nhưng đều dưới sự dẫn dắt của các tư tưởng phong kiến và tư sản và đều thất bại. Cách mạng Việt Nam lâm vào cuộc khủng khoảng sâu sắc về đường lối cứu nước.</span><sup id=\"cite_ref-9\" class=\"reference\" style=\"line-height: 1em; font-size: 11.2px; white-space: nowrap; unicode-bidi: isolate; font-weight: 400; font-style: normal; color: rgb(32, 33, 34); font-family: sans-serif; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\"><a href=\"https://vi.wikipedia.org/wiki/T%C6%B0_t%C6%B0%E1%BB%9Fng_H%E1%BB%93_Ch%C3%AD_Minh1cite_note-9\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none; overflow-wrap: break-word; white-space: nowrap;\">[9]</a></sup><br></p>");
        post8.setTeacherId(class1.getTeacherId());
        post8.setClassId(class1.getId());
        post8.setCreatedDate(new Date().getTime() +  1 * 86400000);
        post8.setId(postRepository.save(post8).getId());

        Post post9 = new Post();
        post9.setDescriptions("<p><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\">Tư tưởng Hồ Chí Minh chỉ chính thức đưa vào Cương lĩnh của Đảng Cộng sản Việt Nam năm 1991, sau khi công cuộc Đổi mới phát động, chấp thuận phân hóa giai cấp, nhiều lý luận của chủ nghĩa Mác - Lênin không có tính khả thi trong cơ chế thị trường phải gác lại như đạo đức xã hội chủ nghĩa, xây dựng con người xã hội chủ nghĩa, làm theo năng lực hưởng theo lao động trên toàn xã hội... (</span><a href=\"https://vi.wikipedia.org/wiki/Ch%E1%BB%A7_ngh%C4%A9a_c%E1%BB%99ng_s%E1%BA%A3n\" title=\"Chủ nghĩa cộng sản\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">chủ nghĩa cộng sản</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>đặt ra mục tiêu cuối cùng là xóa bỏ giai cấp, bóc lột, xóa bỏ giàu - nghèo, làm theo năng lực hưởng theo nhu cầu, công hữu trên nền tảng dân chủ, xóa bỏ giáo điều tôn giáo được xem là mị dân, xóa bỏ nhà nước đi đến dân chủ trực tiếp và bình đẳng, xóa bỏ các đường biên giới quốc gia, đưa các dân tộc đến cùng một lợi ích, xóa bỏ bất bình đẳng giữa các dân tộc trên phạm vi thế giới...). Các giáo trình của Việt Nam thường khai thác tư tưởng Hồ Chí Minh theo chiều hướng trên.</span><br></p>");
        post9.setTeacherId(class1.getTeacherId());
        post9.setClassId(class1.getId());
        post9.setCreatedDate(new Date().getTime() +  2 * 86400000);
        post9.setId(postRepository.save(post9).getId());

        Post post10 = new Post();
        post10.setDescriptions("<p><strong style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">Tư tưởng Hồ Chí Minh</strong><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>là một hệ thống quan điểm và<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/T%C6%B0_t%C6%B0%E1%BB%9Fng\" class=\"mw-redirect\" title=\"Tư tưởng\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">tư tưởng</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>của<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/H%E1%BB%93_Ch%C3%AD_Minh\" title=\"Hồ Chí Minh\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">Hồ Chí Minh</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>trong sự nghiệp cách mạng của ông được<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/%C4%90%E1%BA%A3ng_C%E1%BB%99ng_s%E1%BA%A3n_Vi%E1%BB%87t_Nam\" title=\"Đảng Cộng sản Việt Nam\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">Đảng Cộng sản Việt Nam</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>tổng kết, hệ thống hóa.&nbsp;</span><br></p>");
        post10.setTeacherId(class1.getTeacherId());
        post10.setClassId(class1.getId());
        post10.setCreatedDate(new Date().getTime() +  3 * 86400000);
        post10.setId(postRepository.save(post10).getId());

        Post post11 = new Post();
        post11.setDescriptions("""
                🔥🔥🔥HOT HOT HOT🔥🔥🔥
                🔥🔥🔥Một sự kiện không thể bỏ lỡ dành cho các bạn sinh viên Java 3-4-5 & Dự Án Mẫu- Dự Án 1 - SEMINIAR TẤT TẦN TẬT CÁC CÁCH LÀM VIỆC GIỮA JAVA VỚI CSDL đang chờ đón các bạn đây:🔥🔥🔥
                                
                💥Nội dung chính của seminar bao gồm:
                ⁉️ HIỂU Lợi ích của việc kết nối với CSDL
                ⁉️ BIẾT Các cách kết nối với CSDL như là JDBC, Hibernate, JPA
                ⁉️ HIỂU Cơ chế của mỗi cách kết nối và cách hoạt động của từng loại
                                
                💥 Chắn chắn rằng SEMINIAR sẽ giúp các bạn có thể giúp các bạn hiểu rõ hơn về 3 khái niệm JDBC/Hibernate/JPA . Nó là nền tảng giúp các bạn sau học lên Java 4,5,6 một cách dễ dàng hơn ✅
                🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡
                🕘 Thời gian: 20h30- 22h30
                🗓 Ngày: 27-07-2023
                                
                🧡🧡🧡Giảng viên tham gia:🧡🧡🧡
                🥰 GV1: Nguyễn Thúy Hằng
                🥰 GV2: Vũ Văn Nguyên
                🥰 GV3: Nguyễn Hoàng Tiến
                🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡
                                
                👋👋Hãy nhanh tay đăng ký tham gia sự kiện
                LINK ĐĂNG KÝ: https://forms.gle/qER6gWqWfGDrrDoP9
                """);
        post11.setTeacherId(class1.getTeacherId());
        post11.setClassId(class1.getId());
        post11.setCreatedDate(new Date().getTime() +  3 * 86400300);
        post11.setId(postRepository.save(post11).getId());

    // POINT - Class 1
        Point point1 = new Point();
        point1.setStudentId("cdc1629a-d9bd-4a5f-be12-8737ec26df8f");
        point1.setClassId(class1.getId());
        point1.setCheckPointPhase1(8.0);
        point1.setCheckPointPhase2(5.0);
        point1.setFinalPoint(6.5);
        point1.setId(pointRepository.save(point1).getId());

        Point point2 = new Point();
        point2.setStudentId("874bffc6-38ce-467e-9458-47868e1e2f52");
        point2.setClassId(class1.getId());
        point2.setCheckPointPhase1(8.0);
        point2.setCheckPointPhase2(6.0);
        point2.setFinalPoint(7.0);
        point2.setId(pointRepository.save(point2).getId());

        Point point3 = new Point();
        point3.setStudentId("f5d1e3ab-e544-4d77-8bd3-830c904f0e93");
        point3.setClassId(class1.getId());
        point3.setCheckPointPhase1(8.0);
        point3.setCheckPointPhase2(5.0);
        point3.setFinalPoint(9.0);
        point3.setId(pointRepository.save(point3).getId());

        Point point4 = new Point();
        point4.setStudentId("aa47cd2b-ac99-4277-905b-ac2bb59886e9");
        point4.setClassId(class1.getId());
        point4.setCheckPointPhase1(8.0);
        point4.setCheckPointPhase2(5.0);
        point4.setFinalPoint(9.0);
        point4.setId(pointRepository.save(point4).getId());

        Point point5 = new Point();
        point5.setStudentId("8559e6b3-8465-48e8-92a4-0f6147dc568d");
        point5.setClassId(class1.getId());
        point5.setCheckPointPhase1(8.0);
        point5.setCheckPointPhase2(5.0);
        point5.setFinalPoint(9.0);
        point5.setId(pointRepository.save(point5).getId());

        Point point6 = new Point();
        point6.setStudentId("ad51426d-7545-4f31-896d-cad398cac2c5");
        point6.setClassId(class1.getId());
        point6.setCheckPointPhase1(8.0);
        point6.setCheckPointPhase2(7.0);
        point6.setFinalPoint(8.0);
        point6.setId(pointRepository.save(point6).getId());

        Point point7 = new Point();
        point7.setStudentId("698b1fba-dffe-4a0d-884a-984b14d8dca7");
        point7.setClassId(class1.getId());
        point7.setCheckPointPhase1(5.0);
        point7.setCheckPointPhase2(5.0);
        point7.setFinalPoint(9.0);
        point7.setId(pointRepository.save(point7).getId());

        Point point8 = new Point();
        point8.setStudentId("e015e9dc-b01a-4516-b9a9-70a09e0b5408");
        point8.setClassId(class1.getId());
        point8.setCheckPointPhase1(8.0);
        point8.setCheckPointPhase2(5.0);
        point8.setFinalPoint(4.0);
        point8.setId(pointRepository.save(point8).getId());

        Point point9 = new Point();
        point9.setStudentId("732e7e24-6c0a-420f-96fc-31046410b057");
        point9.setClassId(class1.getId());
        point9.setCheckPointPhase1(6.0);
        point9.setCheckPointPhase2(6.0);
        point9.setFinalPoint(6.0);
        point9.setId(pointRepository.save(point9).getId());

        Point point10 = new Point();
        point10.setStudentId("69308521-e690-4b5f-8756-52bedb630951");
        point10.setClassId(class1.getId());
        point10.setCheckPointPhase1(8.0);
        point10.setCheckPointPhase2(2.0);
        point10.setFinalPoint(3.0);
        point10.setId(pointRepository.save(point10).getId());

        Point point11 = new Point();
        point11.setStudentId("01fefe14-dbf8-4275-99b3-04b4bd96a5bc");
        point11.setClassId(class1.getId());
        point11.setCheckPointPhase1(5.0);
        point11.setCheckPointPhase2(5.0);
        point11.setFinalPoint(5.0);
        point11.setId(pointRepository.save(point11).getId());

        Point point12 = new Point();
        point12.setStudentId("0af2514a-ef53-48c5-945a-65248c890c7f");
        point12.setClassId(class1.getId());
        point12.setCheckPointPhase1(7.0);
        point12.setCheckPointPhase2(7.0);
        point12.setFinalPoint(7.0);
        point12.setId(pointRepository.save(point12).getId());

        Point point13 = new Point();
        point13.setStudentId("941359a8-c58f-4355-ac0d-673cc78f0b32");
        point13.setClassId(class1.getId());
        point13.setCheckPointPhase1(5.0);
        point13.setCheckPointPhase2(5.0);
        point13.setFinalPoint(3.0);
        point13.setId(pointRepository.save(point13).getId());

        Point point14 = new Point();
        point14.setStudentId("195d63ae-9de2-447d-9f62-54488070dd48");
        point14.setClassId(class1.getId());
        point14.setCheckPointPhase1(8.0);
        point14.setCheckPointPhase2(10.0);
        point14.setFinalPoint(9.0);
        point14.setId(pointRepository.save(point14).getId());

        Point point15 = new Point();
        point15.setStudentId("7749a5e9-b674-4aca-9e2d-cd36535de3fa");
        point15.setClassId(class1.getId());
        point15.setCheckPointPhase1(8.0);
        point15.setCheckPointPhase2(10.0);
        point15.setFinalPoint(9.0);
        point15.setId(pointRepository.save(point15).getId());

        Point point16 = new Point();
        point16.setStudentId("5000cd3b-ad08-4abe-9b71-c85c98aa07ed");
        point16.setClassId(class1.getId());
        point16.setCheckPointPhase1(10.0);
        point16.setCheckPointPhase2(5.0);
        point16.setFinalPoint(9.0);
        point16.setId(pointRepository.save(point16).getId());

        Point point17 = new Point();
        point17.setStudentId("dac59af0-6bdb-4b26-ad8a-b5effa44875d");
        point17.setClassId(class1.getId());
        point17.setCheckPointPhase1(3.0);
        point17.setCheckPointPhase2(5.0);
        point17.setFinalPoint(7.0);
        point17.setId(pointRepository.save(point17).getId());

        Point point18 = new Point();
        point18.setStudentId("ad2a384f-f20f-430d-a378-e856018f3338");
        point18.setClassId(class1.getId());
        point18.setCheckPointPhase1(8.0);
        point18.setCheckPointPhase2(5.0);
        point18.setFinalPoint(10.0);
        point18.setId(pointRepository.save(point18).getId());

        Point point19 = new Point();
        point19.setStudentId("c9c3ecbc-4dc1-46a3-9585-f3ee7550a97c");
        point19.setClassId(class1.getId());
        point19.setCheckPointPhase1(8.0);
        point19.setCheckPointPhase2(5.0);
        point19.setFinalPoint(2.0);
        point19.setId(pointRepository.save(point19).getId());

    // POINT - Class 2 - teacher HangNT
        Point point20 = new Point();
        point20.setStudentId("6178966a-c08b-45f6-98aa-35b8ac243ede");
        point20.setClassId(class2.getId());
        point20.setCheckPointPhase1(8.0);
        point20.setCheckPointPhase2(5.0);
        point20.setFinalPoint(7.0);
        point20.setId(pointRepository.save(point20).getId());

    // POINT - class 3 - teacher NguyenVV
        Point point21 = new Point();
        point21.setStudentId("de60e713-56cc-4964-ac6c-f58dcee3dcab");
        point21.setClassId(class3.getId());
        point21.setCheckPointPhase1(8.0);
        point21.setCheckPointPhase2(8.0);
        point21.setFinalPoint(9.0);
        point21.setId(pointRepository.save(point21).getId());

    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }

}