package com.portalprojectstool;

import com.labreportapp.portalprojects.entity.Category;
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
import com.labreportapp.portalprojects.repository.LabelRepository;
import com.labreportapp.portalprojects.repository.LabelProjectTodoRepository;
import com.labreportapp.portalprojects.repository.MemberProjectRepository;
import com.labreportapp.portalprojects.repository.PeriodTodoRepository;
import com.labreportapp.portalprojects.repository.PeriodRepository;
import com.labreportapp.portalprojects.repository.ProjectCategoryRepository;
import com.labreportapp.portalprojects.repository.ProjectRepository;
import com.labreportapp.portalprojects.repository.LabelProjectRepository;
import com.labreportapp.portalprojects.repository.StakeholderProjectRepository;
import com.labreportapp.portalprojects.repository.TodoListRepository;
import com.labreportapp.portalprojects.repository.TodoRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author thangncph26123
 */

@SpringBootApplication
@EnableJpaRepositories(
        basePackages = "com.labreportapp.portalprojects.repository"
)
public class DBGenerator implements CommandLineRunner {

    private final boolean IS_RELEASE = false;

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

    @Override
    public void run(String... args) throws Exception {
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

        Project project1 = new Project();
        project1.setCode("Project_1");
        project1.setName("Module quản lý dự án");
        project1.setStartTime(1678294800000L);
        project1.setEndTime(1685379600000L);
        project1.setProgress(Float.parseFloat("0"));
        project1.setBackgroundColor("#59a1e3");
        project1.setDescriptions("Mục đích của dự án là để quản lý các dự án của bộ môn PTPM");
        project1.setStatusProject(StatusProject.DANG_DIEN_RA);
        project1.setId((projectRepository.save(project1).getId()));

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

        MemberProject memberProject1 = new MemberProject();
        memberProject1.setMemberId("db39ebf0-bdd4-11ed-afa1-0242ac120002");
        memberProject1.setProjectId(project1.getId());
        memberProject1.setRole(RoleMemberProject.MANAGER);
        memberProject1.setStatusWork(StatusWork.DANG_LAM);
        memberProject1.setId(memberProjectRepository.save(memberProject1).getId());

        MemberProject memberProject2 = new MemberProject();
        memberProject2.setMemberId("c5cf1e20-bdd4-11ed-afa1-0242ac120002");
        memberProject2.setProjectId(project1.getId());
        memberProject2.setRole(RoleMemberProject.LEADER);
        memberProject2.setStatusWork(StatusWork.DANG_LAM);
        memberProject2.setId(memberProjectRepository.save(memberProject2).getId());

        MemberProject memberProject3 = new MemberProject();
        memberProject3.setMemberId("d21b02c0-bdd4-11ed-afa1-0242ac120002");
        memberProject3.setProjectId(project1.getId());
        memberProject3.setRole(RoleMemberProject.DEV);
        memberProject3.setStatusWork(StatusWork.DANG_LAM);
        memberProject3.setId(memberProjectRepository.save(memberProject3).getId());

        MemberProject memberProject4 = new MemberProject();
        memberProject4.setMemberId("f01c9f36-bdd4-11ed-afa1-0242ac120002");
        memberProject4.setProjectId(project1.getId());
        memberProject4.setRole(RoleMemberProject.TESTER);
        memberProject4.setStatusWork(StatusWork.DANG_LAM);
        memberProject4.setId(memberProjectRepository.save(memberProject4).getId());

        MemberProject memberProject5 = new MemberProject();
        memberProject5.setMemberId("f6b388e6-bdd4-11ed-afa1-0242ac120002");
        memberProject5.setProjectId(project1.getId());
        memberProject5.setRole(RoleMemberProject.DEV);
        memberProject5.setStatusWork(StatusWork.DANG_LAM);
        memberProject5.setId(memberProjectRepository.save(memberProject5).getId());

        MemberProject memberProject6 = new MemberProject();
        memberProject6.setMemberId("02e78c02-bdd5-11ed-afa1-0242ac120002");
        memberProject6.setProjectId(project1.getId());
        memberProject6.setRole(RoleMemberProject.TESTER);
        memberProject6.setStatusWork(StatusWork.DANG_LAM);
        memberProject6.setId(memberProjectRepository.save(memberProject6).getId());

        MemberProject memberProject7 = new MemberProject();
        memberProject7.setMemberId("0791e31a-bdd5-11ed-afa1-0242ac120002");
        memberProject7.setProjectId(project1.getId());
        memberProject7.setRole(RoleMemberProject.DEV);
        memberProject7.setStatusWork(StatusWork.DANG_LAM);
        memberProject7.setId(memberProjectRepository.save(memberProject7).getId());

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
        //

        Project project2 = new Project();
        project2.setCode("Project_2");
        project2.setName("Module quản lý thành viên");
        project2.setStartTime(1678294800000L);
        project2.setEndTime(1685379600000L);
        project2.setBackgroundColor("#59a1e3");
        project2.setProgress(Float.parseFloat("0"));
        project2.setDescriptions("Mục đích của dự án là để quản lý các dự án của bộ môn PTPM");
        project2.setStatusProject(StatusProject.DANG_DIEN_RA);
        project2.setId((projectRepository.save(project2).getId()));

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

        MemberProject memberProject1_2 = new MemberProject();
        memberProject1_2.setMemberId("c5cf1e20-bdd4-11ed-afa1-0242ac120002");
        memberProject1_2.setProjectId(project2.getId());
        memberProject1_2.setRole(RoleMemberProject.MANAGER);
        memberProject1_2.setStatusWork(StatusWork.DANG_LAM);
        memberProject1_2.setId(memberProjectRepository.save(memberProject1_2).getId());

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

        Path currentPath = Paths.get(""); // đường dẫn hiện tại
        String parentPath = currentPath.toAbsolutePath().toString().substring(0, currentPath.toAbsolutePath().toString().lastIndexOf("\\"));

        String test = parentPath + "/front_end/assets/imgTodo";

        try {
            File directory = new File(test);
            FileUtils.deleteDirectory(directory);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String folderNew = parentPath + "/front_end/assets/imgTodo";
        File folder = new File(folderNew);
        if (!folder.exists()) {
            folder.mkdirs();
        }

    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }

}