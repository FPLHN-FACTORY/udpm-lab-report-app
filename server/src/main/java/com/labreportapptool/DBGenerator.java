package com.labreportapptool;

import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.Attendance;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.labreport.entity.FeedBack;
import com.labreportapp.labreport.entity.HomeWork;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.entity.MemberFactory;
import com.labreportapp.labreport.entity.MemberTeamFactory;
import com.labreportapp.labreport.entity.Note;
import com.labreportapp.labreport.entity.Point;
import com.labreportapp.labreport.entity.Post;
import com.labreportapp.labreport.entity.Report;
import com.labreportapp.labreport.entity.RoleFactory;
import com.labreportapp.labreport.entity.Semester;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.entity.Team;
import com.labreportapp.labreport.entity.TeamFactory;
import com.labreportapp.labreport.entity.TemplateReport;
import com.labreportapp.labreport.infrastructure.constant.AllowUseTrello;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.labreport.infrastructure.constant.StatusAttendance;
import com.labreportapp.labreport.infrastructure.constant.StatusClass;
import com.labreportapp.labreport.infrastructure.constant.StatusFeedBack;
import com.labreportapp.labreport.infrastructure.constant.StatusMeeting;
import com.labreportapp.labreport.infrastructure.constant.StatusMemberTeamFactory;
import com.labreportapp.labreport.infrastructure.constant.StatusStudentFeedBack;
import com.labreportapp.labreport.infrastructure.constant.StatusTeacherEdit;
import com.labreportapp.labreport.infrastructure.constant.StatusTeam;
import com.labreportapp.labreport.infrastructure.constant.TypeMeeting;
import com.labreportapp.labreport.repository.ActivityRepository;
import com.labreportapp.labreport.repository.AttendanceRepository;
import com.labreportapp.labreport.repository.ClassConfigurationRepository;
import com.labreportapp.labreport.repository.ClassRepository;
import com.labreportapp.labreport.repository.FeedBackRepository;
import com.labreportapp.labreport.repository.HomeWorkRepository;
import com.labreportapp.labreport.repository.LevelRepository;
import com.labreportapp.labreport.repository.MeetingPeriodRepository;
import com.labreportapp.labreport.repository.MeetingRepository;
import com.labreportapp.labreport.repository.MemberFactoryRepository;
import com.labreportapp.labreport.repository.MemberTeamFactoryRepository;
import com.labreportapp.labreport.repository.NoteRepository;
import com.labreportapp.labreport.repository.PointRepository;
import com.labreportapp.labreport.repository.PostRepository;
import com.labreportapp.labreport.repository.ReportRepository;
import com.labreportapp.labreport.repository.RoleFactoryRepository;
import com.labreportapp.labreport.repository.SemesterRepository;
import com.labreportapp.labreport.repository.StudentClassesRepository;
import com.labreportapp.labreport.repository.TeamFactoryRepository;
import com.labreportapp.labreport.repository.TeamRepository;
import com.labreportapp.labreport.repository.TemplateReportRepository;
import com.labreportapp.portalprojects.entity.Category;
import com.labreportapp.portalprojects.entity.GroupProject;
import com.labreportapp.portalprojects.entity.Label;
import com.labreportapp.portalprojects.entity.LabelProject;
import com.labreportapp.portalprojects.entity.LabelProjectTodo;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.entity.Period;
import com.labreportapp.portalprojects.entity.PeriodTodo;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.entity.ProjectCategory;
import com.labreportapp.portalprojects.entity.Todo;
import com.labreportapp.portalprojects.entity.TodoList;
import com.labreportapp.portalprojects.entity.TypeProject;
import com.labreportapp.portalprojects.infrastructure.constant.Constants;
import com.labreportapp.portalprojects.infrastructure.constant.PriorityLevel;
import com.labreportapp.portalprojects.infrastructure.constant.RoleMemberProject;
import com.labreportapp.portalprojects.infrastructure.constant.StatusPeriod;
import com.labreportapp.portalprojects.infrastructure.constant.StatusProject;
import com.labreportapp.portalprojects.infrastructure.constant.StatusTodo;
import com.labreportapp.portalprojects.infrastructure.constant.StatusWork;
import com.labreportapp.portalprojects.infrastructure.constant.TypeTodo;
import com.labreportapp.portalprojects.repository.AssignRepository;
import com.labreportapp.portalprojects.repository.CategoryRepository;
import com.labreportapp.portalprojects.repository.GroupProjectRepository;
import com.labreportapp.portalprojects.repository.LabelProjectRepository;
import com.labreportapp.portalprojects.repository.LabelProjectTodoRepository;
import com.labreportapp.portalprojects.repository.LabelRepository;
import com.labreportapp.portalprojects.repository.MemberProjectRepository;
import com.labreportapp.portalprojects.repository.PeriodRepository;
import com.labreportapp.portalprojects.repository.PeriodTodoRepository;
import com.labreportapp.portalprojects.repository.ProjectCategoryRepository;
import com.labreportapp.portalprojects.repository.ProjectRepository;
import com.labreportapp.portalprojects.repository.StakeholderProjectRepository;
import com.labreportapp.portalprojects.repository.TodoListRepository;
import com.labreportapp.portalprojects.repository.TodoRepository;
import com.labreportapp.portalprojects.repository.TypeProjectRepository;
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
        basePackages = {"com.labreportapp.labreport.repository",
                "com.labreportapp.portalprojects.repository"}
)
public class DBGenerator implements CommandLineRunner {

    private final boolean IS_RELEASE = false;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private FeedBackRepository feedBackRepository;

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

    @Autowired
    private ClassConfigurationRepository classConfigurationRepository;

    @Autowired
    private AssignRepository assignRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabelProjectTodoRepository labelTodoRepository;

    @Autowired
    private MemberProjectRepository memberProjectRepository;

    @Autowired
    private PeriodTodoRepository periodTodoRepository;

    @Autowired
    private PeriodRepository periodRepository;

    @Autowired
    private ProjectCategoryRepository projectCategoryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private LabelProjectRepository labelProjectRepository;

    @Autowired
    private StakeholderProjectRepository stakeholderProjectRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TemplateReportRepository templateReportRepository;

    @Autowired
    private MeetingPeriodRepository meetingPeriodRepository;

    @Autowired
    private RoleFactoryRepository roleFactoryRepository; // bảng mới thêm ngày 14/10/2023

    @Autowired
    private TeamFactoryRepository teamFactoryRepository;  // bảng mới thêm ngày 14/10/2023

    @Autowired
    private MemberFactoryRepository memberFactoryRepository; // bảng mới thêm ngày 14/10/2023

    @Autowired
    private MemberTeamFactoryRepository memberTeamFactoryRepository; // bảng mới thêm ngày 14/10/2023

    @Autowired
    private TypeProjectRepository typeProjectRepository;// portal project // bảng mới thêm ngày 14/10/2023

    @Autowired
    private GroupProjectRepository groupProjectRepository;// portal project // bảng mới thêm ngày 14/10/2023

    @Override
    public void run(String... args) throws Exception {

        Semester semester = new Semester();
        semester.setName("FALL 2023");
        semester.setStartTime(new Date().getTime());
        semester.setEndTime(new Date().getTime() + 2678400000L);
        semester.setStartTimeStudent(new Date().getTime());
        semester.setStatusFeedBack(StatusFeedBack.DA_FEEDBACK);
        semester.setEndTimeStudent(new Date().getTime() + 86400000L * 7);
        semester.setId(semesterRepository.save(semester).getId());

        Semester semester2 = new Semester();
        semester2.setName("SPRING 2023");
        semester2.setStartTime(1682874000000L);
        semester2.setStartTimeStudent(1682874000000L);
        semester2.setEndTime(1682874000000L + 2678400000L);
        semester2.setStatusFeedBack(StatusFeedBack.DA_FEEDBACK);
        semester2.setEndTimeStudent(1682874000000L + 86400000L * 7);
        semester2.setId(semesterRepository.save(semester2).getId());

        com.labreportapp.labreport.entity.Level level1 = new com.labreportapp.labreport.entity.Level();
        level1.setName("Level 1");
        level1.setId(levelRepository.save(level1).getId());

        com.labreportapp.labreport.entity.Level level2 = new com.labreportapp.labreport.entity.Level();
        level2.setName("Level 2");
        level2.setId(levelRepository.save(level2).getId());

        com.labreportapp.labreport.entity.Level level3 = new com.labreportapp.labreport.entity.Level();
        level3.setName("Level 3");
        level3.setId(levelRepository.save(level3).getId());

        Activity activity1 = new Activity();
        activity1.setCode("JAVA_WEB");
        activity1.setName("Xây dựng cho sinh viên quy trình làm việc với dự án, thực hành làm việc với website");
        activity1.setStartTime(new Date().getTime() + 10000);
        activity1.setEndTime(new Date().getTime() + 10000 + 2678400000L);
        activity1.setSemesterId(semester.getId());
        activity1.setAllowUseTrello(AllowUseTrello.CHO_PHEP);
        activity1.setLevelId(level3.getId());
        activity1.setDescriptions("https://docs.google.com/spreadsheets/d/1Gop11N-inh_I-TegG3OyGZpfKu4Ls1-qTY1IAMHSCY4/edit#gid=1799431034");
        activity1.setId(activityRepository.save(activity1).getId());

        Activity activity2 = new Activity();
        activity2.setCode("JAVA_SWING");
        activity2.setName("Xây dựng nhóm đồ án làm trước đồ án tốt nghiệp");
        activity2.setStartTime(new Date().getTime() + 10000);
        activity2.setEndTime(new Date().getTime() + 10000 + 2678400000L);
        activity2.setSemesterId(semester.getId());
        activity2.setLevelId(level2.getId());
        activity2.setAllowUseTrello(AllowUseTrello.KHONG_CHO_PHEP);
        activity2.setDescriptions("https://docs.google.com/spreadsheets/d/1Gop11N-inh_I-TegG3OyGZpfKu4Ls1-qTY1IAMHSCY4/edit#gid=1799431034");
        activity2.setId(activityRepository.save(activity2).getId());

        MeetingPeriod meetingPeriod1 = new MeetingPeriod();
        meetingPeriod1.setName("Ca 1");
        meetingPeriod1.setStartHour(7);
        meetingPeriod1.setStartMinute(15);
        meetingPeriod1.setEndHour(9);
        meetingPeriod1.setEndMinute(15);
        meetingPeriod1.setId(meetingPeriodRepository.save(meetingPeriod1).getId());

        MeetingPeriod meetingPeriod2 = new MeetingPeriod();
        meetingPeriod2.setName("Ca 2");
        meetingPeriod2.setStartHour(9);
        meetingPeriod2.setStartMinute(25);
        meetingPeriod2.setEndHour(11);
        meetingPeriod2.setEndMinute(25);
        meetingPeriod2.setId(meetingPeriodRepository.save(meetingPeriod2).getId());

        MeetingPeriod meetingPeriod3 = new MeetingPeriod();
        meetingPeriod3.setName("Ca 3");
        meetingPeriod3.setStartHour(12);
        meetingPeriod3.setStartMinute(0);
        meetingPeriod3.setEndHour(14);
        meetingPeriod3.setEndMinute(0);
        meetingPeriod3.setId(meetingPeriodRepository.save(meetingPeriod3).getId());

        MeetingPeriod meetingPeriod4 = new MeetingPeriod();
        meetingPeriod4.setName("Ca 4");
        meetingPeriod4.setStartHour(14);
        meetingPeriod4.setStartMinute(10);
        meetingPeriod4.setEndHour(16);
        meetingPeriod4.setEndMinute(10);
        meetingPeriod4.setId(meetingPeriodRepository.save(meetingPeriod4).getId());

        MeetingPeriod meetingPeriod5 = new MeetingPeriod();
        meetingPeriod5.setName("Ca 5");
        meetingPeriod5.setStartHour(16);
        meetingPeriod5.setStartMinute(20);
        meetingPeriod5.setEndHour(18);
        meetingPeriod5.setEndMinute(20);
        meetingPeriod5.setId(meetingPeriodRepository.save(meetingPeriod5).getId());

        MeetingPeriod meetingPeriod6 = new MeetingPeriod();
        meetingPeriod6.setName("Ca 6");
        meetingPeriod6.setStartHour(18);
        meetingPeriod6.setStartMinute(30);
        meetingPeriod6.setEndHour(20);
        meetingPeriod6.setEndMinute(30);
        meetingPeriod6.setId(meetingPeriodRepository.save(meetingPeriod6).getId());

        MeetingPeriod meetingPeriod7 = new MeetingPeriod();
        meetingPeriod7.setName("Ca 7");
        meetingPeriod7.setStartHour(20);
        meetingPeriod7.setStartMinute(40);
        meetingPeriod7.setEndHour(22);
        meetingPeriod7.setEndMinute(40);
        meetingPeriod7.setId(meetingPeriodRepository.save(meetingPeriod7).getId());

        Class class1 = new Class();
        class1.setCode("JAVA_WEB_1");
        class1.setClassPeriod(meetingPeriod3.getId());
        class1.setStartTime(new Date().getTime() + 50000);
        class1.setClassSize(19);
        class1.setPassword("123456");
        class1.setActivityId(activity1.getId());
        //class1.setTeacherId("FA2BAD81-93A5-4F02-B1B7-08DBB743DD7D".toLowerCase());
        class1.setTeacherId("71090c89-f618-41ae-2a8d-08dbb201efe8".toLowerCase());
        class1.setDescriptions("Lớp làm trước đồ án tốt nghiệp bán hàng");
        class1.setStatusClass(StatusClass.OPEN);
        class1.setStatusTeacherEdit(StatusTeacherEdit.CHO_PHEP);
        class1.setId(classRepository.save(class1).getId());

        Class class2 = new Class();
        class2.setCode("JAVA_SWING_1");
        class2.setClassPeriod(meetingPeriod5.getId());
        class2.setStartTime(new Date().getTime() + 50000);
        class2.setClassSize(1);
        class2.setPassword("000000");
        class2.setStatusClass(StatusClass.OPEN);
        class2.setActivityId(activity2.getId());
        class2.setTeacherId("FA2BAD81-93A5-4F02-B1B7-08DBB743DD7D".toLowerCase());
        class2.setDescriptions("Lớp làm đồ án tốt nghiệp web bán hàng");
        class2.setStatusTeacherEdit(StatusTeacherEdit.CHO_PHEP);
        class2.setId(classRepository.save(class2).getId());

        Class class3 = new Class();
        class3.setCode("JAVA_WEB_2");
        class3.setClassPeriod(meetingPeriod5.getId());
        class3.setStartTime(new Date().getTime() + 50000);
        class3.setClassSize(1);
        class3.setStatusClass(StatusClass.OPEN);
        class3.setPassword("123456");
        class3.setActivityId(activity1.getId());
//        class3.setTeacherId("1243F96A-42BD-49B3-8E45-08DBB2F9FEB4".toLowerCase());
        class3.setTeacherId("71090c89-f618-41ae-2a8d-08dbb201efe8".toLowerCase());
        class3.setDescriptions("Lớp làm trước dự án 1 bán hàng");
        class3.setId(classRepository.save(class3).getId());

        Class class4 = new Class();// check sent class FAIL do quá số lượng sinh viên
        class4.setCode("JAVA_WEB_3");
        class4.setClassPeriod(meetingPeriod3.getId());
        class4.setStartTime(new Date().getTime() + 50000);
        class4.setClassSize(0);
        class4.setPassword("123456");
        class4.setActivityId(activity1.getId());
        class4.setTeacherId("FA2BAD81-93A5-4F02-B1B7-08DBB743DD7D".toLowerCase());
        class4.setDescriptions("Lớp làm trước đồ án tốt nghiệp bán hàng");
        class4.setStatusClass(StatusClass.OPEN);
        class4.setStatusTeacherEdit(StatusTeacherEdit.CHO_PHEP);
        class4.setId(classRepository.save(class4).getId());

        Class class5 = new Class();// check sent class FAIL do Khác ACtiviti 1quá số lượng sinh viên
        class5.setCode("JAVA_WEB_4");
        class5.setClassPeriod(meetingPeriod3.getId());
        class5.setStartTime(new Date().getTime() + 50000);
        class5.setClassSize(0);
        class5.setPassword("123456");
        class5.setActivityId(activity2.getId());
        class5.setTeacherId("FA2BAD81-93A5-4F02-B1B7-08DBB743DD7D".toLowerCase());
        class5.setDescriptions("Lớp làm trước đồ án tốt nghiệp bán hàng");
        class5.setStatusClass(StatusClass.OPEN);
        class5.setStatusTeacherEdit(StatusTeacherEdit.CHO_PHEP);
        class5.setId(classRepository.save(class5).getId());

        Class class6 = new Class();
        class6.setCode("JAVA_WEB_5");
        class6.setClassPeriod(meetingPeriod3.getId());
        class6.setStartTime(new Date().getTime() + 50000);
        class6.setClassSize(0);
        class6.setPassword("123456");
        class6.setActivityId(activity1.getId());
        class6.setTeacherId("FA2BAD81-93A5-4F02-B1B7-08DBB743DD7D".toLowerCase());
        class6.setDescriptions("Lớp làm trước đồ án tốt nghiệp bán hàng");
        class6.setStatusClass(StatusClass.OPEN);
        class6.setStatusTeacherEdit(StatusTeacherEdit.CHO_PHEP);
        class6.setId(classRepository.save(class6).getId());

        Class class7 = new Class();
        class7.setCode("JAVA_WEB_6");
        class7.setClassPeriod(meetingPeriod3.getId());
        class7.setStartTime(new Date().getTime() + 50000);
        class7.setClassSize(0);
        class7.setPassword("123456");
        class7.setActivityId(activity1.getId());
        class7.setTeacherId("FA2BAD81-93A5-4F02-B1B7-08DBB743DD7D".toLowerCase());
        class7.setDescriptions("Lớp làm trước đồ án tốt nghiệp bán hàng");
        class7.setStatusClass(StatusClass.OPEN);
        class7.setStatusTeacherEdit(StatusTeacherEdit.CHO_PHEP);
        class7.setId(classRepository.save(class7).getId());

        Project project1 = new Project();
        project1.setCode("Project_1");
        project1.setName("Module điểm thưởng");
        project1.setStartTime(1696260088553L);
        project1.setEndTime(1695446153961L);
        project1.setProgress(Float.parseFloat("0"));
        project1.setBackgroundColor("rgb(38, 144, 214)");
        project1.setDescriptions("Mục đích xem điểm thưởng");
        project1.setStatusProject(StatusProject.DANG_DIEN_RA);
        project1.setId((projectRepository.save(project1).getId()));

        Project project2 = new Project();
        project2.setCode("Project_2");
        project2.setName("Module quản lý dự án");
        project2.setStartTime(1678294800000L);
        project2.setEndTime(1685379600000L);
        project2.setProgress(Float.parseFloat("0"));
        project2.setBackgroundColor("#59a1e3");
        project2.setDescriptions("Mục đích của dự án là để quản lý các dự án của bộ môn PTPM");
        project2.setStatusProject(StatusProject.DANG_DIEN_RA);
        project2.setId((projectRepository.save(project2).getId()));

        Project project3 = new Project();
        project3.setCode("Project_3");
        project3.setName("Module bài viết");
        project3.setStartTime(1678294800000L);
        project3.setEndTime(1685379600000L);
        project3.setProgress(Float.parseFloat("0"));
        project3.setBackgroundColor("#59a1e3");
        project3.setDescriptions("Mục đích phục vụ xem bài viết của bộ môn");
        project3.setStatusProject(StatusProject.DANG_DIEN_RA);
        project3.setId((projectRepository.save(project3).getId()));


//Team - class 1
        Team team1 = new Team();
        team1.setProjectId(project1.getId());
        team1.setName("Nhóm 1");
        team1.setSubjectName("Website bán giày Bee Shoes");
        team1.setClassId(class1.getId());
        team1.setId(teamRepository.save(team1).getId());

        Team team2 = new Team();
        team2.setProjectId(project2.getId());
        team2.setName("Nhóm 2");
        team2.setSubjectName("Website camera fly");
        team2.setClassId(class1.getId());
        team2.setId(teamRepository.save(team2).getId());

        Team team3 = new Team();
        team3.setProjectId(project3.getId());
        team3.setName("Nhóm 3");
        team3.setSubjectName("Website bán quần áo Bee Poly");
        team3.setClassId(class1.getId());
        team3.setId(teamRepository.save(team3).getId());

//        Team team4 = new Team();
//        team4.setName("Nhóm 4");
//        team4.setSubjectName("Website bán nước hoa Base Poly");
//        team4.setClassId(class1.getId());
//        team4.setId(teamRepository.save(team4).getId());

        //Team - class 2
        Team team5 = new Team();
        team5.setName("Nhóm 1");
        team5.setSubjectName("Website bán giày Bee Shoes");
        team5.setClassId(class2.getId());
        team5.setId(teamRepository.save(team5).getId());

        Team team6 = new Team();
        team6.setName("Nhóm 2");
        team6.setSubjectName("Website camera HIPOLY");
        team6.setClassId(class2.getId());
        team6.setId(teamRepository.save(team6).getId());
//
//        Team team7 = new Team();
//        team7.setCode("TC2_3");
//        team7.setName("Nhóm 3");
//        team7.setSubjectName("Website bán áo ONESH Poly");
//        team7.setClassId(class2.getId());
//        team7.setId(teamRepository.save(team7).getId());
//
//        Team team8 = new Team();
//        team8.setCode("TC2_4");
//        team8.setName("Nhóm 4");
//        team8.setSubjectName("Website bán quần hoa CHPPoly");
//        team8.setClassId(class2.getId());
//        team8.setId(teamRepository.save(team8).getId());

// student_ class
        // class 1- team 2 (1-5)
        StudentClasses studentClasses1 = new StudentClasses();
        studentClasses1.setStudentId("71090C89-F618-41AE-2A8D-08DBB201EFE8".toLowerCase());
        studentClasses1.setClassId(class1.getId());
        studentClasses1.setTeamId(team2.getId());
        studentClasses1.setEmail("hieundph25894@fpt.edu.vn");
        studentClasses1.setRole(RoleTeam.MEMBER);
        studentClasses1.setStatus(StatusTeam.ACTIVE);
        studentClasses1.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses1.setId(studentClassesRepository.save(studentClasses1).getId());

        StudentClasses studentClasses2 = new StudentClasses();
        studentClasses2.setStudentId("2435C7D5-9BEC-45AC-9BFE-08DBA87523FE".toLowerCase());
        studentClasses2.setClassId(class1.getId());
        studentClasses2.setTeamId(team2.getId());
        studentClasses2.setEmail("thangncph26123@fpt.edu.vn");
        studentClasses2.setRole(RoleTeam.LEADER);
        studentClasses2.setStatus(StatusTeam.ACTIVE);
        studentClasses2.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses2.setId(studentClassesRepository.save(studentClasses2).getId());

        StudentClasses studentClasses3 = new StudentClasses();
        studentClasses3.setStudentId("59F0CB47-5BD4-4909-B1C4-08DBB743DD7D".toLowerCase());
        studentClasses3.setClassId(class1.getId());
        studentClasses3.setTeamId(team1.getId());
        studentClasses3.setEmail("vanntph19604@fpt.edu.vn");
        studentClasses3.setRole(RoleTeam.MEMBER);
        studentClasses3.setStatus(StatusTeam.ACTIVE);
        studentClasses3.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses3.setId(studentClassesRepository.save(studentClasses3).getId());

        StudentClasses studentClasses4 = new StudentClasses();
        studentClasses4.setStudentId("F5882312-81A5-4D44-8E44-08DBB2F9FEB4".toLowerCase());
        studentClasses4.setClassId(class1.getId());
        studentClasses4.setTeamId(team1.getId());
        studentClasses4.setEmail("quynhncph26201@fpt.edu.vn");
        studentClasses4.setRole(RoleTeam.MEMBER);
        studentClasses4.setStatus(StatusTeam.ACTIVE);
        studentClasses4.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses4.setId(studentClassesRepository.save(studentClasses4).getId());

        StudentClasses studentClasses5 = new StudentClasses();
        studentClasses5.setStudentId("80DE791C-F32B-4E7D-8E46-08DBB2F9FEB4".toLowerCase());
        studentClasses5.setClassId(class1.getId());
        studentClasses5.setTeamId(team1.getId());
        studentClasses5.setEmail("hieundph26058@fpt.edu.vn");
        studentClasses5.setRole(RoleTeam.MEMBER);
        studentClasses5.setStatus(StatusTeam.ACTIVE);
        studentClasses5.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses5.setId(studentClassesRepository.save(studentClasses5).getId());

//class 1 - team 1 ( 6-10)
        StudentClasses studentClasses6 = new StudentClasses();
        studentClasses6.setStudentId("2B5C2803-C998-4012-8E47-08DBB2F9FEB4".toLowerCase());
        studentClasses6.setClassId(class1.getId());
        studentClasses6.setTeamId(team1.getId());
        studentClasses6.setEmail("vinhnvph23845@fpt.edu.vn");
        studentClasses6.setRole(RoleTeam.LEADER);
        studentClasses6.setStatus(StatusTeam.ACTIVE);
        studentClasses6.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses6.setId(studentClassesRepository.save(studentClasses6).getId());

        StudentClasses studentClasses7 = new StudentClasses();
        studentClasses7.setStudentId("FCB1D931-CB71-4F12-94D6-08DBB66B2F92".toLowerCase());
        studentClasses7.setClassId(class1.getId());
        studentClasses7.setTeamId(team1.getId());
        studentClasses7.setEmail("huynqph26772@fpt.edu.vn");
        studentClasses7.setRole(RoleTeam.MEMBER);
        studentClasses7.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses7.setStatus(StatusTeam.ACTIVE);
        studentClasses7.setId(studentClassesRepository.save(studentClasses7).getId());

        StudentClasses studentClasses8 = new StudentClasses();
        studentClasses8.setStudentId("6A85641C-874B-4AD0-B1BA-08DBB743DD7D".toLowerCase());
        studentClasses8.setClassId(class1.getId());
        studentClasses8.setTeamId(team2.getId());
        studentClasses8.setEmail("hatqph21186@fpt.edu.vn");
        studentClasses8.setRole(RoleTeam.MEMBER);
        studentClasses8.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses8.setStatus(StatusTeam.ACTIVE);
        studentClasses8.setId(studentClassesRepository.save(studentClasses8).getId());

        StudentClasses studentClasses9 = new StudentClasses();
        studentClasses9.setStudentId("B8E51E50-4823-4F9A-B1BC-08DBB743DD7D".toLowerCase());
        studentClasses9.setClassId(class1.getId());
        studentClasses9.setTeamId(team2.getId());
        studentClasses9.setEmail("nhatnvph26159@fpt.edu.vn");
        studentClasses9.setRole(RoleTeam.MEMBER);
        studentClasses9.setStatus(StatusTeam.ACTIVE);
        studentClasses9.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses9.setId(studentClassesRepository.save(studentClasses9).getId());

        StudentClasses studentClasses10 = new StudentClasses();
        studentClasses10.setStudentId("B34C613D-8AA5-4865-B1BD-08DBB743DD7D".toLowerCase());
        studentClasses10.setClassId(class1.getId());
        studentClasses10.setTeamId(team2.getId());
        studentClasses10.setEmail("tuannvph25577@fpt.edu.vn");
        studentClasses10.setRole(RoleTeam.MEMBER);
        studentClasses10.setStatus(StatusTeam.ACTIVE);
        studentClasses10.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses10.setId(studentClassesRepository.save(studentClasses10).getId());
        // class 1- team 3 (11-15)
        StudentClasses studentClasses11 = new StudentClasses();
        studentClasses11.setStudentId("D3C53418-67EA-47FE-B1BE-08DBB743DD7D".toLowerCase());
        studentClasses11.setClassId(class1.getId());
        studentClasses11.setTeamId(team3.getId());
        studentClasses11.setEmail("anhdtnph25326@fpt.edu.vn");
        studentClasses11.setRole(RoleTeam.LEADER);
        studentClasses11.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses11.setStatus(StatusTeam.ACTIVE);
        studentClasses11.setId(studentClassesRepository.save(studentClasses11).getId());

        StudentClasses studentClasses12 = new StudentClasses();
        studentClasses12.setStudentId("07E9748D-CF8C-4D48-B1BF-08DBB743DD7D".toLowerCase());
        studentClasses12.setClassId(class1.getId());
        studentClasses12.setTeamId(team3.getId());
        studentClasses12.setEmail("trangntph19494@fpt.edu.vn");
        studentClasses12.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses12.setRole(RoleTeam.MEMBER);
        studentClasses12.setStatus(StatusTeam.ACTIVE);
        studentClasses12.setId(studentClassesRepository.save(studentClasses12).getId());

        StudentClasses studentClasses13 = new StudentClasses();
        studentClasses13.setStudentId("967C6BB6-0F50-4862-B1C0-08DBB743DD7D".toLowerCase());
        studentClasses13.setClassId(class1.getId());
        studentClasses13.setTeamId(team3.getId());
        studentClasses13.setEmail("huyvqph25924@fpt.edu.vn");
        studentClasses13.setRole(RoleTeam.MEMBER);
        studentClasses13.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses13.setStatus(StatusTeam.ACTIVE);
        studentClasses13.setId(studentClassesRepository.save(studentClasses13).getId());

        StudentClasses studentClasses14 = new StudentClasses();
        studentClasses14.setStudentId("1D566092-B2DD-49C6-B1C1-08DBB743DD7D".toLowerCase());
        studentClasses14.setClassId(class1.getId());
        studentClasses14.setTeamId(team3.getId());
        studentClasses14.setEmail("hungpvph25929@fpt.edu.vn");
        studentClasses14.setRole(RoleTeam.MEMBER);
        studentClasses14.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses14.setStatus(StatusTeam.ACTIVE);
        studentClasses14.setId(studentClassesRepository.save(studentClasses14).getId());

        StudentClasses studentClasses15 = new StudentClasses();
        studentClasses15.setStudentId("8E0A1E2D-246F-49B7-B1C2-08DBB743DD7D".toLowerCase());
        studentClasses15.setClassId(class1.getId());
        studentClasses15.setTeamId(team3.getId());
        studentClasses15.setEmail("hoangdvph25902@fpt.edu.vn");
        studentClasses15.setRole(RoleTeam.MEMBER);
        studentClasses15.setStatus(StatusTeam.ACTIVE);
        studentClasses15.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses15.setId(studentClassesRepository.save(studentClasses15).getId());

        StudentClasses studentClasses16 = new StudentClasses();
        studentClasses16.setStudentId("69CE3C9F-5100-4A4F-B1C3-08DBB743DD7D".toLowerCase());
        studentClasses16.setClassId(class1.getId());
        studentClasses16.setTeamId(null);
        studentClasses16.setEmail("sonhnph19532@fpt.edu.vn");
        studentClasses16.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses16.setRole(RoleTeam.MEMBER);
        studentClasses16.setStatus(StatusTeam.ACTIVE);
        studentClasses16.setId(studentClassesRepository.save(studentClasses16).getId());

        StudentClasses studentClasses17 = new StudentClasses();
        studentClasses17.setStudentId("33BC7EF4-F150-4D7D-B1C5-08DBB743DD7D".toLowerCase());
        studentClasses17.setClassId(class1.getId());
        studentClasses17.setTeamId(null);
        studentClasses17.setEmail("trangdttph27721@fpt.edu.vn");
        studentClasses17.setRole(RoleTeam.MEMBER);
        studentClasses17.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses17.setStatus(StatusTeam.ACTIVE);
        studentClasses17.setId(studentClassesRepository.save(studentClasses17).getId());

        StudentClasses studentClasses18 = new StudentClasses();
        studentClasses18.setStudentId("5529080E-7F8B-49FC-B1C6-08DBB743DD7D".toLowerCase());
        studentClasses18.setClassId(class1.getId());
        studentClasses18.setTeamId(null);
        studentClasses18.setEmail("cuongnqph26071@fpt.edu.vn");
        studentClasses18.setRole(RoleTeam.MEMBER);
        studentClasses18.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses18.setStatus(StatusTeam.ACTIVE);
        studentClasses18.setId(studentClassesRepository.save(studentClasses18).getId());

        StudentClasses studentClasses19 = new StudentClasses();
        studentClasses19.setStudentId("39715979-9A4A-4F5B-B1C7-08DBB743DD7D".toLowerCase());
        studentClasses19.setClassId(class1.getId());
        studentClasses19.setTeamId(null);
        studentClasses19.setEmail("dungnpph25823@fpt.edu.vn");
        studentClasses19.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        studentClasses19.setRole(RoleTeam.MEMBER);
        studentClasses19.setStatus(StatusTeam.ACTIVE);
        studentClasses19.setId(studentClassesRepository.save(studentClasses19).getId());

        // class 2 - team 5
        StudentClasses studentClasses20 = new StudentClasses();
        studentClasses20.setStudentId("C5715153-1C04-461D-B1C8-08DBB743DD7D".toLowerCase());
        studentClasses20.setClassId(class3.getId());
        studentClasses20.setTeamId(team5.getId());
        studentClasses20.setEmail("huongvnph27229@fpt.edu.vn");
        studentClasses20.setRole(RoleTeam.MEMBER);
        studentClasses20.setStatus(StatusTeam.ACTIVE);
        studentClasses20.setId(studentClassesRepository.save(studentClasses20).getId());

        // class 3 - team 6
        StudentClasses studentClasses21 = new StudentClasses();
        studentClasses21.setStudentId("09B3E4C8-0E0F-4F03-B1C9-08DBB743DD7D".toLowerCase());
        studentClasses21.setClassId(class2.getId());
        studentClasses21.setTeamId(team6.getId());
        studentClasses21.setEmail("anhltvph25818@fpt.edu.vn");
        studentClasses21.setRole(RoleTeam.LEADER);
        studentClasses21.setStatus(StatusTeam.ACTIVE);
        studentClasses21.setId(studentClassesRepository.save(studentClasses21).getId());

        FeedBack feedBack1 = new FeedBack();
        feedBack1.setDescriptions("Day ok #");
        feedBack1.setClassId(class1.getId());
        feedBack1.setStudentId(studentClasses1.getStudentId());
        feedBack1.setId(feedBackRepository.save(feedBack1).getId());

        FeedBack feedBack2 = new FeedBack();
        feedBack2.setDescriptions("Day ok #");
        feedBack2.setClassId(class1.getId());
        feedBack2.setStudentId(studentClasses2.getStudentId());
        feedBack2.setId(feedBackRepository.save(feedBack2).getId());

        FeedBack feedBack3 = new FeedBack();
        feedBack3.setDescriptions("Day ok #");
        feedBack3.setClassId(class1.getId());
        feedBack3.setStudentId(studentClasses3.getStudentId());
        feedBack3.setId(feedBackRepository.save(feedBack3).getId());

        FeedBack feedBack4 = new FeedBack();
        feedBack4.setDescriptions("Day ok #");
        feedBack4.setClassId(class1.getId());
        feedBack4.setStudentId(studentClasses4.getStudentId());
        feedBack4.setId(feedBackRepository.save(feedBack4).getId());

        FeedBack feedBack5 = new FeedBack();
        feedBack5.setDescriptions("Day ok #");
        feedBack5.setClassId(class1.getId());
        feedBack5.setStudentId(studentClasses5.getStudentId());
        feedBack5.setId(feedBackRepository.save(feedBack5).getId());

        FeedBack feedBack6 = new FeedBack();
        feedBack6.setDescriptions("Day ok #");
        feedBack6.setClassId(class1.getId());
        feedBack6.setStudentId(studentClasses6.getStudentId());
        feedBack6.setId(feedBackRepository.save(feedBack6).getId());

        FeedBack feedBack7 = new FeedBack();
        feedBack7.setDescriptions("Day ok #");
        feedBack7.setClassId(class1.getId());
        feedBack7.setStudentId(studentClasses7.getStudentId());
        feedBack7.setId(feedBackRepository.save(feedBack7).getId());

        FeedBack feedBack8 = new FeedBack();
        feedBack8.setDescriptions("Day ok #");
        feedBack8.setClassId(class1.getId());
        feedBack8.setStudentId(studentClasses8.getStudentId());
        feedBack8.setId(feedBackRepository.save(feedBack8).getId());

        FeedBack feedBack9 = new FeedBack();
        feedBack9.setDescriptions("Day ok #");
        feedBack9.setClassId(class1.getId());
        feedBack9.setStudentId(studentClasses9.getStudentId());
        feedBack9.setId(feedBackRepository.save(feedBack9).getId());

        FeedBack feedBack10 = new FeedBack();
        feedBack10.setDescriptions("Day ok #");
        feedBack10.setClassId(class1.getId());
        feedBack10.setStudentId(studentClasses10.getStudentId());
        feedBack10.setId(feedBackRepository.save(feedBack10).getId());

        FeedBack feedBack11 = new FeedBack();
        feedBack11.setDescriptions("Day ok #");
        feedBack11.setClassId(class1.getId());
        feedBack11.setStudentId(studentClasses11.getStudentId());
        feedBack11.setId(feedBackRepository.save(feedBack11).getId());

        FeedBack feedBack12 = new FeedBack();
        feedBack12.setDescriptions("Day ok #");
        feedBack12.setClassId(class1.getId());
        feedBack12.setStudentId(studentClasses12.getStudentId());
        feedBack12.setId(feedBackRepository.save(feedBack12).getId());

        FeedBack feedBack13 = new FeedBack();
        feedBack13.setDescriptions("Day ok #");
        feedBack13.setClassId(class1.getId());
        feedBack13.setStudentId(studentClasses13.getStudentId());
        feedBack13.setId(feedBackRepository.save(feedBack13).getId());

        FeedBack feedBack14 = new FeedBack();
        feedBack14.setDescriptions("Day ok #");
        feedBack14.setClassId(class1.getId());
        feedBack14.setStudentId(studentClasses14.getStudentId());
        feedBack14.setId(feedBackRepository.save(feedBack14).getId());

        FeedBack feedBack15 = new FeedBack();
        feedBack15.setDescriptions("Day ok #");
        feedBack15.setClassId(class1.getId());
        feedBack15.setStudentId(studentClasses15.getStudentId());
        feedBack15.setId(feedBackRepository.save(feedBack15).getId());

        FeedBack feedBack16 = new FeedBack();
        feedBack16.setDescriptions("Day ok #");
        feedBack16.setClassId(class1.getId());
        feedBack16.setStudentId(studentClasses16.getStudentId());
        feedBack16.setId(feedBackRepository.save(feedBack16).getId());

        FeedBack feedBack17 = new FeedBack();
        feedBack17.setDescriptions("Day ok #");
        feedBack17.setClassId(class1.getId());
        feedBack17.setStudentId(studentClasses17.getStudentId());
        feedBack17.setId(feedBackRepository.save(feedBack17).getId());

        FeedBack feedBack18 = new FeedBack();
        feedBack18.setDescriptions("Day ok #");
        feedBack18.setClassId(class1.getId());
        feedBack18.setStudentId(studentClasses18.getStudentId());
        feedBack18.setId(feedBackRepository.save(feedBack18).getId());

        FeedBack feedBack19 = new FeedBack();
        feedBack19.setDescriptions("Day ok #");
        feedBack19.setClassId(class1.getId());
        feedBack19.setStudentId(studentClasses19.getStudentId());
        feedBack19.setId(feedBackRepository.save(feedBack19).getId());

// Meeting
        Meeting meeting1 = new Meeting();
        meeting1.setName("Buổi 1");
        meeting1.setMeetingDate(new Date().getTime() - 86400000);
        meeting1.setMeetingPeriod(meetingPeriod1.getId());
        meeting1.setDescriptions("Học tập và làm theo tấm gương đạo đức HỒ CHÍ MINH");
        meeting1.setClassId(class1.getId());
        meeting1.setTypeMeeting(TypeMeeting.ONLINE);
        meeting1.setTeacherId(class1.getTeacherId());
        meeting1.setStatusMeeting(StatusMeeting.BUOI_NGHI);
        meeting1.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting1.setId(meetingRepository.save(meeting1).getId());


        Meeting meeting2 = new Meeting();
        meeting2.setName("Buổi 2");
        meeting2.setMeetingDate(new Date().getTime());
        meeting2.setMeetingPeriod(meetingPeriod2.getId());
        meeting2.setDescriptions("TÁC HẠI CỦA VIỆC THỨC KHUYA, NGỦ MUỘN");
        meeting2.setClassId(class1.getId());
        meeting2.setTeacherId(class1.getTeacherId());
        meeting2.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting2.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting2.setAddress("");
        meeting2.setId(meetingRepository.save(meeting2).getId());

        Meeting meeting1Class = new Meeting();
        meeting1Class.setName("Buổi 1");
        meeting1Class.setMeetingDate(new Date().getTime() - 86400000);
        meeting1Class.setMeetingPeriod(meetingPeriod2.getId());
        meeting1Class.setTeacherId(class2.getTeacherId());
        meeting1Class.setDescriptions("Buổi 1 lớp thầy Nguyên VV4 _ 001 J5_NGUYENVV4_001");
        meeting1Class.setClassId(class2.getId());
        meeting1Class.setNotes("Buổi 1 đã điểm danh");
        meeting1Class.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting1Class.setTypeMeeting(TypeMeeting.ONLINE);
        meeting1Class.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting1Class.setId(meetingRepository.save(meeting1Class).getId());

        Attendance attendanceMeeting1 = new Attendance();
        attendanceMeeting1.setMeetingId(meeting1Class.getId());
        attendanceMeeting1.setName(meeting1Class.getName());
        attendanceMeeting1.setStudentId(studentClasses21.getStudentId());
        attendanceMeeting1.setStatus(StatusAttendance.YES);
        attendanceMeeting1.setId(attendanceRepository.save(attendanceMeeting1).getId());

        Meeting meeting2Class = new Meeting();
        meeting2Class.setName("Buổi 2");
        meeting2Class.setMeetingDate(new Date().getTime());
        meeting2Class.setMeetingPeriod(meetingPeriod2.getId());
        meeting2Class.setDescriptions("Kỷ luật tốt Giữ gìn vệ sinh thật tốt Khiêm tốn, thật thà, dũng cảm");
        meeting2Class.setClassId(class2.getId());
        meeting2Class.setTeacherId(class2.getTeacherId());
        meeting2Class.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting2Class.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting2Class.setAddress("");
        meeting2Class.setId(meetingRepository.save(meeting2Class).getId());

        Meeting meeting3 = new Meeting();
        meeting3.setName("Buổi 3");
        meeting3.setMeetingDate(new Date().getTime() + 3);
        meeting3.setMeetingPeriod(meetingPeriod6.getId());
        meeting3.setDescriptions("Câu chuyện về 5 điều Bác Hồ dạy cũng nhắc nhở chúng ta, không chỉ các cơ quan báo chí mà nhiều ngành, nhiều lĩnh vực khác hiện nay cũng đang đơn giản, dễ dãi trong dùng từ ");
        meeting3.setClassId(class1.getId());
        meeting3.setTeacherId(class1.getTeacherId());
        meeting3.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting3.setTypeMeeting(TypeMeeting.ONLINE);
        meeting3.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting3.setId(meetingRepository.save(meeting3).getId());

        Meeting meeting4 = new Meeting();
        meeting4.setName("Buổi 4");
        meeting4.setMeetingDate(new Date().getTime() + 86400000);
        meeting4.setMeetingPeriod(meetingPeriod2.getId());
        meeting4.setDescriptions("5 Điều Bác Hồ Dạy Thiếu niên, Nhi đồng");
        meeting4.setClassId(class1.getId());
        meeting4.setTeacherId(class1.getTeacherId());
        meeting4.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting4.setTypeMeeting(TypeMeeting.ONLINE);
        meeting4.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting4.setId(meetingRepository.save(meeting4).getId());

        Meeting meeting5 = new Meeting();
        meeting5.setName("Buổi 5");
        meeting5.setMeetingDate(new Date().getTime() + 2 * 86400000);
        meeting5.setMeetingPeriod(meetingPeriod3.getId());
        meeting5.setDescriptions("");
        meeting5.setClassId(class1.getId());
        meeting5.setTeacherId(class1.getTeacherId());
        meeting5.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting5.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting5.setAddress("");
        meeting5.setId(meetingRepository.save(meeting5).getId());

        Meeting meeting6 = new Meeting();
        meeting6.setName("Buổi 6");
        meeting6.setMeetingDate(new Date().getTime() + 3 * 86400000 + 5);
        meeting6.setMeetingPeriod(meetingPeriod4.getId());
        meeting6.setDescriptions("Xét từ góc độ chủ thể sáng tạo và phát triển (ai làm nên nó): là hệ thống quan điểm và học thuyết đó được sáng lập bởi C. Mác, Ph. Ăngghen và sự phát triển, vận dụng vào thực tiễn của V.I. Lênin");
        meeting6.setClassId(class1.getId());
        meeting6.setTeacherId(class1.getTeacherId());
        meeting6.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting6.setTypeMeeting(TypeMeeting.ONLINE);
        meeting6.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting6.setId(meetingRepository.save(meeting6).getId());

        Meeting meeting7 = new Meeting();
        meeting7.setName("Buổi 7  ");
        meeting7.setMeetingDate(new Date().getTime() + 3 * 86400000 + 300);
        meeting7.setMeetingPeriod(meetingPeriod2.getId());
        meeting7.setDescriptions("Xét từ góc độ cấu tạo (nó gồm có những cái gì): Chủ nghĩa Mác - Lênin có ba bộ phận lý luận cơ bản hợp thành");
        meeting7.setClassId(class1.getId());
        meeting7.setTeacherId(class1.getTeacherId());
        meeting7.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting7.setTypeMeeting(TypeMeeting.ONLINE);
        meeting7.setAddress("https://meet.google.com/kea-hhgi-yix");
        meeting7.setId(meetingRepository.save(meeting7).getId());

        Meeting meeting8 = new Meeting();
        meeting8.setName("Buổi 8");
        meeting8.setMeetingDate(new Date().getTime() + 4 * 86400000);
        meeting8.setMeetingPeriod(meetingPeriod1.getId());
        meeting8.setDescriptions("Triết học là bộ phận lý luận nghiên cứu những quy luật vận động, phát triển chung nhất của tự nhiên, xã hội và tư duy");
        meeting8.setClassId(class1.getId());
        meeting8.setTeacherId(class1.getTeacherId());
        meeting8.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting8.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting8.setAddress("");
        meeting8.setId(meetingRepository.save(meeting8).getId());

        Meeting meeting9 = new Meeting();
        meeting9.setName("Buổi 9");
        meeting9.setMeetingDate(new Date().getTime() + 6 * 86400000);
        meeting9.setMeetingPeriod(meetingPeriod2.getId());
        meeting9.setDescriptions("Triết học là bộ phận lý luận nghiên cứu những quy luật vận động, phát triển chung nhất của tự nhiên, xã hội và tư duy");
        meeting9.setClassId(class1.getId());
        meeting9.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting9.setTeacherId(class1.getTeacherId());
        meeting9.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting9.setAddress("");
        meeting9.setId(meetingRepository.save(meeting9).getId());

        Meeting meeting10 = new Meeting();
        meeting10.setName("Buổi 10");
        meeting10.setTeacherId(class1.getTeacherId());
        meeting10.setMeetingDate(new Date().getTime() + 7 * 86400000);
        meeting10.setMeetingPeriod(meetingPeriod2.getId());
        meeting10.setDescriptions("Triết học là bộ phận lý luận nghiên cứu những quy luật vận động, phát triển chung nhất của tự nhiên, xã hội và tư duy");
        meeting10.setClassId(class1.getId());
        meeting10.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting10.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting10.setAddress("");
        meeting10.setId(meetingRepository.save(meeting10).getId());

        Meeting meeting11 = new Meeting();
        meeting11.setName("Buổi 11");
        meeting11.setMeetingDate(new Date().getTime() + 8 * 86400000);
        meeting11.setMeetingPeriod(meetingPeriod2.getId());
        meeting11.setDescriptions("Chủ nghĩa Mác-Lênin là hệ thống quan điểm và học thuyết khoa học của C.Mác, Ph.Ăngghen và sự phát triển của V.I.Lênin");
        meeting11.setClassId(class1.getId());
        meeting11.setTeacherId(class1.getTeacherId());
        meeting11.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting11.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting11.setAddress("");
        meeting11.setId(meetingRepository.save(meeting11).getId());

        Meeting meeting12 = new Meeting();
        meeting12.setName("Buổi 12");
        meeting12.setMeetingDate(new Date().getTime() + 9 * 86400000);
        meeting12.setMeetingPeriod(meetingPeriod2.getId());
        meeting12.setDescriptions("Chủ nghĩa cộng sản được xây dựng bởi các nhà sáng lập chủ nghĩa cộng sản Marx");
        meeting12.setClassId(class1.getId());
        meeting12.setTeacherId(class1.getTeacherId());
        meeting12.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting12.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting12.setAddress("");
        meeting12.setId(meetingRepository.save(meeting12).getId());

        Meeting meeting13 = new Meeting();
        meeting13.setName("Buổi 13 ");
        meeting13.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting13.setMeetingDate(new Date().getTime() + 13 * 86400000);
        meeting13.setMeetingPeriod(meetingPeriod2.getId());
        meeting13.setDescriptions("Nhà sử học Marx–Lenin đương đại Eric Hobsbawm");
        meeting13.setClassId(class1.getId());
        meeting13.setTeacherId(class1.getTeacherId());
        meeting13.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting13.setAddress("");
        meeting13.setId(meetingRepository.save(meeting13).getId());

        Meeting meeting14 = new Meeting();
        meeting14.setName("Buổi 14 ");
        meeting14.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting14.setMeetingDate(new Date().getTime() + 14 * 86400000);
        meeting14.setMeetingPeriod(meetingPeriod2.getId());
        meeting14.setDescriptions("Sau sự ly khai của những người vô chính phủ, quốc tế thứ nhất tan vỡ");
        meeting14.setClassId(class1.getId());
        meeting14.setTeacherId(class1.getTeacherId());
        meeting14.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting14.setAddress("");
        meeting14.setId(meetingRepository.save(meeting14).getId());

        Meeting meeting15 = new Meeting();
        meeting15.setName("Buổi 15");
        meeting15.setMeetingDate(new Date().getTime() + 15 * 86400000);
        meeting15.setMeetingPeriod(meetingPeriod2.getId());
        meeting15.setDescriptions("Không có giải pháp nào là vĩnh cửu");
        meeting15.setClassId(class1.getId());
        meeting15.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting15.setTeacherId(class1.getTeacherId());
        meeting15.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting15.setAddress("");
        meeting15.setId(meetingRepository.save(meeting15).getId());

        Meeting meeting16 = new Meeting();
        meeting16.setName("Buổi 16");
        meeting16.setMeetingDate(new Date().getTime() + 16 * 86400000);
        meeting16.setMeetingPeriod(meetingPeriod2.getId());
        meeting16.setDescriptions("Sự sụp đổ của Liên Xô và Đông Âu (do kinh tế gặp nhiều khó khăn, trong khi nhà nước không có dấu hiệu tự triệt tiêu như ý tưởng của Marx)");
        meeting16.setClassId(class1.getId());
        meeting16.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting16.setTeacherId(class1.getTeacherId());
        meeting16.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting16.setAddress("");
        meeting16.setId(meetingRepository.save(meeting16).getId());

        Meeting meeting17 = new Meeting();
        meeting17.setName("Buổi 17");
        meeting17.setMeetingDate(new Date().getTime() + 17 * 86400000);
        meeting17.setMeetingPeriod(meetingPeriod2.getId());
        meeting17.setDescriptions("Lời kêu gọi này được phát ra vào sáng ngày 20 tháng 12 năm 1946");
        meeting17.setClassId(class1.getId());
        meeting17.setTeacherId(class1.getTeacherId());
        meeting17.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting17.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting17.setAddress("");
        meeting17.setId(meetingRepository.save(meeting17).getId());

        Meeting meeting18 = new Meeting();
        meeting18.setName("Buổi 18");
        meeting18.setMeetingDate(new Date().getTime() + 18 * 86400000);
        meeting18.setMeetingPeriod(meetingPeriod2.getId());
        meeting18.setDescriptions("Ngày 19 tháng 12, khi chiến sự bùng nổ - là ngày được gọi là Toàn quốc kháng chiến");
        meeting18.setClassId(class1.getId());
        meeting18.setTeacherId(class1.getTeacherId());
        meeting18.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting18.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting18.setAddress("");
        meeting18.setId(meetingRepository.save(meeting18).getId());

        Meeting meeting19 = new Meeting();
        meeting19.setName("Buổi 19");
        meeting19.setMeetingDate(new Date().getTime() + 19 * 86400000);
        meeting19.setMeetingPeriod(meetingPeriod2.getId());
        meeting19.setDescriptions("Thức khuya hay ngủ ít có thể dẫn tới nguy cơ tăng cân theo chiều hướng tiêu cực, có thể gây thêm các tác dụng khác là nguy cơ mắc bệnh tiểu đường, tăng huyết áp…");
        meeting19.setClassId(class1.getId());
        meeting19.setTeacherId(class1.getTeacherId());
        meeting19.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting19.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting19.setAddress("");
        meeting19.setId(meetingRepository.save(meeting19).getId());

        Meeting meeting20 = new Meeting();
        meeting20.setName("Buổi 20");
        meeting20.setMeetingDate(new Date().getTime() + 20 * 86400000);
        meeting20.setMeetingPeriod(meetingPeriod2.getId());
        meeting20.setDescriptions("75 năm trôi qua, nhưng khí thế hào hùng của Lời kêu gọi toàn quốc kháng chiến đã trở thành ngày lịch sử, là dấu son chói lọi trong cuộc đấu tranh bảo vệ độc lập, chủ quyền, thống nhất và toàn vẹn lãnh thổ.");
        meeting20.setClassId(class1.getId());
        meeting20.setTeacherId(class1.getTeacherId());
        meeting20.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting20.setTypeMeeting(TypeMeeting.OFFLINE);
        meeting20.setAddress("");
        meeting20.setId(meetingRepository.save(meeting20).getId());

// homework
        //class 1- team 1
        HomeWork homeWork1 = new HomeWork();
        homeWork1.setName("Bài tập về nhà buổi 1 team bee fly");
        homeWork1.setDescriptions("Tạo entity và mapping dữ liệu - BEE FLY");
        homeWork1.setMeetingId(meeting1.getId());
        homeWork1.setTeamId(team1.getId());
        homeWork1.setId(homeWorkRepository.save(homeWork1).getId());

        HomeWork homeWork2 = new HomeWork();
        homeWork2.setName("Bài tập về nhà buổi 2");
        homeWork2.setDescriptions("Tạo repository sử dụng spring jpa - BEE FLY");
        homeWork2.setMeetingId(meeting2.getId());
        homeWork2.setTeamId(team1.getId());
        homeWork2.setId(homeWorkRepository.save(homeWork2).getId());

        HomeWork homeWork3 = new HomeWork();
        homeWork3.setName("Bài tập về nhà buổi 3");
        homeWork3.setDescriptions("Tạo serice và service implements - BEE FLY");
        homeWork3.setMeetingId(meeting3.getId());
        homeWork3.setTeamId(team1.getId());
        homeWork3.setId(homeWorkRepository.save(homeWork3).getId());

        HomeWork homeWork4 = new HomeWork();
        homeWork4.setName("Bài tập về nhà buổi 4");
        homeWork4.setDescriptions("Tạo model chứa các request và responese - BEE FLY");
        homeWork4.setMeetingId(meeting4.getId());
        homeWork4.setTeamId(team1.getId());
        homeWork4.setId(homeWorkRepository.save(homeWork4).getId());

        HomeWork homeWork5 = new HomeWork();
        homeWork5.setName("Bài tập về nhà buổi 5");
        homeWork5.setDescriptions("Thêm config vào file application.properties - BEE FLY");
        homeWork5.setMeetingId(meeting5.getId());
        homeWork5.setTeamId(team1.getId());
        homeWork5.setId(homeWorkRepository.save(homeWork5).getId());

        HomeWork homeWork6 = new HomeWork();
        homeWork6.setName("Bài tập về nhà buổi 6");
        homeWork6.setDescriptions("Tạo file config message.properties chứa các key validations - BEE FLY");
        homeWork6.setMeetingId(meeting6.getId());
        homeWork6.setTeamId(team1.getId());
        homeWork6.setId(homeWorkRepository.save(homeWork6).getId());

        HomeWork homeWork7 = new HomeWork();
        homeWork7.setName("Bài tập về nhà buổi 7");
        homeWork7.setDescriptions("Thêm 1 số chức năng vào service - BEE FLY");
        homeWork7.setMeetingId(meeting7.getId());
        homeWork7.setTeamId(team1.getId());
        homeWork7.setId(homeWorkRepository.save(homeWork7).getId());

        HomeWork homeWork8 = new HomeWork();
        homeWork8.setName("Bài tập về nhà buổi 8");
        homeWork8.setDescriptions("Tạo package Controller sử dụng SPRINGBOOT - BEE FLY");
        homeWork8.setMeetingId(meeting8.getId());
        homeWork8.setTeamId(team1.getId());
        homeWork8.setId(homeWorkRepository.save(homeWork8).getId());

        HomeWork homeWork9 = new HomeWork();
        homeWork9.setName("Bài tập về nhà buổi 9");
        homeWork9.setDescriptions("Sử dụng @RestController API và Sercurity ADMIN - BEE FLY");
        homeWork9.setMeetingId(meeting9.getId());
        homeWork9.setTeamId(team1.getId());
        homeWork9.setId(homeWorkRepository.save(homeWork9).getId());
        //note -team 1

        Note note1 = new Note();
        note1.setName("Note buổi 1 team 1 Bee fly");
        note1.setMeetingId(meeting1.getId());
        note1.setTeamId(team1.getId());
        note1.setDescriptions("team 1");
        note1.setId(noteRepository.save(note1).getId());

        Note note2 = new Note();
        note2.setName("Note buổi 2 team 1 Bee fly");
        note2.setMeetingId(meeting2.getId());
        note2.setTeamId(team1.getId());
        note2.setDescriptions("team 1");
        note2.setId(noteRepository.save(note2).getId());

        Note note3 = new Note();
        note3.setName("Note buổi 3 team 2");
        note3.setMeetingId(meeting3.getId());
        note3.setTeamId(team1.getId());
        note3.setDescriptions("Service ok");
        note3.setId(noteRepository.save(note3).getId());

        Note note4 = new Note();
        note4.setName("Note buổi 4 team 2");
        note4.setMeetingId(meeting4.getId());
        note4.setTeamId(team1.getId());
        note4.setDescriptions("Model view ok");
        note4.setId(noteRepository.save(note4).getId());

        Note note5 = new Note();
        note5.setName("Note buổi 5 team 2");
        note5.setMeetingId(meeting5.getId());
        note5.setTeamId(team1.getId());
        note5.setDescriptions("Config ok");
        note5.setId(noteRepository.save(note5).getId());

        Note note6 = new Note();
        note6.setName("Note buổi 6 team 2");
        note6.setMeetingId(meeting6.getId());
        note6.setTeamId(team1.getId());
        note6.setDescriptions("Message config ok");
        note6.setId(noteRepository.save(note6).getId());

        Note note7 = new Note();
        note7.setName("Note buổi 7 team 2");
        note7.setMeetingId(meeting7.getId());
        note7.setTeamId(team1.getId());
        note7.setDescriptions("Chức năng bee fly ok");
        note7.setId(noteRepository.save(note7).getId());

        Note note8 = new Note();
        note8.setName("Note buổi 1 team 2");
        note8.setMeetingId(meeting8.getId());
        note8.setTeamId(team1.getId());
        note8.setDescriptions("Note buoi 8 nhe");
        note8.setId(noteRepository.save(note8).getId());

        // report - team 1 - class 1
        Report report1 = new Report();
        report1.setDescriptions("Báo cáo: \n  1.Sinh viên 1: Đi học thêm" +
                "\n  2. Sinh viên 2: Đi chơi với người yêu" + "\n  3. Sinh viên 3: Ngủ nướng cả ngày");
        report1.setMeetingId(meeting1.getId());
        report1.setTeamId(team1.getId());
        report1.setId(reportRepository.save(report1).getId());

        Report report2 = new Report();
        report2.setDescriptions("Báo cáo: \n  1.Sinh viên 1: PAYYY" +
                "\n  2. Sinh viên 2: NHẢY" + "\n  3. Sinh viên 3: HIIIIIIIIIIIIIIIIHIHA");
        report2.setMeetingId(meeting2.getId());
        report2.setTeamId(team1.getId());
        report2.setId(reportRepository.save(report2).getId());

        TemplateReport templateReport = new TemplateReport();
        templateReport.setDescriptions("Báo cáo: \n  1. Sinh viên 1: Mô tả nhiệm vụ, công việc " +
                "\n  2. Sinh viên 2: ..." + "\n  3. Sinh viên 3: ..." + "\n  4. Sinh viên : ...");
        templateReport.setId(templateReportRepository.save(templateReport).getId());

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
        // post1.setTeacherId(class1.getTeacherId());
        post1.setTeacherId("FA2BAD81-93A5-4F02-B1B7-08DBB743DD7D".toLowerCase());
        post1.setClassId(class1.getId());
        post1.setCreatedDate(1696221098000L);
        post1.setId(postRepository.save(post1).getId());

        Post post12 = new Post();
        post12.setDescriptions("""
                Chào mừng đến với workshop "Hành trình Khám phá Hàm"!
                📣 Workshop này sẽ giúp bạn:
                ✅ Hiểu rõ cách sử dụng hàm trong lập trình.
                ✅ Nắm vững cách viết các loại hàm.
                ✅ Áp dụng kiến thức vào viết mã nguồn tối ưu hơn.
                📣 Hãy tham gia để:
                ✍️ Nắm vững cú pháp và khai báo hàm.
                ✍️ Hiểu cách truyền tham số và nhận giá trị trả về từ hàm.
                ✍️ Tìm hiểu các khái niệm quan trọng như biến cục bộ và biến toàn cục trong hàm.
                🗓️ Thời gian: Thứ 5, ngày 05/10/2023
                🕒 Từ 20:30 - 22:30
                🏠 Địa điểm: Zoom
                🎤 Diễn giả:
                1️⃣ Cô HuyenNK6
                2️⃣ Cô HangNT169
                3️⃣ Cô NganCT4
                🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡
                🔑Workshop "Hành trình Khám phá Hàm" sẽ mang lại cho bạn những kiến thức, kỹ năng cần thiết để nâng cao chất lượng và hiệu quả của mã nguồn lập trình.
                🔑Đừng bỏ lỡ cơ hội này! Đăng ký ngay để tham gia workshop và mở ra cánh cửa mới cho sự phát triển và thành công trong lĩnh vực lập trình!
                👋🔥🔥🔥🔥🔥🔥🔥🔥🔥
                📩📩Link đăng ký: https://forms.gle/pG2sBXuPkSngnFNE8 """);
        // post1.setTeacherId(class1.getTeacherId());
        post12.setTeacherId("FA2BAD81-93A5-4F02-B1B7-08DBB743DD7D".toLowerCase());
        post12.setClassId(class1.getId());
        post12.setCreatedDate(new Date().getTime() - 10000);
        post12.setId(postRepository.save(post12).getId());

        Post post2 = new Post();
        post2.setDescriptions("""
                📢📢📢Cuộc thi Ý tưởng khởi nghiệp sinh viên - Startup Kite 2023  – ƯƠM MẦM TƯƠNG LAI đang dần bước vào Bán kết
                👉👉👉Cùng với sự góp sức của các thầy cô bộ môn UDPM, 2 đội dự thi đã đứng TOP đầu vòng loại, tiến thẳng vào vòng Bán kết Quốc gia. Với 2 dự án tiêu biểu sau:
                🏖Dự án: TripS - Ứng dụng hướng dẫn viên du lịch thông minh
                ⛵️Nhóm lĩnh vực dự thi: Du lịch
                🏋️Dự án: SportLS - Ứng dụng tìm kiếm địa điểm thể thao
                🏙Nhóm lĩnh vực dự thi: Công nghệ cho xã hội và phát triển cộng đồng
                🍻🥂🍺Cùng chúc mừng 🎊🎊, cổ vũ tất cả các thành viên trong đội tiến bước vào BÁN KẾT và dành tấm vé vào vòng Chung kết Quốc gia🏅🏅🏅
                """);
        post2.setTeacherId(class1.getTeacherId());
        post2.setClassId(class1.getId());
        post2.setCreatedDate(new Date().getTime() - 20000);
        post2.setId(postRepository.save(post2).getId());

        Post post3 = new Post();
        post3.setDescriptions("""
                📝Nắm bắt bí kíp qua môn với Tutor - Đăng ký ngay! 📝
                Nhanh tay đăng ký ngay Tutor để không bỏ lỡ những kiến thực cực kỳ bổ ích nào các bạn🥰🥰
                Kỳ FALL 2023 - Block 1 này , các bạn nhanh tay đăng ký các môn Tutor để các thầy cô truyền bí kíp qua môn
                1.COM108:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                2. MOB1014:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                3. SOF2041:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                4. SOF3021:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                5. COM2034:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                6. COM2012:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                7. MOB1023:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                8. NET101:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                9. NET102:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                10. SOF205:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                11. SOA101:
                Ngày: 21/09/2023- 13/10/2023
                Thời gian: 12:00-14:00
                Form đăng ký: https://forms.gle/9w966vnJuHqm3KTr9
                #fptpolytechnic #cntt #ptpm #udpm #seminar #workshop #tutor
                """);
        post3.setTeacherId(class1.getTeacherId());
        post3.setClassId(class1.getId());
        post3.setCreatedDate(1696221098000L);
        post3.setId(postRepository.save(post3).getId());

        Post post4 = new Post();
        post4.setDescriptions("Giá trị đức tính “Cần, Kiệm, Liêm, Chính” trong tư tưởng Hồ Chí Minh về xây dựng đạo đức cách mạng của đội ngũ cán bộ, đảng viên hiện nay !");
        post4.setTeacherId(class1.getTeacherId());
        post4.setClassId(class1.getId());
        post4.setCreatedDate(new Date().getTime() - 86400000 - 500);
        post4.setId(postRepository.save(post4).getId());

        Post post5 = new Post();
        post5.setDescriptions("""
                📖 VUI CHƠI ĐỪNG BỎ RƠI WORKSHOP📖

                🧡🧡Trong buổi này chúng ta sẽ cùng nhau chỉ rõ hơn về vai trò của CODING CONVENTION trong khi lập trình
                Với sự tham gia của của các GV:
                1️⃣ Cô Nguyễn Khánh Huyền - HuyenNK6
                2️⃣ Cô Nguyễn Thuý Hằng - HangNT169
                3️⃣ Cô Chu Thị Ngân - NganCT4

                ✔️Workshop CODING CONVENTIONS sẽ có các nội dung chính sau đây:
                ✍️Khái niệm coding conventions
                ✍️ Tầm quan trọng của convetion trong code như nào
                ✍️ Các tiêu chí của coding convention trong lập trình
                ✍️ Quy tắc đặt tên (camelCase, PascalCase, snake_case)…

                🔥🔥🔥 ĐẶC BIỆT 🔥🔥🔥
                ✅ Vận dụng với CẤU TRÚC RẼ NHÁNH & VÒNG LẶP theo quy tiêu chuẩn code
                ✔️Buổi WORKSHOP này sẽ là bước đệm hoàn hảo giúp các bạn CHUẨN HÓA HOÀN HẢO

                🧡🧡🧡🧡🧡 WORKSHOP 🧡🧡🧡🧡🧡
                🕘 Thời gian: 20:30 - 22:30
                🗓 Ngày: 18-09-2023
                🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡🧡
                🔑Tất cả sẽ sẽ được bật mí tại workshop “CODING CONVENTIONS”
                👋🔥🔥🔥🔥🔥🔥🔥🔥🔥
                📩📩Link đăng ký: https://forms.gle/vcg2sVSfgKU4ytvB6
                #fptpolytechnic #cntt #ptpm #udpm #seminar #workshop
                """);
        post5.setTeacherId(class1.getTeacherId());
        post5.setClassId(class1.getId());
        post5.setCreatedDate(new Date().getTime() - 1 * 86400000);
        post5.setId(postRepository.save(post5).getId());

        Post post6 = new Post();
        post6.setDescriptions("""
                ✍ ✍ ✍ HỌC ĂN, HỌC NÓI, HỌC GÓI, HỌC MỞ ..... HỌC GÌ Ở XƯỞNG NHỈ ???
                💥 LIVESTREAM HỌC GÌ Ở XƯỞNG RA MẮT💥
                😍😍😍 Bật mí những Dự án trong xưởng:\s
                       👉Dự án Swing nâng cao\s
                       👉Dự án Winform nâng cao\s
                       👉Dự án java web nâng cao \s
                       👉Dự án C# web nâng cao\s
                       👉 Dự án kiểm thử nâng cao
                       👉Dự án IT helpdesk cho sinh viên ngành UDPM
                        ...............CONTINUE.........
                📢📢ĐỂ BIẾT CỤ THỂ VÀ CHI TIẾT HƠN NỮA THÌ CÁC BẠN HÃY JOIN BUỔI LIVESTREAM VÔ CÙNG HỮU ÍCH NÀY NHÉ 💪💪💪
                🔥VỚI SỰ GÓP MẶT CỦA 2 THẦY: 🔥
                👨🏻‍💼 Thầy DungNA29- CNBM UPDM Dung Anh Nguyen
                👨🏻‍🔧 Thầy NguyenVV4- Giám đốc Xưởng TH Vũ Nguyên
                💢Hãy nhanh tay đăng ký ngay TẠI ĐƯỜNG LINK:  https://forms.gle/74F4SqUk38wZAqUi6\s
                👉Link tham gia nhóm Xưởng thực hành: https://zalo.me/g/epztrt410
                ⏰ Thời gian:  20h30
                📅 Ngày: 16-09-2023
                💻 Hình thức tham gia: Online Livestream qua Facebook
                """);
        post6.setTeacherId(class1.getTeacherId());
        post6.setClassId(class1.getId());
        post6.setCreatedDate(new Date().getTime() - 2 * 86400000);
        post6.setId(postRepository.save(post6).getId());

        Post post7 = new Post();
        post7.setDescriptions("""
                [LIVESTREAM TRỰC TIẾP]
                📌 NGÀNH XỬ LÝ DỮ LIỆU - CƠ HỘI & THÁCH THỨC
                📢📢 HOT HOT📢📢
                🔑 Sắp tới có 1 buổi livestream chia sẻ về ngành XỬ LÝ DỮ LIỆU cùng các diễn giả. Các bạn sinh viên nhanh tay đăng ký tham gia và đón xem!\s
                🥰 GV1: Nguyễn Anh Dũng - CNBM UDPM
                🥰 GV2: Trần Tuấn Phong - TM XLDL
                🥰 GV3: Vũ Thị Kim Thư - GV XLDL
                🥰 GV4: Đỗ Bảo Linh - GV XLDL
                🥰 GV5: Chu Thị Ngân - GV XLDL
                🎯 Bạn nhận được gì từ buổi OR NGÀNH???
                ✔ Được chia sẻ, lắng nghe tâm tư nguyện vọng của các bạn về ngành
                ✔ Giúp các bạn tìm định hướng nghề nghiệp và thách thức trong ngành Xử lý dữ liệu
                ✔ Cơ hội tiếp cận nguồn học tập hiệu quả, ring về những phần quà hấp dẫn
                ✔ Và còn nhiều thứ hay ho và hấp dẫn khác,...
                """);
        post7.setTeacherId(class1.getTeacherId());
        post7.setClassId(class1.getId());
        post7.setCreatedDate(new Date().getTime());
        post7.setId(postRepository.save(post7).getId());

        Post post8 = new Post();
        post8.setDescriptions("<p><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\">Trong suốt quá trình thực dân Pháp cai trị, từ giữa thế kỷ XIX đến đầu thế kỷ XX, đã có nhiều cuộc khởi nghĩa nổi dậy chống<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/Th%E1%BB%B1c_d%C3%A2n_Ph%C3%A1p\" class=\"mw-redirect\" title=\"Thực dân Pháp\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">thực dân Pháp</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\">. Các cuộc khởi nghĩa vũ trang dưới khẩu hiệu \"Cần vương\" do các văn thân, sĩ phu lãnh đạo cuối cùng cũng thất bại. Các cuộc khởi nghĩa vũ trang này mang đậm tinh thần yêu nước và đầy dũng khí trước quân thù, nhưng đều dưới sự dẫn dắt của các tư tưởng phong kiến và tư sản và đều thất bại. Cách mạng Việt Nam lâm vào cuộc khủng khoảng sâu sắc về đường lối cứu nước.</span><sup id=\"cite_ref-9\" class=\"reference\" style=\"line-height: 1em; font-size: 11.2px; white-space: nowrap; unicode-bidi: isolate; font-weight: 400; font-style: normal; color: rgb(32, 33, 34); font-family: sans-serif; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\"><a href=\"https://vi.wikipedia.org/wiki/T%C6%B0_t%C6%B0%E1%BB%9Fng_H%E1%BB%93_Ch%C3%AD_Minh1cite_note-9\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none; overflow-wrap: break-word; white-space: nowrap;\">[9]</a></sup><br></p>");
        post8.setTeacherId(class1.getTeacherId());
        post8.setClassId(class1.getId());
        post8.setCreatedDate(new Date().getTime() + 1 * 86400000);
        post8.setId(postRepository.save(post8).getId());

        Post post9 = new Post();
        post9.setDescriptions("<p><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\">Tư tưởng Hồ Chí Minh chỉ chính thức đưa vào Cương lĩnh của Đảng Cộng sản Việt Nam năm 1991, sau khi công cuộc Đổi mới phát động, chấp thuận phân hóa giai cấp, nhiều lý luận của chủ nghĩa Mác - Lênin không có tính khả thi trong cơ chế thị trường phải gác lại như đạo đức xã hội chủ nghĩa, xây dựng con người xã hội chủ nghĩa, làm theo năng lực hưởng theo lao động trên toàn xã hội... (</span><a href=\"https://vi.wikipedia.org/wiki/Ch%E1%BB%A7_ngh%C4%A9a_c%E1%BB%99ng_s%E1%BA%A3n\" title=\"Chủ nghĩa cộng sản\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">chủ nghĩa cộng sản</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>đặt ra mục tiêu cuối cùng là xóa bỏ giai cấp, bóc lột, xóa bỏ giàu - nghèo, làm theo năng lực hưởng theo nhu cầu, công hữu trên nền tảng dân chủ, xóa bỏ giáo điều tôn giáo được xem là mị dân, xóa bỏ nhà nước đi đến dân chủ trực tiếp và bình đẳng, xóa bỏ các đường biên giới quốc gia, đưa các dân tộc đến cùng một lợi ích, xóa bỏ bất bình đẳng giữa các dân tộc trên phạm vi thế giới...). Các giáo trình của Việt Nam thường khai thác tư tưởng Hồ Chí Minh theo chiều hướng trên.</span><br></p>");
        post9.setTeacherId(class1.getTeacherId());
        post9.setClassId(class1.getId());
        post9.setCreatedDate(new Date().getTime() + 2 * 86400000);
        post9.setId(postRepository.save(post9).getId());

        Post post10 = new Post();
        post10.setDescriptions("<p><strong style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">Tư tưởng Hồ Chí Minh</strong><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>là một hệ thống quan điểm và<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/T%C6%B0_t%C6%B0%E1%BB%9Fng\" class=\"mw-redirect\" title=\"Tư tưởng\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">tư tưởng</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>của<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/H%E1%BB%93_Ch%C3%AD_Minh\" title=\"Hồ Chí Minh\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">Hồ Chí Minh</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>trong sự nghiệp cách mạng của ông được<span>&nbsp;</span></span><a href=\"https://vi.wikipedia.org/wiki/%C4%90%E1%BA%A3ng_C%E1%BB%99ng_s%E1%BA%A3n_Vi%E1%BB%87t_Nam\" title=\"Đảng Cộng sản Việt Nam\" style=\"text-decoration: none; color: rgb(51, 102, 204); background: none rgb(255, 255, 255); overflow-wrap: break-word; font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal;\">Đảng Cộng sản Việt Nam</a><span style=\"color: rgb(32, 33, 34); font-family: sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span>&nbsp;</span>tổng kết, hệ thống hóa.&nbsp;</span><br></p>");
        post10.setTeacherId(class1.getTeacherId());
        post10.setClassId(class1.getId());
        post10.setCreatedDate(new Date().getTime() + 3 * 86400000);
        post10.setId(postRepository.save(post10).getId());

        Post post11 = new Post();
        post11.setDescriptions("""
                GÓC FLEX 😀😅😂
                🥉🥉🥉Cuộc thi Game Việt Hackathon 2023 đang dần bước vào chung kết
                💯Góp mặt cùng với cuộc thi là đội thi FLEXCODE 💯💯💯đến từ bộ môn Ứng dụng phần mềm
                ⚡️⚡️Vượt qua hàng trăm đội thi khác trên cả nước, từ ngày lập đội, đến khi lên ý tưởng vào vòng bán kết và bây giờ chuẩn bị bước vào Chung kết trong khoảng thời gian 3 tháng. Đó là sự nỗ lực không ngừng nghỉ, chăm chỉ từ cả thầy và trò.
                👉👉👉 Nhờ sự hướng dẫn tận tình từ thầy cô BM UDPM cùng với sự tham gia của đội Flexcode bao gồm 5 thành viên 👨‍👩‍👦‍👦👨‍👨‍👦. Flexcode đã chính thức đưa UDPM flex khi lọt vào top14 cuộc thi gameviet hackathon 😍😍😍
                Cùng chúc mừng 🎊🎊tất cả các thành viên trong đội và cỗ vũ các bạn tiến bước chung kết🏅🏅🏅Không có giải không về 💪💪💪
                 """);
        post11.setTeacherId(class1.getTeacherId());
        post11.setClassId(class1.getId());
        post11.setCreatedDate(new Date().getTime() + 3 * 86400300);
        post11.setId(postRepository.save(post11).getId());

        // POINT - Class 1
        Point point1 = new Point();
        point1.setStudentId("71090C89-F618-41AE-2A8D-08DBB201EFE8".toLowerCase());
        point1.setClassId(class1.getId());
        point1.setCheckPointPhase1(8.0);
        point1.setCheckPointPhase2(5.0);
        point1.setFinalPoint(6.5);
        point1.setId(pointRepository.save(point1).getId());

        Point point2 = new Point();
        point2.setStudentId("2435C7D5-9BEC-45AC-9BFE-08DBA87523FE".toLowerCase());
        point2.setClassId(class1.getId());
        point2.setCheckPointPhase1(8.0);
        point2.setCheckPointPhase2(6.0);
        point2.setFinalPoint(7.0);
        point2.setId(pointRepository.save(point2).getId());

        Point point3 = new Point();
        point3.setStudentId("59F0CB47-5BD4-4909-B1C4-08DBB743DD7D".toLowerCase());
        point3.setClassId(class1.getId());
        point3.setCheckPointPhase1(8.0);
        point3.setCheckPointPhase2(6.0);
        point3.setFinalPoint(7.0);
        point3.setId(pointRepository.save(point3).getId());

        Point point4 = new Point();
        point4.setStudentId("F5882312-81A5-4D44-8E44-08DBB2F9FEB4".toLowerCase());
        point4.setClassId(class1.getId());
        point4.setCheckPointPhase1(2.0);
        point4.setCheckPointPhase2(2.0);
        point4.setFinalPoint(2.0);
        point4.setId(pointRepository.save(point4).getId());

        Point point5 = new Point();
        point5.setStudentId("80DE791C-F32B-4E7D-8E46-08DBB2F9FEB4".toLowerCase());
        point5.setClassId(class1.getId());
        point5.setCheckPointPhase1(1.0);
        point5.setCheckPointPhase2(5.0);
        point5.setFinalPoint(3.0);
        point5.setId(pointRepository.save(point5).getId());

        Point point6 = new Point();
        point6.setStudentId("2B5C2803-C998-4012-8E47-08DBB2F9FEB4".toLowerCase());
        point6.setClassId(class1.getId());
        point6.setCheckPointPhase1(8.0);
        point6.setCheckPointPhase2(8.0);
        point6.setFinalPoint(8.0);
        point6.setId(pointRepository.save(point6).getId());

        Point point7 = new Point();
        point7.setStudentId("FCB1D931-CB71-4F12-94D6-08DBB66B2F92".toLowerCase());
        point7.setClassId(class1.getId());
        point7.setCheckPointPhase1(5.0);
        point7.setCheckPointPhase2(5.0);
        point7.setFinalPoint(5.0);
        point7.setId(pointRepository.save(point7).getId());

        Point point8 = new Point();
        point8.setStudentId("6A85641C-874B-4AD0-B1BA-08DBB743DD7D".toLowerCase());
        point8.setClassId(class1.getId());
        point8.setCheckPointPhase1(7.0);
        point8.setCheckPointPhase2(7.0);
        point8.setFinalPoint(7.0);
        point8.setId(pointRepository.save(point8).getId());

        Point point9 = new Point();
        point9.setStudentId("B8E51E50-4823-4F9A-B1BC-08DBB743DD7D".toLowerCase());
        point9.setClassId(class1.getId());
        point9.setCheckPointPhase1(6.0);
        point9.setCheckPointPhase2(6.0);
        point9.setFinalPoint(6.0);
        point9.setId(pointRepository.save(point9).getId());

        Point point10 = new Point();
        point10.setStudentId("B34C613D-8AA5-4865-B1BD-08DBB743DD7D".toLowerCase());
        point10.setClassId(class1.getId());
        point10.setCheckPointPhase1(3.0);
        point10.setCheckPointPhase2(3.0);
        point10.setFinalPoint(3.0);
        point10.setId(pointRepository.save(point10).getId());

        Point point11 = new Point();
        point11.setStudentId("D3C53418-67EA-47FE-B1BE-08DBB743DD7D".toLowerCase());
        point11.setClassId(class1.getId());
        point11.setCheckPointPhase1(2.0);
        point11.setCheckPointPhase2(2.0);
        point11.setFinalPoint(2.0);
        point11.setId(pointRepository.save(point11).getId());

        Point point12 = new Point();
        point12.setStudentId("07E9748D-CF8C-4D48-B1BF-08DBB743DD7D".toLowerCase());
        point12.setClassId(class1.getId());
        point12.setCheckPointPhase1(1.0);
        point12.setCheckPointPhase2(1.0);
        point12.setFinalPoint(1.0);
        point12.setId(pointRepository.save(point12).getId());

        Point point13 = new Point();
        point13.setStudentId("967C6BB6-0F50-4862-B1C0-08DBB743DD7D".toLowerCase());
        point13.setClassId(class1.getId());
        point13.setCheckPointPhase1(5.0);
        point13.setCheckPointPhase2(5.0);
        point13.setFinalPoint(5.0);
        point13.setId(pointRepository.save(point13).getId());

        Point point14 = new Point();
        point14.setStudentId("1D566092-B2DD-49C6-B1C1-08DBB743DD7D".toLowerCase());
        point14.setClassId(class1.getId());
        point14.setCheckPointPhase1(5.0);
        point14.setCheckPointPhase2(5.5);
        point14.setFinalPoint(5.25);
        point14.setId(pointRepository.save(point14).getId());

        Point point15 = new Point();
        point15.setStudentId("8E0A1E2D-246F-49B7-B1C2-08DBB743DD7D".toLowerCase());
        point15.setClassId(class1.getId());
        point15.setCheckPointPhase1(6.5);
        point15.setCheckPointPhase2(5.5);
        point15.setFinalPoint(6.0);
        point15.setId(pointRepository.save(point15).getId());

        Point point16 = new Point();
        point16.setStudentId("69CE3C9F-5100-4A4F-B1C3-08DBB743DD7D".toLowerCase());
        point16.setClassId(class1.getId());
        point16.setCheckPointPhase1(7.5);
        point16.setCheckPointPhase2(5.5);
        point16.setFinalPoint(6.5);
        point16.setId(pointRepository.save(point16).getId());

        Point point17 = new Point();
        point17.setStudentId("33BC7EF4-F150-4D7D-B1C5-08DBB743DD7D".toLowerCase());
        point17.setClassId(class1.getId());
        point17.setCheckPointPhase1(2.5);
        point17.setCheckPointPhase2(5.5);
        point17.setFinalPoint(4.0);
        point17.setId(pointRepository.save(point17).getId());

        Point point18 = new Point();
        point18.setStudentId("5529080E-7F8B-49FC-B1C6-08DBB743DD7D".toLowerCase());
        point18.setClassId(class1.getId());
        point18.setCheckPointPhase1(10.0);
        point18.setCheckPointPhase2(10.0);
        point18.setFinalPoint(10.0);
        point18.setId(pointRepository.save(point18).getId());

        Point point19 = new Point();
        point19.setStudentId("39715979-9A4A-4F5B-B1C7-08DBB743DD7D".toLowerCase());
        point19.setClassId(class1.getId());
        point19.setCheckPointPhase1(9.5);
        point19.setCheckPointPhase2(5.5);
        point19.setFinalPoint(7.5);
        point19.setId(pointRepository.save(point19).getId());

        // POINT - Class 2 - teacher HangNT
        Point point20 = new Point();
        point20.setStudentId("C5715153-1C04-461D-B1C8-08DBB743DD7D".toLowerCase());
        point20.setClassId(class3.getId());
        point20.setCheckPointPhase1(8.0);
        point20.setCheckPointPhase2(8.0);
        point20.setFinalPoint(8.0);
        point20.setId(pointRepository.save(point20).getId());

        // POINT - class 3 - teacher NguyenVV
        Point point21 = new Point();
        point21.setStudentId("09B3E4C8-0E0F-4F03-B1C9-08DBB743DD7D".toLowerCase());
        point21.setClassId(class2.getId());
        point21.setCheckPointPhase1(9.0);
        point21.setCheckPointPhase2(9.0);
        point21.setFinalPoint(9.0);
        point21.setId(pointRepository.save(point21).getId());

        ClassConfiguration classConfiguration = new ClassConfiguration();
        classConfiguration.setClassSizeMax(25);
        classConfiguration.setClassSizeMin(15);
        classConfiguration.setPointMin(5D);
        classConfiguration.setMaximumNumberOfBreaks(20D);
        classConfigurationRepository.save(classConfiguration);

        ////////////////////////////////////////

        Category category1 = new Category();
        category1.setCode("Cate_1");
        category1.setName("Phát triển game");
        category1.setId(categoryRepository.save(category1).getId());

        Category category2 = new Category();
        category2.setCode("Cate_2");
        category2.setName("Phát triển web");
        category2.setId(categoryRepository.save(category2).getId());

        Category category3 = new Category();
        category3.setCode("Cate_3");
        category3.setName("Phát triển app");
        category3.setId(categoryRepository.save(category3).getId());

        Label label1 = new Label();
        label1.setCode("Bug");
        label1.setName("Lỗi");
        label1.setColorLabel(Constants.COLOR_FF4500);
        label1.setId(labelRepository.save(label1).getId());

        Label label2 = new Label();
        label2.setCode("Feature");
        label2.setName("Tính năng");
        label2.setColorLabel(Constants.COLOR_47799C);
        label2.setId(labelRepository.save(label2).getId());

        Label label3 = new Label();
        label3.setCode("Enhancement");
        label3.setName("Cải tiến");
        label3.setColorLabel(Constants.COLOR_FA8072);
        label3.setId(labelRepository.save(label3).getId());

        Label label4 = new Label();
        label4.setCode("Design");
        label4.setName("Thiết kế");
        label4.setColorLabel(Constants.COLOR_ADFF2F);
        label4.setId(labelRepository.save(label4).getId());

        Label label5 = new Label();
        label5.setCode("Marketing");
        label5.setName("Marketing");
        label5.setColorLabel(Constants.COLOR_7AA1E4);
        label5.setId(labelRepository.save(label5).getId());

        Label label6 = new Label();
        label6.setCode("Content");
        label6.setName("Content");
        label6.setColorLabel(Constants.COLOR_FFA500);
        label6.setId(labelRepository.save(label6).getId());

        Label label7 = new Label();
        label7.setCode("Research");
        label7.setName("Nghiên cứu");
        label7.setColorLabel(Constants.COLOR_FFD700);
        label7.setId(labelRepository.save(label7).getId());

        Label label8 = new Label();
        label8.setCode("Infrastructure");
        label8.setName("Hạ tầng");
        label8.setColorLabel(Constants.COLOR_FF6347);
        label8.setId(labelRepository.save(label8).getId());

        Label label9 = new Label();
        label9.setCode("Documentation");
        label9.setName("Tài liệu");
        label9.setColorLabel(Constants.COLOR_FFFF00);
        label9.setId(labelRepository.save(label9).getId());

        Label label10 = new Label();
        label10.setCode("Support");
        label10.setName("Hỗ trợ");
        label10.setColorLabel(Constants.COLOR_EE82EE);
        label10.setId(labelRepository.save(label10).getId());

        LabelProject labelProject1 = new LabelProject();
        labelProject1.setCode(label1.getCode());
        labelProject1.setName(label1.getName());
        labelProject1.setColorLabel(label1.getColorLabel());
        labelProject1.setProjectId(project1.getId());
        labelProject1.setId((labelProjectRepository.save(labelProject1).getId()));

        LabelProject labelProject2 = new LabelProject();
        labelProject2.setCode(label2.getCode());
        labelProject2.setName(label2.getName());
        labelProject2.setColorLabel(label2.getColorLabel());
        labelProject2.setProjectId(project1.getId());
        labelProject2.setId((labelProjectRepository.save(labelProject2).getId()));

        LabelProject labelProject3 = new LabelProject();
        labelProject3.setCode(label3.getCode());
        labelProject3.setName(label3.getName());
        labelProject3.setColorLabel(label3.getColorLabel());
        labelProject3.setProjectId(project1.getId());
        labelProject3.setId((labelProjectRepository.save(labelProject3).getId()));

        LabelProject labelProject4 = new LabelProject();
        labelProject4.setCode(label4.getCode());
        labelProject4.setName(label4.getName());
        labelProject4.setColorLabel(label4.getColorLabel());
        labelProject4.setProjectId(project1.getId());
        labelProject4.setId((labelProjectRepository.save(labelProject4).getId()));

        LabelProject labelProject5 = new LabelProject();
        labelProject5.setCode(label5.getCode());
        labelProject5.setName(label5.getName());
        labelProject5.setColorLabel(label5.getColorLabel());
        labelProject5.setProjectId(project1.getId());
        labelProject5.setId((labelProjectRepository.save(labelProject5).getId()));

        LabelProject labelProject6 = new LabelProject();
        labelProject6.setCode(label6.getCode());
        labelProject6.setName(label6.getName());
        labelProject6.setColorLabel(label6.getColorLabel());
        labelProject6.setProjectId(project1.getId());
        labelProject6.setId((labelProjectRepository.save(labelProject6).getId()));

        LabelProject labelProject7 = new LabelProject();
        labelProject7.setCode(label7.getCode());
        labelProject7.setName(label7.getName());
        labelProject7.setColorLabel(label7.getColorLabel());
        labelProject7.setProjectId(project1.getId());
        labelProject7.setId((labelProjectRepository.save(labelProject7).getId()));

        LabelProject labelProject8 = new LabelProject();
        labelProject8.setCode(label8.getCode());
        labelProject8.setName(label8.getName());
        labelProject8.setColorLabel(label8.getColorLabel());
        labelProject8.setProjectId(project1.getId());
        labelProject8.setId((labelProjectRepository.save(labelProject8).getId()));

        LabelProject labelProject9 = new LabelProject();
        labelProject9.setCode(label9.getCode());
        labelProject9.setName(label9.getName());
        labelProject9.setColorLabel(label9.getColorLabel());
        labelProject9.setProjectId(project1.getId());
        labelProject9.setId((labelProjectRepository.save(labelProject9).getId()));

        LabelProject labelProject10 = new LabelProject();
        labelProject10.setCode(label10.getCode());
        labelProject10.setName(label10.getName());
        labelProject10.setColorLabel(label10.getColorLabel());
        labelProject10.setProjectId(project1.getId());
        labelProject10.setId((labelProjectRepository.save(labelProject10).getId()));

        TodoList todoList1 = new TodoList();
        todoList1.setCode("TodoList_1");
        todoList1.setName("VIỆC CẦN LÀM");
        todoList1.setProjectId(project1.getId());
        todoList1.setIndexTodoList(Byte.parseByte("0"));
        todoList1.setId((todoListRepository.save(todoList1).getId()));

        TodoList todoList2 = new TodoList();
        todoList2.setCode("TodoList_2");
        todoList2.setProjectId(project1.getId());
        todoList2.setName("VIỆC ĐANG LÀM");
        todoList2.setIndexTodoList(Byte.parseByte("1"));
        todoList2.setId((todoListRepository.save(todoList2).getId()));

        TodoList todoList3 = new TodoList();
        todoList3.setCode("TodoList_3");
        todoList3.setProjectId(project1.getId());
        todoList3.setName("CẦN SỬA");
        todoList3.setIndexTodoList(Byte.parseByte("2"));
        todoList3.setId((todoListRepository.save(todoList3).getId()));

        TodoList todoList4 = new TodoList();
        todoList4.setCode("TodoList_4");
        todoList4.setName("CẦN ĐÁNH GIÁ");
        todoList4.setProjectId(project1.getId());
        todoList4.setIndexTodoList(Byte.parseByte("3"));
        todoList4.setId((todoListRepository.save(todoList4).getId()));

        TodoList todoList5 = new TodoList();
        todoList5.setCode("TodoList_5");
        todoList5.setName("ĐÃ HOÀN THÀNH");
        todoList5.setProjectId(project1.getId());
        todoList5.setIndexTodoList(Byte.parseByte("4"));
        todoList5.setId((todoListRepository.save(todoList5).getId()));

        TodoList todoList6 = new TodoList();
        todoList6.setCode("TodoList_6");
        todoList6.setName("TẠM HOÃN");
        todoList6.setProjectId(project1.getId());
        todoList6.setIndexTodoList(Byte.parseByte("5"));
        todoList6.setId((todoListRepository.save(todoList6).getId()));

        ProjectCategory projectCategory1 = new ProjectCategory();
        projectCategory1.setCategoryId(category2.getId());
        projectCategory1.setProjectId(project1.getId());
        projectCategory1.setId(projectCategoryRepository.save(projectCategory1).getId());

        ProjectCategory projectCategory2 = new ProjectCategory();
        projectCategory2.setCategoryId(category1.getId());
        projectCategory2.setProjectId(project1.getId());
        projectCategory2.setId(projectCategoryRepository.save(projectCategory2).getId());

        //  project 1 - team 1
        MemberProject memberProject1 = new MemberProject();
        memberProject1.setMemberId("f5882312-81a5-4d44-8e44-08dbb2f9feb4".toLowerCase());
        memberProject1.setProjectId(project1.getId());
        memberProject1.setEmail("quynhncph26201@fpt.edu.vn");
        memberProject1.setRole(RoleMemberProject.MANAGER);
        memberProject1.setStatusWork(StatusWork.DANG_LAM);
        memberProject1.setId(memberProjectRepository.save(memberProject1).getId());

        MemberProject memberProject2 = new MemberProject();
        memberProject2.setMemberId("80de791c-f32b-4e7d-8e46-08dbb2f9feb4".toLowerCase());
        memberProject2.setProjectId(project1.getId());
        memberProject2.setEmail("hieundph26058@fpt.edu.vn");
        memberProject2.setRole(RoleMemberProject.LEADER);
        memberProject2.setStatusWork(StatusWork.DANG_LAM);
        memberProject2.setId(memberProjectRepository.save(memberProject2).getId());

        MemberProject memberProject3 = new MemberProject();
        memberProject3.setMemberId("fcb1d931-cb71-4f12-94d6-08dbb66b2f92".toLowerCase());
        memberProject3.setProjectId(project1.getId());
        memberProject3.setEmail("huynqph26772@fpt.edu.vn");
        memberProject3.setRole(RoleMemberProject.DEV);
        memberProject3.setStatusWork(StatusWork.DANG_LAM);
        memberProject3.setId(memberProjectRepository.save(memberProject3).getId());

        MemberProject memberProject4 = new MemberProject();
        memberProject4.setMemberId("59f0cb47-5bd4-4909-b1c4-08dbb743dd7d".toLowerCase());
        memberProject4.setProjectId(project1.getId());
        memberProject4.setEmail("vanntph19604@fpt.edu.vn");
        memberProject4.setRole(RoleMemberProject.TESTER);
        memberProject4.setStatusWork(StatusWork.DANG_LAM);
        memberProject4.setId(memberProjectRepository.save(memberProject4).getId());

        MemberProject memberProject5 = new MemberProject();
        memberProject5.setMemberId("2b5c2803-c998-4012-8e47-08dbb2f9feb4".toLowerCase());
        memberProject5.setProjectId(project1.getId());
        memberProject5.setEmail("vinhnvph23845@fpt.edu.vn");
        memberProject5.setRole(RoleMemberProject.DEV);
        memberProject5.setStatusWork(StatusWork.DANG_LAM);
        memberProject5.setId(memberProjectRepository.save(memberProject5).getId());

        // project 2 - team 2
        MemberProject memberProject6 = new MemberProject();
        memberProject6.setMemberId("71090C89-F618-41AE-2A8D-08DBB201EFE8".toLowerCase());
        memberProject6.setProjectId(project2.getId());
        memberProject6.setEmail("hieundph25894@fpt.edu.vn");
        memberProject6.setRole(RoleMemberProject.MANAGER);
        memberProject6.setStatusWork(StatusWork.DANG_LAM);
        memberProject6.setId(memberProjectRepository.save(memberProject6).getId());

        MemberProject memberProject7 = new MemberProject();
        memberProject7.setMemberId("2435C7D5-9BEC-45AC-9BFE-08DBA87523FE".toLowerCase());
        memberProject7.setProjectId(project2.getId());
        memberProject7.setEmail("thangncph26123@fpt.edu.vn");
        memberProject7.setRole(RoleMemberProject.LEADER);
        memberProject7.setStatusWork(StatusWork.DANG_LAM);
        memberProject7.setId(memberProjectRepository.save(memberProject7).getId());

        MemberProject memberProject8 = new MemberProject();
        memberProject8.setMemberId("6A85641C-874B-4AD0-B1BA-08DBB743DD7D".toLowerCase());
        memberProject8.setProjectId(project2.getId());
        memberProject8.setEmail("hatqph21186@fpt.edu.vn");
        memberProject8.setRole(RoleMemberProject.DEV);
        memberProject8.setStatusWork(StatusWork.DANG_LAM);
        memberProject8.setId(memberProjectRepository.save(memberProject8).getId());

        MemberProject memberProject9 = new MemberProject();
        memberProject9.setMemberId("B8E51E50-4823-4F9A-B1BC-08DBB743DD7D".toLowerCase());
        memberProject9.setProjectId(project2.getId());
        memberProject9.setEmail("nhatnvph26159@fpt.edu.vn");
        memberProject9.setRole(RoleMemberProject.TESTER);
        memberProject9.setStatusWork(StatusWork.DANG_LAM);
        memberProject9.setId(memberProjectRepository.save(memberProject9).getId());

        MemberProject memberProject10 = new MemberProject();
        memberProject10.setMemberId("B34C613D-8AA5-4865-B1BD-08DBB743DD7D".toLowerCase());
        memberProject10.setProjectId(project2.getId());
        memberProject10.setEmail("tuannvph25577@fpt.edu.vn");
        memberProject10.setRole(RoleMemberProject.DEV);
        memberProject10.setStatusWork(StatusWork.DANG_LAM);
        memberProject10.setId(memberProjectRepository.save(memberProject10).getId());

        // project 3
        MemberProject memberProject11 = new MemberProject();
        memberProject11.setMemberId("d3c53418-67ea-47fe-b1be-08dbb743dd7d".toLowerCase());
        memberProject11.setProjectId(project3.getId());
        memberProject11.setEmail("anhdtnph25326@fpt.edu.vn");
        memberProject11.setRole(RoleMemberProject.MANAGER);
        memberProject11.setStatusWork(StatusWork.DANG_LAM);
        memberProject11.setId(memberProjectRepository.save(memberProject11).getId());

        MemberProject memberProject12 = new MemberProject();
        memberProject12.setMemberId("07e9748d-cf8c-4d48-b1bf-08dbb743dd7d".toLowerCase());
        memberProject12.setProjectId(project3.getId());
        memberProject12.setEmail("trangntph19494@fpt.edu.vn");
        memberProject12.setRole(RoleMemberProject.LEADER);
        memberProject12.setStatusWork(StatusWork.DANG_LAM);
        memberProject12.setId(memberProjectRepository.save(memberProject12).getId());

        MemberProject memberProject13 = new MemberProject();
        memberProject13.setMemberId("8e0a1e2d-246f-49b7-b1c2-08dbb743dd7d".toLowerCase());
        memberProject13.setProjectId(project3.getId());
        memberProject13.setEmail("hoangdvph25902@fpt.edu.vn");
        memberProject13.setRole(RoleMemberProject.DEV);
        memberProject13.setStatusWork(StatusWork.DANG_LAM);
        memberProject13.setId(memberProjectRepository.save(memberProject13).getId());

        MemberProject memberProject14 = new MemberProject();
        memberProject14.setMemberId("967C6BB6-0F50-4862-B1C0-08DBB743DD7D".toLowerCase());
        memberProject14.setProjectId(project3.getId());
        memberProject14.setEmail("huyvqph25924@fpt.edu.vn");
        memberProject14.setRole(RoleMemberProject.TESTER);
        memberProject14.setStatusWork(StatusWork.DANG_LAM);
        memberProject14.setId(memberProjectRepository.save(memberProject14).getId());

        MemberProject memberProject15 = new MemberProject();
        memberProject15.setMemberId("1d566092-b2dd-49c6-b1c1-08dbb743dd7d".toLowerCase());
        memberProject15.setProjectId(project3.getId());
        memberProject15.setEmail("hungpvph25929@fpt.edu.vn");
        memberProject15.setRole(RoleMemberProject.DEV);
        memberProject15.setStatusWork(StatusWork.DANG_LAM);
        memberProject15.setId(memberProjectRepository.save(memberProject15).getId());


        Period period1 = new Period();
        period1.setCode("period_1");
        period1.setName("Giai đoạn thiết kế database");
        period1.setStartTime(1678294800000L);
        period1.setEndTime(1682355600000L);
        period1.setTarget("Hoàn thiện khung database");
        period1.setProgress(Float.parseFloat("0"));
        period1.setStatusPeriod(StatusPeriod.DANG_DIEN_RA);
        period1.setDescriptions("Giai đoạn quan trọng 1");
        period1.setProjectId(project1.getId());
        period1.setId(periodRepository.save(period1).getId());

        Period period2 = new Period();
        period2.setCode("period_2");
        period2.setName("Giai đoạn thiết kế giao diện");
        period2.setStartTime(1682355600000L);
        period2.setEndTime(1685379600000L);
        period2.setTarget("Hoàn thiện khung database");
        period2.setProgress(Float.parseFloat("0"));
        period2.setStatusPeriod(StatusPeriod.CHUA_DIEN_RA);
        period2.setDescriptions("Giai đoạn quan trọng 2");
        period2.setProjectId(project1.getId());
        period2.setId(periodRepository.save(period2).getId());

        Todo todo1 = new Todo();
        todo1.setCode("Todo_1");
        todo1.setName("CRUD bảng category");
        todo1.setProgress((short) 0);
        todo1.setDescriptions("Không có mô tả");
        todo1.setDeadline(1679677200000L);
        todo1.setCompletionTime(null);
        todo1.setTodoListId(todoList1.getId());
        todo1.setTodoId(null);
        todo1.setIndexTodo((short) 0);
        todo1.setPriorityLevel(PriorityLevel.QUAN_TRONG);
        todo1.setType(TypeTodo.CONG_VIEC);
        todo1.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo1.setId(todoRepository.save(todo1).getId());

        Todo todo2 = new Todo();
        todo2.setCode("Todo_2");
        todo2.setName("CRUD bảng label");
        todo2.setProgress((short) 0);
        todo2.setDescriptions("Không có mô tả");
        todo2.setDeadline(1679677200000L);
        todo2.setTodoListId(todoList1.getId());
        todo2.setCompletionTime(null);
        todo2.setIndexTodo((short) 1);
        todo2.setTodoId(null);
        todo2.setPriorityLevel(PriorityLevel.CAO);
        todo2.setType(TypeTodo.CONG_VIEC);
        todo2.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo2.setId(todoRepository.save(todo2).getId());

        Todo todo3 = new Todo();
        todo3.setCode("Todo_3");
        todo3.setName("Làm tài liệu đặc tả");
        todo3.setProgress((short) 0);
        todo3.setDescriptions("Không có mô tả");
        todo3.setDeadline(1679677200000L);
        todo3.setTodoListId(todoList1.getId());
        todo3.setCompletionTime(null);
        todo3.setIndexTodo((short) 2);
        todo3.setTodoId(null);
        todo3.setPriorityLevel(PriorityLevel.TRUNG_BINH);
        todo3.setType(TypeTodo.CONG_VIEC);
        todo3.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo3.setId(todoRepository.save(todo3).getId());

        Todo todo4 = new Todo();
        todo4.setCode("Todo_4");
        todo4.setName("Làm mình làm mẩy");
        todo4.setProgress((short) 0);
        todo4.setDescriptions("Không có mô tả");
        todo4.setDeadline(1679677200000L);
        todo4.setCompletionTime(null);
        todo4.setTodoListId(todoList2.getId());
        todo4.setIndexTodo((short) 0);
        todo4.setTodoId(null);
        todo4.setPriorityLevel(PriorityLevel.THAP);
        todo4.setType(TypeTodo.CONG_VIEC);
        todo4.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo4.setId(todoRepository.save(todo4).getId());

        Todo todo5 = new Todo();
        todo5.setCode("Todo_5");
        todo5.setName("Phân quyền");
        todo5.setProgress((short) 0);
        todo5.setDescriptions("Không có mô tả");
        todo5.setDeadline(1679677200000L);
        todo5.setTodoListId(todoList2.getId());
        todo5.setIndexTodo((short) 1);
        todo5.setCompletionTime(null);
        todo5.setTodoId(null);
        todo5.setType(TypeTodo.CONG_VIEC);
        todo5.setPriorityLevel(PriorityLevel.CAO);
        todo5.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo5.setId(todoRepository.save(todo5).getId());

        Todo todo6 = new Todo();
        todo6.setCode("Todo_6");
        todo6.setName("Đăng nhập");
        todo6.setProgress((short) 0);
        todo6.setDescriptions("");
        todo6.setDeadline(1679677200000L);
        todo6.setTodoListId(todoList2.getId());
        todo6.setIndexTodo((short) 2);
        todo6.setCompletionTime(null);
        todo6.setTodoId(null);
        todo6.setType(TypeTodo.CONG_VIEC);
        todo6.setPriorityLevel(null);
        todo6.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo6.setId(todoRepository.save(todo6).getId());

        Todo todo7 = new Todo();
        todo7.setCode("Todo_7");
        todo7.setName("CRUD các đầu việc");
        todo7.setProgress((short) 33);
        todo7.setDescriptions("Không có mô tả");
        todo7.setDeadline(1679677200000L);
        todo7.setTodoListId(todoList2.getId());
        todo7.setIndexTodo((short) 3);
        todo7.setCompletionTime(null);
        todo7.setTodoId(null);
        todo7.setType(TypeTodo.CONG_VIEC);
        todo7.setPriorityLevel(PriorityLevel.QUAN_TRONG);
        todo7.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo7.setId(todoRepository.save(todo7).getId());

        Todo todo7_2 = new Todo();
        todo7_2.setCode("Todo_7_2");
        todo7_2.setName("Sửa đầu việc");
        todo7_2.setTodoId(todo7.getId());
        todo7_2.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo7_2.setId(todoRepository.save(todo7_2).getId());

        Todo todo7_1 = new Todo();
        todo7_1.setCode("Todo_7_1");
        todo7_1.setName("Thêm đầu việc");
        todo7_1.setTodoId(todo7.getId());
        todo7_1.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo7_1.setId(todoRepository.save(todo7_1).getId());

        Todo todo7_3 = new Todo();
        todo7_3.setCode("Todo_7_2");
        todo7_3.setName("Xem đầu việc");
        todo7_3.setStatusTodo(StatusTodo.DA_HOAN_THANH);
        todo7_3.setTodoId(todo7.getId());
        todo7_3.setId(todoRepository.save(todo7_3).getId());

        Todo todo8 = new Todo();
        todo8.setCode("Todo_8");
        todo8.setName("Đăng ký");
        todo8.setProgress((short) 0);
        todo8.setDescriptions("Không có mô tả");
        todo8.setDeadline(1679677200000L);
        todo8.setTodoId(null);
        todo8.setCompletionTime(null);
        todo8.setTodoListId(todoList2.getId());
        todo8.setIndexTodo((short) 4);
        todo8.setType(TypeTodo.CONG_VIEC);
        todo8.setPriorityLevel(PriorityLevel.CAO);
        todo8.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo8.setId(todoRepository.save(todo8).getId());

        Todo todo9 = new Todo();
        todo9.setCode("Todo_9");
        todo9.setName("Đọc ghi file excel");
        todo9.setProgress((short) 0);
        todo9.setDescriptions("Không có mô tả");
        todo9.setDeadline(1679677200000L);
        todo9.setTodoId(null);
        todo9.setTodoListId(todoList2.getId());
        todo9.setIndexTodo((short) 5);
        todo9.setCompletionTime(null);
        todo9.setType(TypeTodo.CONG_VIEC);
        todo9.setPriorityLevel(PriorityLevel.TRUNG_BINH);
        todo9.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo9.setId(todoRepository.save(todo9).getId());

        Todo todo10 = new Todo();
        todo10.setCode("Todo_10");
        todo10.setName("Gửi mail tự động");
        todo10.setProgress((short) 0);
        todo10.setDescriptions("Không có mô tả");
        todo10.setDeadline(1679677200000L);
        todo10.setTodoId(null);
        todo10.setTodoListId(todoList2.getId());
        todo10.setIndexTodo((short) 6);
        todo10.setCompletionTime(null);
        todo10.setType(TypeTodo.CONG_VIEC);
        todo10.setPriorityLevel(PriorityLevel.THAP);
        todo10.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo10.setId(todoRepository.save(todo10).getId());

        PeriodTodo periodTodo1 = new PeriodTodo();
        periodTodo1.setPeriodId(period1.getId());
        periodTodo1.setTodoId(todo1.getId());
        periodTodo1.setId(periodTodoRepository.save(periodTodo1).getId());

        PeriodTodo periodTodo2 = new PeriodTodo();
        periodTodo2.setPeriodId(period1.getId());
        periodTodo2.setTodoId(todo2.getId());
        periodTodo2.setId(periodTodoRepository.save(periodTodo2).getId());

        PeriodTodo periodTodo3 = new PeriodTodo();
        periodTodo3.setPeriodId(period1.getId());
        periodTodo3.setTodoId(todo3.getId());
        periodTodo3.setId(periodTodoRepository.save(periodTodo3).getId());

        PeriodTodo periodTodo4 = new PeriodTodo();
        periodTodo4.setPeriodId(period1.getId());
        periodTodo4.setTodoId(todo4.getId());
        periodTodo4.setId(periodTodoRepository.save(periodTodo4).getId());

        PeriodTodo periodTodo5 = new PeriodTodo();
        periodTodo5.setPeriodId(period1.getId());
        periodTodo5.setTodoId(todo5.getId());
        periodTodo5.setId(periodTodoRepository.save(periodTodo5).getId());

        PeriodTodo periodTodo6 = new PeriodTodo();
        periodTodo6.setPeriodId(period1.getId());
        periodTodo6.setTodoId(todo6.getId());
        periodTodo6.setId(periodTodoRepository.save(periodTodo6).getId());

        PeriodTodo periodTodo7 = new PeriodTodo();
        periodTodo7.setPeriodId(period1.getId());
        periodTodo7.setTodoId(todo7.getId());
        periodTodo7.setId(periodTodoRepository.save(periodTodo7).getId());

        PeriodTodo periodTodo8 = new PeriodTodo();
        periodTodo8.setPeriodId(period1.getId());
        periodTodo8.setTodoId(todo8.getId());
        periodTodo8.setId(periodTodoRepository.save(periodTodo8).getId());

        PeriodTodo periodTodo9 = new PeriodTodo();
        periodTodo9.setPeriodId(period1.getId());
        periodTodo9.setTodoId(todo9.getId());
        periodTodo9.setId(periodTodoRepository.save(periodTodo9).getId());

        PeriodTodo periodTodo10 = new PeriodTodo();
        periodTodo10.setPeriodId(period1.getId());
        periodTodo10.setTodoId(todo10.getId());
        periodTodo10.setId(periodTodoRepository.save(periodTodo10).getId());

        LabelProjectTodo labelTodo1 = new LabelProjectTodo();
        labelTodo1.setTodoId(todo1.getId());
        labelTodo1.setLabelProjectId(labelProject2.getId());
        labelTodo1.setId(labelTodoRepository.save(labelTodo1).getId());

        LabelProjectTodo labelTodo2 = new LabelProjectTodo();
        labelTodo2.setTodoId(todo2.getId());
        labelTodo2.setLabelProjectId(labelProject4.getId());
        labelTodo2.setId(labelTodoRepository.save(labelTodo2).getId());

        LabelProjectTodo labelTodo3 = new LabelProjectTodo();
        labelTodo3.setTodoId(todo1.getId());
        labelTodo3.setLabelProjectId(labelProject5.getId());
        labelTodo3.setId(labelTodoRepository.save(labelTodo3).getId());

        LabelProjectTodo labelTodo4 = new LabelProjectTodo();
        labelTodo4.setTodoId(todo3.getId());
        labelTodo4.setLabelProjectId(labelProject9.getId());
        labelTodo4.setId(labelTodoRepository.save(labelTodo4).getId());

        LabelProjectTodo labelTodo5 = new LabelProjectTodo();
        labelTodo5.setTodoId(todo4.getId());
        labelTodo5.setLabelProjectId(labelProject1.getId());
        labelTodo5.setId(labelTodoRepository.save(labelTodo5).getId());

        LabelProjectTodo labelTodo6 = new LabelProjectTodo();
        labelTodo6.setTodoId(todo5.getId());
        labelTodo6.setLabelProjectId(labelProject4.getId());
        labelTodo6.setId(labelTodoRepository.save(labelTodo6).getId());

        LabelProjectTodo labelTodo7 = new LabelProjectTodo();
        labelTodo7.setTodoId(todo6.getId());
        labelTodo7.setLabelProjectId(labelProject3.getId());
        labelTodo7.setId(labelTodoRepository.save(labelTodo7).getId());

        LabelProjectTodo labelTodo8 = new LabelProjectTodo();
        labelTodo8.setTodoId(todo7.getId());
        labelTodo8.setLabelProjectId(labelProject5.getId());
        labelTodo8.setId(labelTodoRepository.save(labelTodo8).getId());

        LabelProjectTodo labelTodo9 = new LabelProjectTodo();
        labelTodo9.setTodoId(todo8.getId());
        labelTodo9.setLabelProjectId(labelProject7.getId());
        labelTodo9.setId(labelTodoRepository.save(labelTodo9).getId());

        LabelProjectTodo labelTodo10 = new LabelProjectTodo();
        labelTodo10.setTodoId(todo9.getId());
        labelTodo10.setLabelProjectId(labelProject2.getId());
        labelTodo10.setId(labelTodoRepository.save(labelTodo10).getId());

        LabelProjectTodo labelTodo11 = new LabelProjectTodo();
        labelTodo11.setTodoId(todo10.getId());
        labelTodo11.setLabelProjectId(labelProject8.getId());
        labelTodo11.setId(labelTodoRepository.save(labelTodo11).getId());

        LabelProjectTodo labelTodo12 = new LabelProjectTodo();
        labelTodo12.setTodoId(todo7.getId());
        labelTodo12.setLabelProjectId(labelProject8.getId());
        labelTodo12.setId(labelTodoRepository.save(labelTodo12).getId());

        LabelProjectTodo labelTodo13 = new LabelProjectTodo();
        labelTodo13.setTodoId(todo8.getId());
        labelTodo13.setLabelProjectId(labelProject5.getId());
        labelTodo13.setId(labelTodoRepository.save(labelTodo13).getId());

        LabelProjectTodo labelTodo14 = new LabelProjectTodo();
        labelTodo14.setTodoId(todo7.getId());
        labelTodo14.setLabelProjectId(labelProject1.getId());
        labelTodo14.setId(labelTodoRepository.save(labelTodo14).getId());

        LabelProjectTodo labelTodo15 = new LabelProjectTodo();
        labelTodo15.setTodoId(todo7.getId());
        labelTodo15.setLabelProjectId(labelProject2.getId());
        labelTodo15.setId(labelTodoRepository.save(labelTodo15).getId());

        LabelProjectTodo labelTodo16 = new LabelProjectTodo();
        labelTodo16.setTodoId(todo7.getId());
        labelTodo16.setLabelProjectId(labelProject3.getId());
        labelTodo16.setId(labelTodoRepository.save(labelTodo16).getId());

        LabelProjectTodo labelTodo17 = new LabelProjectTodo();
        labelTodo17.setTodoId(todo7.getId());
        labelTodo17.setLabelProjectId(labelProject4.getId());
        labelTodo17.setId(labelTodoRepository.save(labelTodo17).getId());

        LabelProjectTodo labelTodo18 = new LabelProjectTodo();
        labelTodo18.setTodoId(todo7.getId());
        labelTodo18.setLabelProjectId(labelProject6.getId());
        labelTodo18.setId(labelTodoRepository.save(labelTodo18).getId());


        LabelProject labelProject1_2 = new LabelProject();
        labelProject1_2.setCode(label1.getCode());
        labelProject1_2.setName(label1.getName());
        labelProject1_2.setColorLabel(label1.getColorLabel());
        labelProject1_2.setProjectId(project2.getId());
        labelProject1_2.setId((labelProjectRepository.save(labelProject1_2).getId()));

        LabelProject labelProject2_2 = new LabelProject();
        labelProject2_2.setCode(label2.getCode());
        labelProject2_2.setName(label2.getName());
        labelProject2_2.setColorLabel(label2.getColorLabel());
        labelProject2_2.setProjectId(project2.getId());
        labelProject2_2.setId((labelProjectRepository.save(labelProject2_2).getId()));

        LabelProject labelProject3_2 = new LabelProject();
        labelProject3_2.setCode(label3.getCode());
        labelProject3_2.setName(label3.getName());
        labelProject3_2.setColorLabel(label3.getColorLabel());
        labelProject3_2.setProjectId(project2.getId());
        labelProject3_2.setId((labelProjectRepository.save(labelProject3_2).getId()));

        LabelProject labelProject4_2 = new LabelProject();
        labelProject4_2.setCode(label4.getCode());
        labelProject4_2.setName(label4.getName());
        labelProject4_2.setColorLabel(label4.getColorLabel());
        labelProject4_2.setProjectId(project2.getId());
        labelProject4_2.setId((labelProjectRepository.save(labelProject4_2).getId()));

        LabelProject labelProject5_2 = new LabelProject();
        labelProject5_2.setCode(label5.getCode());
        labelProject5_2.setName(label5.getName());
        labelProject5_2.setColorLabel(label5.getColorLabel());
        labelProject5_2.setProjectId(project2.getId());
        labelProject5_2.setId((labelProjectRepository.save(labelProject5_2).getId()));

        LabelProject labelProject6_2 = new LabelProject();
        labelProject6_2.setCode(label6.getCode());
        labelProject6_2.setName(label6.getName());
        labelProject6_2.setColorLabel(label6.getColorLabel());
        labelProject6_2.setProjectId(project2.getId());
        labelProject6_2.setId((labelProjectRepository.save(labelProject6_2).getId()));

        LabelProject labelProject7_2 = new LabelProject();
        labelProject7_2.setCode(label7.getCode());
        labelProject7_2.setName(label7.getName());
        labelProject7_2.setColorLabel(label7.getColorLabel());
        labelProject7_2.setProjectId(project2.getId());
        labelProject7_2.setId((labelProjectRepository.save(labelProject7_2).getId()));

        LabelProject labelProject8_2 = new LabelProject();
        labelProject8_2.setCode(label8.getCode());
        labelProject8_2.setName(label8.getName());
        labelProject8_2.setColorLabel(label8.getColorLabel());
        labelProject8_2.setProjectId(project2.getId());
        labelProject8_2.setId((labelProjectRepository.save(labelProject8_2).getId()));

        LabelProject labelProject9_2 = new LabelProject();
        labelProject9_2.setCode(label9.getCode());
        labelProject9_2.setName(label9.getName());
        labelProject9_2.setColorLabel(label9.getColorLabel());
        labelProject9_2.setProjectId(project2.getId());
        labelProject9_2.setId((labelProjectRepository.save(labelProject9_2).getId()));

        LabelProject labelProject10_2 = new LabelProject();
        labelProject10_2.setCode(label10.getCode());
        labelProject10_2.setName(label10.getName());
        labelProject10_2.setColorLabel(label10.getColorLabel());
        labelProject10_2.setProjectId(project2.getId());
        labelProject10_2.setId((labelProjectRepository.save(labelProject10_2).getId()));

        TodoList todoList1_2 = new TodoList();
        todoList1_2.setCode("TodoList_1");
        todoList1_2.setName("VIỆC CẦN LÀM");
        todoList1_2.setProjectId(project2.getId());
        todoList1_2.setIndexTodoList(Byte.parseByte("0"));
        todoList1_2.setId((todoListRepository.save(todoList1_2).getId()));

        TodoList todoList2_2 = new TodoList();
        todoList2_2.setCode("TodoList_2");
        todoList2_2.setProjectId(project2.getId());
        todoList2_2.setName("VIỆC ĐANG LÀM");
        todoList2_2.setIndexTodoList(Byte.parseByte("1"));
        todoList2_2.setId((todoListRepository.save(todoList2_2).getId()));

        ProjectCategory projectCategory1_1 = new ProjectCategory();
        projectCategory1_1.setCategoryId(category2.getId());
        projectCategory1_1.setProjectId(project2.getId());
        projectCategory1_1.setId(projectCategoryRepository.save(projectCategory1_1).getId());

//        MemberProject memberProject1_2 = new MemberProject();
//        memberProject1_2.setMemberId("c5cf1e20-bdd4-11ed-afa1-0242ac120002");
//        memberProject1_2.setProjectId(project2.getId());
//        memberProject1_2.setRole(RoleMemberProject.MANAGER);
//        memberProject1_2.setStatusWork(StatusWork.DANG_LAM);
//        memberProject1_2.setId(memberProjectRepository.save(memberProject1_2).getId());

        Period period1_2 = new Period();
        period1_2.setCode("period_1");
        period1_2.setName("Giai đoạn thiết kế database");
        period1_2.setStartTime(1678294800000L);
        period1_2.setEndTime(1682355600000L);
        period1_2.setTarget("Hoàn thiện khung database");
        period1_2.setProgress(Float.parseFloat("0"));
        period1_2.setStatusPeriod(StatusPeriod.DANG_DIEN_RA);
        period1_2.setDescriptions("Giai đoạn quan trọng 1");
        period1_2.setProjectId(project2.getId());
        period1_2.setId(periodRepository.save(period1_2).getId());

        Period period2_2 = new Period();
        period2_2.setCode("period_2");
        period2_2.setName("Giai đoạn thiết kế giao diện");
        period2_2.setStartTime(1682355600000L);
        period2_2.setEndTime(1685379600000L);
        period2_2.setTarget("Hoàn thiện khung database");
        period2_2.setProgress(Float.parseFloat("0"));
        period2_2.setStatusPeriod(StatusPeriod.CHUA_DIEN_RA);
        period2_2.setDescriptions("Giai đoạn quan trọng 2");
        period2_2.setProjectId(project2.getId());
        period2_2.setId(periodRepository.save(period2_2).getId());

        Todo todo1_2 = new Todo();
        todo1_2.setCode("Todo_1");
        todo1_2.setName("CRUD bảng category");
        todo1_2.setProgress((short) 0);
        todo1_2.setDescriptions("Không có mô tả");
        todo1_2.setDeadline(1679677200000L);
        todo1_2.setCompletionTime(null);
        todo1_2.setTodoListId(todoList1_2.getId());
        todo1_2.setIndexTodo((short) 0);
        todo1_2.setTodoId(null);
        todo1_2.setType(TypeTodo.CONG_VIEC);
        todo1_2.setPriorityLevel(PriorityLevel.QUAN_TRONG);
        todo1_2.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo1_2.setId(todoRepository.save(todo1_2).getId());

        PeriodTodo periodTodo1_2 = new PeriodTodo();
        periodTodo1_2.setPeriodId(period1_2.getId());
        periodTodo1_2.setTodoId(todo1_2.getId());
        periodTodo1_2.setId(periodTodoRepository.save(periodTodo1_2).getId());
// Hiệu fake 14/10/2023
        RoleFactory roleFactory1 = new RoleFactory();
        roleFactory1.setName("Quản lý");
        roleFactory1.setDescriptions("Có vai trò quản lý tổng quan, phạm vi, nguồn lực, thời gian,...");
        roleFactory1.setId(roleFactoryRepository.save(roleFactory1).getId());

        RoleFactory roleFactory2 = new RoleFactory();
        roleFactory2.setName("Trưởng nhóm");
        roleFactory2.setDescriptions("Có vai trò lập kế hoạch, phân chia nhiệm vụ, hướng dẫn, giao tiếp và giải quyết xung đột bằng hòa bình hihi...");
        roleFactory2.setId(roleFactoryRepository.save(roleFactory2).getId());

        RoleFactory roleFactory3 = new RoleFactory();
        roleFactory3.setName("Phó nhóm");
        roleFactory3.setDescriptions("Có vai trò hỗ trợ trưởng nhóm trong mọi trường hợp haha,...");
        roleFactory3.setId(roleFactoryRepository.save(roleFactory3).getId());

        RoleFactory roleFactory4 = new RoleFactory();
        roleFactory4.setName("Thành viên");
        roleFactory4.setDescriptions("Có vai trò chỉ việc ăn và lăn lên mọi mặt trận,...");
        roleFactory4.setId(roleFactoryRepository.save(roleFactory4).getId());

        TeamFactory teamFactory1 = new TeamFactory();
        teamFactory1.setName("Team HIỆU XỊN XÒ");
        teamFactory1.setDescriptions("Quản lý xây dựng báo cáo xưởng thực hành vip pro kaka ha => <3 ");
        teamFactory1.setId(teamFactoryRepository.save(teamFactory1).getId());

        TeamFactory teamFactory2 = new TeamFactory();
        teamFactory2.setName("Team Hei Babe bu CHICKEN");
        teamFactory2.setDescriptions("Quản lý xây dựng Module bài viết nhiều bug hơn Team dự án :<<");
        teamFactory2.setId(teamFactoryRepository.save(teamFactory2).getId());

        TeamFactory teamFactory3 = new TeamFactory();
        teamFactory3.setName("Team BARR anh SƠN HÓT POI");
        teamFactory3.setDescriptions("Quản lý xây dựng Module sự kiện event cho CÔ NGUYỄN THỊ HẰNG XINH ĐỆP");
        teamFactory3.setId(teamFactoryRepository.save(teamFactory3).getId());

        TeamFactory teamFactory4 = new TeamFactory();
        teamFactory4.setName("Team ăn chơi");
        teamFactory4.setDescriptions("Chỉ biết ăn chơi hát lượn hhihii");
        teamFactory4.setId(teamFactoryRepository.save(teamFactory4).getId());

        MemberFactory memberFactory1 = new MemberFactory();
        memberFactory1.setMemberId(studentClasses1.getStudentId());
        memberFactory1.setRoleFactoryId(roleFactory1.getId());
        memberFactory1.setId(memberFactoryRepository.save(memberFactory1).getId());

        MemberFactory memberFactory2 = new MemberFactory();
        memberFactory2.setMemberId(studentClasses2.getStudentId());
        memberFactory2.setRoleFactoryId(roleFactory2.getId());
        memberFactory2.setId(memberFactoryRepository.save(memberFactory2).getId());

        MemberFactory memberFactory3 = new MemberFactory();
        memberFactory3.setMemberId(studentClasses3.getStudentId());
        memberFactory3.setRoleFactoryId(roleFactory3.getId());
        memberFactory3.setId(memberFactoryRepository.save(memberFactory3).getId());

        MemberFactory memberFactory4 = new MemberFactory();
        memberFactory4.setMemberId(studentClasses4.getStudentId());
        memberFactory4.setRoleFactoryId(roleFactory4.getId());
        memberFactory4.setId(memberFactoryRepository.save(memberFactory4).getId());

        MemberFactory memberFactory5 = new MemberFactory();
        memberFactory5.setMemberId(studentClasses5.getStudentId());
        memberFactory5.setRoleFactoryId(roleFactory4.getId());
        memberFactory5.setId(memberFactoryRepository.save(memberFactory5).getId());

        MemberFactory memberFactory6 = new MemberFactory();
        memberFactory6.setMemberId(studentClasses6.getStudentId());
        memberFactory6.setRoleFactoryId(roleFactory1.getId());
        memberFactory6.setId(memberFactoryRepository.save(memberFactory6).getId());

        MemberFactory memberFactory7 = new MemberFactory();
        memberFactory7.setMemberId(studentClasses7.getStudentId());
        memberFactory7.setRoleFactoryId(roleFactory2.getId());
        memberFactory7.setId(memberFactoryRepository.save(memberFactory7).getId());

        MemberTeamFactory memberTeamFactory1 = new MemberTeamFactory();
        memberTeamFactory1.setMemberFactoryId(memberFactory1.getId());
        memberTeamFactory1.setTeamFactoryId(teamFactory1.getId());
        memberTeamFactory1.setStatusMemberTeamFactory(StatusMemberTeamFactory.HOAT_DONG);
        memberTeamFactory1.setId(memberTeamFactoryRepository.save(memberTeamFactory1).getId());

        MemberTeamFactory memberTeamFactory2 = new MemberTeamFactory();
        memberTeamFactory2.setMemberFactoryId(memberFactory2.getId());
        memberTeamFactory2.setTeamFactoryId(teamFactory1.getId());
        memberTeamFactory2.setStatusMemberTeamFactory(StatusMemberTeamFactory.HOAT_DONG);
        memberTeamFactory2.setId(memberTeamFactoryRepository.save(memberTeamFactory2).getId());

        MemberTeamFactory memberTeamFactory3 = new MemberTeamFactory();
        memberTeamFactory3.setMemberFactoryId(memberFactory3.getId());
        memberTeamFactory3.setTeamFactoryId(teamFactory1.getId());
        memberTeamFactory3.setStatusMemberTeamFactory(StatusMemberTeamFactory.HOAT_DONG);
        memberTeamFactory3.setId(memberTeamFactoryRepository.save(memberTeamFactory3).getId());

        MemberTeamFactory memberTeamFactory4 = new MemberTeamFactory();
        memberTeamFactory4.setMemberFactoryId(memberTeamFactory4.getId());
        memberTeamFactory4.setTeamFactoryId(teamFactory1.getId());
        memberTeamFactory4.setStatusMemberTeamFactory(StatusMemberTeamFactory.HOAT_DONG);
        memberTeamFactory4.setId(memberTeamFactoryRepository.save(memberTeamFactory4).getId());

        MemberTeamFactory memberTeamFactory5 = new MemberTeamFactory();
        memberTeamFactory5.setMemberFactoryId(memberFactory5.getId());
        memberTeamFactory5.setTeamFactoryId(teamFactory1.getId());
        memberTeamFactory5.setStatusMemberTeamFactory(StatusMemberTeamFactory.KHONG_HOAT_DONG);
        memberTeamFactory5.setId(memberTeamFactoryRepository.save(memberTeamFactory5).getId());

        MemberTeamFactory memberTeamFactory6 = new MemberTeamFactory();
        memberTeamFactory6.setMemberFactoryId(memberFactory6.getId());
        memberTeamFactory6.setTeamFactoryId(teamFactory2.getId());
        memberTeamFactory6.setStatusMemberTeamFactory(StatusMemberTeamFactory.HOAT_DONG);
        memberTeamFactory6.setId(memberTeamFactoryRepository.save(memberTeamFactory6).getId());

        MemberTeamFactory memberTeamFactory7 = new MemberTeamFactory();
        memberTeamFactory7.setMemberFactoryId(memberFactory7.getId());
        memberTeamFactory7.setTeamFactoryId(teamFactory2.getId());
        memberTeamFactory7.setStatusMemberTeamFactory(StatusMemberTeamFactory.HOAT_DONG);
        memberTeamFactory7.setId(memberTeamFactoryRepository.save(memberTeamFactory7).getId());

        GroupProject groupProject1 = new GroupProject();
        groupProject1.setName("Group Chỉ biết ĂN và Lăn");
        groupProject1.setDescription(" Ăn chơi múa hát cắn kẹo hút ke. Nói chung là vứt");
        groupProject1.setBackgroundImage("rgb(38, 144, 214)");
        groupProject1.setId(groupProjectRepository.save(groupProject1).getId());

        GroupProject groupProject2 = new GroupProject();
        groupProject2.setName("Group Chăm chỉ cần cù chịu khó");
        groupProject2.setDescription("ĐƯỢT của nó luôn ạ. Yêu thương chiều chuộng luông");
        groupProject2.setBackgroundImage("#07bc0c");
        groupProject2.setId(groupProjectRepository.save(groupProject2).getId());

        GroupProject groupProject3 = new GroupProject();
        groupProject3.setName("Group Nhởn nhơ");
        groupProject3.setDescription("Nói mà KHông bao giờ chịu nghe, cứ nhơ nhơ cái mặt ra. Nói chung là vứt");
        groupProject3.setBackgroundImage("#f1c40f");
        groupProject3.setId(groupProjectRepository.save(groupProject3).getId());

        TypeProject typeProject1 = new TypeProject();
        typeProject1.setName("Type 1");
        typeProject1.setDescription("Quan trọng, xây dựng hệ thống cổng thông tin của trường !");
        typeProject1.setId(typeProjectRepository.save(typeProject1).getId());

        TypeProject typeProject2 = new TypeProject();
        typeProject2.setName("Type 2");
        typeProject2.setDescription("Không quan trọng, được sử dụng cho các nhóm trong xưởng !");
        typeProject2.setId(typeProjectRepository.save(typeProject2).getId());

    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }

}