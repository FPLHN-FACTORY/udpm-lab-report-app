package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportService;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportTeam;
import com.labreportapp.labreport.core.teacher.model.request.TeCreateProjectToTeamRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeCreateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.request.TeTeamUpdateStudentClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.labreport.core.teacher.repository.TeCategoryRepository;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeLabelProjectRepository;
import com.labreportapp.labreport.core.teacher.repository.TeLabelRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMemberProjectRepository;
import com.labreportapp.labreport.core.teacher.repository.TeProjectCategoryRepository;
import com.labreportapp.labreport.core.teacher.repository.TeProjectRepository;
import com.labreportapp.labreport.core.teacher.repository.TeRoleConfigReposiotry;
import com.labreportapp.labreport.core.teacher.repository.TeRoleMemberProjectRepository;
import com.labreportapp.labreport.core.teacher.repository.TeRoleProjectRepository;
import com.labreportapp.labreport.core.teacher.repository.TeSemesterRepository;
import com.labreportapp.labreport.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.labreport.core.teacher.repository.TeTeamsRepositoty;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.core.teacher.service.TeTeamsService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.Semester;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.entity.Team;
import com.labreportapp.labreport.infrastructure.constant.RoleDefault;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.labreport.util.RandomString;
import com.labreportapp.labreport.util.SemesterHelper;
import com.labreportapp.portalprojects.entity.Label;
import com.labreportapp.portalprojects.entity.LabelProject;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.entity.RoleConfig;
import com.labreportapp.portalprojects.entity.RoleMemberProject;
import com.labreportapp.portalprojects.entity.RoleProject;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.StatusProject;
import com.labreportapp.portalprojects.infrastructure.constant.StatusWork;
import com.labreportapp.portalprojects.infrastructure.constant.TypeProject;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TeTeamsServiceImpl implements TeTeamsService {

    @Autowired
    private TeTeamsRepositoty teTeamsRepositoty;

    @Autowired
    private TeStudentClassesRepository teStudentClassesRepository;

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private TeExcelImportService teExcelImportService;

    @Autowired
    private TeProjectRepository teProjectRepository;

    @Autowired
    private TeMemberProjectRepository teMemberProjectRepository;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private TeRoleConfigReposiotry teRoleConfigReposiotry;

    @Autowired
    private TeRoleProjectRepository teRoleProjectRepository;

    @Autowired
    private TeLabelRepository teLabelRepository;

    @Autowired
    private TeLabelProjectRepository teLabelProjectRepository;

    @Autowired
    private TeRoleMemberProjectRepository teRoleMemberProjectRepository;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private TeSemesterRepository teSemesterRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private TeProjectCategoryRepository teProjectCategoryRepository;

    @Autowired
    private TeCategoryRepository teCategoryRepository;

    @Override
    public List<TeTeamsRespone> getAllTeams(final TeFindStudentClasses teFindStudentClasses) {
        return teTeamsRepositoty.findTeamsByIdClass(teFindStudentClasses);
    }

    @Override
    @Transactional
    @Synchronized
    public Team createTeam(@Valid TeCreateTeamsRequest request) {
        List<TeTeamUpdateStudentClassRequest> studentClassesRequest = request.getListStudentClasses();
        int checkLeader = 0;
        for (TeTeamUpdateStudentClassRequest teTeamUpdateStudentClassRequest : studentClassesRequest) {
            if (teTeamUpdateStudentClassRequest.getRole().equals("0")) {
                checkLeader++;
            }
        }
        if (checkLeader > 1) {
            throw new RestApiException(Message.UNIQUE_LEADER_TEAM);
        }
        StringBuilder message = new StringBuilder();
        String codeClass = loggerUtil.getCodeClassByIdClass(request.getClassId());
        String nameSemester = loggerUtil.getNameSemesterByIdClass(request.getClassId());

        Team team = new Team();
        team.setName("Nhóm " + teTeamsRepositoty.getNameNhomAuto(request.getClassId()));
        team.setSubjectName(request.getSubjectName());
        team.setClassId(request.getClassId());
        Team teamCreate = teTeamsRepositoty.save(team);
        message.append("Đã tạo \"").append(teamCreate.getName()).append("\" với chủ đề: \"").append(teamCreate.getSubjectName()).append("\". ");
        List<StudentClasses> listStudentClasses = teStudentClassesRepository.findStudentClassesByIdClass(request.getClassId());
        List<SimpleResponse> listInforStudent = teStudentClassesService.searchAllStudentByIdClass(request.getClassId());
        List<StudentClasses> studentClassesNew = new ArrayList<>();

        AtomicInteger countStudentJoinTeam = new AtomicInteger();
        countStudentJoinTeam.set(0);
        StringBuilder messageCheckStudentJoin = new StringBuilder();
        studentClassesRequest.forEach(item -> {
            listStudentClasses.forEach(studentDB -> {
                if (studentDB.getId().equals(item.getIdStudentClass())) {
                    if ("0".equals(item.getRole())) {
                        studentDB.setRole(RoleTeam.LEADER);
                    } else {
                        studentDB.setRole(RoleTeam.MEMBER);
                    }
                    studentDB.setClassId(request.getClassId());
                    studentDB.setTeamId(teamCreate.getId());
                    studentClassesNew.add(studentDB);
                    listInforStudent.forEach(infor -> {
                        if (infor.getId().equals(studentDB.getStudentId())) {
                            messageCheckStudentJoin.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName());
                            if (studentDB.getRole() == RoleTeam.LEADER) {
                                messageCheckStudentJoin.append(" trưởng nhóm");
                            }
                            messageCheckStudentJoin.append(",");
                        }
                    });
                    countStudentJoinTeam.set(countStudentJoinTeam.get() + 1);
                }
            });
        });
        if (messageCheckStudentJoin.length() > 0 && messageCheckStudentJoin.charAt(messageCheckStudentJoin.length() - 1) == ',') {
            messageCheckStudentJoin.deleteCharAt(messageCheckStudentJoin.length() - 1);
        }
        if (countStudentJoinTeam.get() > 0) {
            message.append(" Với số thành viên là ").append(countStudentJoinTeam.get()).append(" - Gồm: ").append(messageCheckStudentJoin.toString()).append(".");
        } else {
            message.append(" Với số thành viên là 0. ");
        }
        teStudentClassesRepository.saveAll(studentClassesNew);
        loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);
        return teamCreate;
    }

    @Override
    @Transactional
    @Synchronized
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public Team updateTeam(@Valid TeUpdateTeamsRequest request) {
        Optional<Team> teamFilter = teTeamsRepositoty.findById(request.getId());
        if (teamFilter == null) {
            throw new RestApiException(Message.TEAM_NOT_EXISTS);
        }
        Team team = teamFilter.get();
        List<TeTeamUpdateStudentClassRequest> studentClassesRequest = request.getListStudentClasses();
        List<TeTeamUpdateStudentClassRequest> studentClassesDeleteIdTeamRequest =
                request.getListStudentClassesDeleteIdTeam();
        int checkLeader = 0;
        for (TeTeamUpdateStudentClassRequest teTeamUpdateStudentClassRequest : studentClassesRequest) {
            if (teTeamUpdateStudentClassRequest.getRole().equals("0")) {
                checkLeader++;
            }
        }
        if (checkLeader > 1) {
            throw new RestApiException(Message.UNIQUE_LEADER_TEAM);
        }
        StringBuilder message = new StringBuilder();
        String codeClass = loggerUtil.getCodeClassByIdClass(team.getClassId());
        String nameSemester = loggerUtil.getNameSemesterByIdClass(team.getClassId());

        if (team.getSubjectName().equals("")) {
            message.append("Đã cập nhật \"").append(team.getName()).append("\" và thêm chủ đề là \"").append(request.getSubjectName()).append("\". ");
        } else if (request.getSubjectName().equals("") && !team.getSubjectName().equals("")) {
            message.append("Đã cập nhật \"").append(team.getName()).append("\" và xóa chủ đề. ");
        } else if (!request.getSubjectName().equals("") && !team.getSubjectName().equals("")) {
            message.append("Đã cập nhật \"").append(team.getName()).append("\" và cập nhật chủ đề từ \"").append(team.getSubjectName()).append("\" thành \"").append(request.getSubjectName()).append("\". ");
        }
        team.setSubjectName(request.getSubjectName() != null ? request.getSubjectName().trim() : "");
        ConcurrentHashMap<String, StudentClasses> mapStudent = new ConcurrentHashMap<>();
        addDataStudentDB(mapStudent, team.getClassId());
        List<StudentClasses> listStudentClassesUpdate = new ArrayList<>();
        List<StudentClasses> listStudentClass = teStudentClassesRepository.findStudentClassesByIdClass(team.getClassId());
        List<SimpleResponse> listInforStudent = teStudentClassesService.searchAllStudentByIdClass(team.getClassId());

        AtomicInteger countStudentUpdateJoinTeam = new AtomicInteger();
        countStudentUpdateJoinTeam.set(0);
        StringBuilder messageCheckUpdate = new StringBuilder();
        studentClassesRequest.forEach(item -> {
            listStudentClass.forEach(stu -> {
                if (stu.getId().equals(item.getIdStudentClass())) {
                    StudentClasses studentClasses = new StudentClasses();
                    studentClasses.setId(stu.getId());
                    studentClasses.setStudentId(stu.getStudentId());
                    studentClasses.setStatusStudentFeedBack(stu.getStatusStudentFeedBack());
                    studentClasses.setStatus(stu.getStatus());
                    studentClasses.setEmail(stu.getEmail());
                    studentClasses.setClassId(stu.getClassId());
                    if ("0".equals(item.getRole())) {
                        studentClasses.setRole(RoleTeam.LEADER);
                    } else {
                        studentClasses.setRole(RoleTeam.MEMBER);
                    }
                    listInforStudent.forEach(infor -> {
                        if (infor.getId().equals(item.getIdStudent())) {
                            messageCheckUpdate.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName());
                            if (studentClasses.getRole() == RoleTeam.LEADER) {
                                messageCheckUpdate.append("  trưởng nhóm");
                            }
                            messageCheckUpdate.append(",");
                        }
                    });
                    studentClasses.setTeamId(team.getId());
                    countStudentUpdateJoinTeam.set(countStudentUpdateJoinTeam.get() + 1);
                    listStudentClassesUpdate.add(studentClasses);
                }
            });
        });
        if (messageCheckUpdate.length() > 0 && messageCheckUpdate.charAt(messageCheckUpdate.length() - 1) == ',') {
            messageCheckUpdate.deleteCharAt(messageCheckUpdate.length() - 1);
        }
        if (countStudentUpdateJoinTeam.get() > 0) {
            message.append(" Với số thành viên là ").append(countStudentUpdateJoinTeam.get()).append(" - Gồm: ").append(messageCheckUpdate.toString()).append(". ");
        } else {
            message.append(" Với số thành viên là 0. ");
        }

        AtomicInteger countStudentDeleteJoinTeam = new AtomicInteger();
        countStudentUpdateJoinTeam.set(0);
        StringBuilder messageCheckDelete = new StringBuilder();
        studentClassesDeleteIdTeamRequest.forEach(item -> {
            listStudentClass.forEach(student -> {
                if (student.getId().equals(item.getIdStudentClass())) {
                    StudentClasses st = new StudentClasses();
                    st.setId(item.getIdStudentClass());
                    st.setStudentId(item.getIdStudent());
                    st.setTeamId(null);
                    st.setClassId(student.getClassId());
                    st.setRole(RoleTeam.MEMBER);
                    st.setStatusStudentFeedBack(student.getStatusStudentFeedBack());
                    st.setEmail(student.getEmail());
                    st.setStatus(student.getStatus());
                    listInforStudent.forEach(infor -> {
                        if (infor.getId().equals(item.getIdStudent())) {
                            messageCheckDelete.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName()).append(",");
                        }
                    });
                    countStudentDeleteJoinTeam.set(countStudentDeleteJoinTeam.get() + 1);
                    listStudentClassesUpdate.add(st);
                }
            });
        });
        if (messageCheckDelete.length() > 0 && messageCheckDelete.charAt(messageCheckDelete.length() - 1) == ',') {
            messageCheckDelete.deleteCharAt(messageCheckDelete.length() - 1);
        }
        if (countStudentDeleteJoinTeam.get() > 0) {
            message.append(" Và xóa ").append(countStudentDeleteJoinTeam.get()).append(" thành viên - Gồm: ").append(messageCheckDelete.toString()).append(" ra khỏi nhóm. ");
        }
        loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);
        teStudentClassesRepository.saveAll(listStudentClassesUpdate);
        return teTeamsRepositoty.save(team);
    }

    public String convertLongToStringDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public Team createProjectAddTeam(TeCreateProjectToTeamRequest request) {
        Optional<Team> team = teTeamsRepositoty.findById(request.getIdTeam());
        if (!team.isPresent()) {
            throw new RestApiException(Message.TEAM_NOT_EXISTS);
        }
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        Team teamUp = team.get();
        Project project = new Project();
        if (idSemesterCurrent != null) {
            Optional<Semester> semesterCurrent = teSemesterRepository.findById(idSemesterCurrent);
            if (semesterCurrent.isPresent()) {
                project.setEndTime(DateUtils.truncate(new Date(semesterCurrent.get().getEndTime()), Calendar.DATE).getTime());
            } else {
                project.setEndTime(DateUtils.truncate(new Date(), Calendar.DATE).getTime() + 90L * 86400000);
            }
        }
        project.setCode("Project_" + RandomString.random());
        project.setName("Dự án " + RandomString.random());
        project.setStartTime(DateUtils.truncate(new Date(), Calendar.DATE).getTime());
        project.setDescriptions(teamUp.getSubjectName());
        project.setProgress(0F);
        project.setStatusProject(StatusProject.DANG_DIEN_RA);
        project.setTypeProject(TypeProject.DU_AN_XUONG_THUC_HANH);
        project.setBackgroundColor("rgb(38, 144, 214)");
        Project projectNew = teProjectRepository.save(project);
        teamUp.setProjectId(projectNew.getId());

        StringBuilder message = new StringBuilder();
        String codeClass = loggerUtil.getCodeClassByIdClass(teamUp.getClassId());
        String nameSemester = loggerUtil.getNameSemesterByIdClass(teamUp.getClassId());
        message.append("Đã tạo trello cho \"").append(teamUp.getName()).append("\" với Mã dự án: ").append(projectNew.getCode()).append(", ").append("Tên dự án: ").append(project.getName()).append(" , Thời gian bắt đầu-kết thúc: ").append(convertLongToStringDate(projectNew.getStartTime())).append("-").append(convertLongToStringDate(projectNew.getEndTime())).append(", Loại: Dự án xưởng thực hành");
        if (!project.getDescriptions().equals("")) {
            message.append(", Mô tả: ").append(projectNew.getDescriptions());
        }
        message.append(" và background mặc định. ");
        loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);

        List<Label> listLabel = teLabelRepository.findAll();
        List<LabelProject> newLabelProject = new ArrayList<>();
        listLabel.forEach(item -> {
            LabelProject labelProject = new LabelProject();
            labelProject.setProjectId(projectNew.getId());
            labelProject.setName(item.getName());
            labelProject.setColorLabel(item.getColorLabel());
            newLabelProject.add(labelProject);
        });
        teLabelProjectRepository.saveAll(newLabelProject);

        List<RoleConfig> listRoleConfig = teRoleConfigReposiotry.findAll();
        List<RoleProject> newRoleProject = new ArrayList<>();
        listRoleConfig.forEach(item -> {
            RoleProject roleProject = new RoleProject();
            roleProject.setProjectId(projectNew.getId());
            roleProject.setName(item.getName());
            roleProject.setDescription(item.getDescription());
            roleProject.setRoleDefault(item.getRoleDefault());
            newRoleProject.add(roleProject);
        });
        List<RoleProject> listRoleProject = teRoleProjectRepository.saveAll(newRoleProject);

        MemberProject memberCreateProject = new MemberProject();
        memberCreateProject.setMemberId(labReportAppSession.getUserId());
        memberCreateProject.setProjectId(projectNew.getId());
        memberCreateProject.setEmail(labReportAppSession.getEmail());
        memberCreateProject.setStatusWork(StatusWork.DANG_LAM);
        memberCreateProject.setId(teMemberProjectRepository.save(memberCreateProject).getId());

        RoleMemberProject roleMemberProjectDefault = new RoleMemberProject();
        roleMemberProjectDefault.setMemberProjectId(memberCreateProject.getId());
        Optional<RoleProject> roleProSetDefault = listRoleProject.stream()
                .filter(i -> i.getRoleDefault().equals(RoleDefault.DEFAULT))
                .findFirst();
        if (roleProSetDefault.isPresent()) {
            roleMemberProjectDefault.setRoleProjectId(roleProSetDefault.get().getId());
        } else {
            if (listRoleProject.size() > 0) {
                RoleProject roleProjectAp = listRoleProject.get(0);
                roleMemberProjectDefault.setRoleProjectId(roleProjectAp.getId());
            }
        }
        teRoleMemberProjectRepository.save(roleMemberProjectDefault);
        return teTeamsRepositoty.save(teamUp);
    }

    @Override
    @Transactional
    @Synchronized
    public String deleteTeamById(String idTeam) {
        Team team = teTeamsRepositoty.findById(idTeam).get();
        if (team != null) {
            String idClass = team.getClassId();
            List<StudentClasses> listStudentClass = teStudentClassesRepository.findAllStudentClassesByIdTeam(idTeam);
            listStudentClass.forEach(item -> {
                item.setTeamId(null);
                teStudentClassesRepository.save(item);
            });
            StringBuilder message = new StringBuilder();
            String codeClass = loggerUtil.getCodeClassByIdClass(team.getClassId());
            String nameSemester = loggerUtil.getNameSemesterByIdClass(team.getClassId());
            message.append("Đã xóa \"").append(team.getName()).append("\" và xóa tất cả thành viên ra khỏi nhóm. ");
            loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);
            teTeamsRepositoty.delete(team);
            List<Team> listTeam = teTeamsRepositoty.getTeamByClassId(idClass);
            if (listTeam.size() > 0) {
                for (int i = 0; i < listTeam.size(); i++) {
                    listTeam.get(i).setName("Nhóm " + (i + 1));
                }
                teTeamsRepositoty.saveAll(listTeam);
            }
            return "Xóa nhóm thành công !";
        } else {
            return "Không tìm thấy nhóm, xóa thất bại !";
        }
    }

    @Override
    @Synchronized
    public ByteArrayOutputStream exportExcelTeam(HttpServletResponse response, String idClass) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchApiStudentClassesByIdClass(idClass);
            Class objClass = teClassRepository.findById(idClass).get();
            Sheet sheet = configTitle(workbook, objClass.getCode());
            if (listStudent == null) {
                workbook.write(response.getOutputStream());
                workbook.close();
                return outputStream;
            }
            int rowIndex = 3;
            int index = 1;
            for (TeStudentCallApiResponse data : listStudent) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(index++);
                dataRow.getCell(0).setCellStyle(chooseCellStyle("dataCenterTable", workbook));
                dataRow.createCell(1).setCellValue(data.getName());
                dataRow.getCell(1).setCellStyle(chooseCellStyle("dataTable", workbook));
                dataRow.createCell(2).setCellValue(data.getEmail());
                dataRow.getCell(2).setCellStyle(chooseCellStyle("dataTable", workbook));
                dataRow.createCell(3).setCellValue(data.getRole() != null ? data.getRole().equals("0") ? "X" : "" : "");
                if (data.getNameTeam() == null || data.getNameTeam().equals("")) {
                    dataRow.createCell(3).setCellValue("");
                }
                dataRow.getCell(3).setCellStyle(chooseCellStyle("dataCenterTable", workbook));
                dataRow.createCell(4).setCellValue(data.getNameTeam());
                dataRow.getCell(4).setCellStyle(chooseCellStyle("dataTable", workbook));
                dataRow.createCell(5).setCellValue(data.getSubjectName());
                dataRow.getCell(5).setCellStyle(chooseCellStyle("dataTable", workbook));
            }
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i, true);
            }
            workbook.write(response.getOutputStream());
            workbook.close();
            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Sheet configTitle(Workbook workbook, String name) {
        Sheet sheet = workbook.createSheet("Danh sách");
        Row titleRow = sheet.createRow(0);
        Cell cellTitle = titleRow.createCell(0);
        cellTitle.setCellValue("DANH SÁCH NHÓM TRONG LỚP " + name);
        cellTitle.setCellStyle(chooseCellStyle("title", workbook));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 8000);
        sheet.setColumnWidth(3, 2000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 7000);
        Row headerRow = sheet.createRow(2);
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("STT");
        cell0.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell2 = headerRow.createCell(1);
        cell2.setCellValue("Họ và tên");
        cell2.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell3 = headerRow.createCell(2);
        cell3.setCellValue("Email");
        cell3.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell4 = headerRow.createCell(3);
        cell4.setCellValue("Vai trò");
        cell4.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell5 = headerRow.createCell(4);
        cell5.setCellValue("Nhóm");
        cell5.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell6 = headerRow.createCell(5);
        cell6.setCellValue("Chủ đề");
        cell6.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell8 = titleRow.createCell(8);
        cell8.setCellValue("Lưu ý: Mỗi nhóm chỉ có duy nhất 1 trưởng nhóm");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 11));
        cell8.setCellStyle(chooseCellStyle("note", workbook));
        Row dataRow2 = sheet.createRow(1);
        Cell leaderCell = dataRow2.createCell(8);
        leaderCell.setCellValue("Trưởng nhóm");
        leaderCell.setCellStyle(chooseCellStyle("dataCenterTable", workbook));
        Cell memberCell = dataRow2.createCell(9);
        memberCell.setCellValue("Thành viên");
        memberCell.setCellStyle(chooseCellStyle("dataCenterTable", workbook));
        Cell leaderCell1 = headerRow.createCell(8);
        leaderCell1.setCellValue("X");
        leaderCell1.setCellStyle(chooseCellStyle("dataCenterTable", workbook));
        Cell leaderCell2 = headerRow.createCell(9);
        leaderCell2.setCellValue("");
        leaderCell2.setCellStyle(chooseCellStyle("dataCenterTable", workbook));
        for (int i = 7; i <= 9; i++) {
            sheet.autoSizeColumn(i, true);
        }
        return sheet;
    }

    private CellStyle chooseCellStyle(String type, Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font fontStyle = workbook.createFont();
        fontStyle.setFontName("Times New Roman");
        if (type.equals("title")) {
            fontStyle.setBold(true);
            fontStyle.setFontHeightInPoints((short) 20);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("titleTable")) {
            fontStyle.setBold(true);
            fontStyle.setColor(IndexedColors.WHITE.getIndex());
            fontStyle.setFontHeightInPoints((short) 13);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFont(fontStyle);
            cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        }
        if (type.equals("dataTable")) {
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("dataCenterTable")) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("note")) {
            fontStyle.setColor(Font.COLOR_RED);
            fontStyle.setFontHeightInPoints((short) 11);
            cellStyle.setFont(fontStyle);
        }
        return cellStyle;
    }

    private final List<TeExcelImportTeam> listTeamImportNew = new ArrayList<>();

    private boolean isExcelFile(MultipartFile file) {
        Workbook workbookCheck = null;
        try {
            workbookCheck = new XSSFWorkbook(file.getInputStream());
        } catch (Exception e) {
            try {
                workbookCheck = new HSSFWorkbook(file.getInputStream());
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    @Synchronized
    public TeExcelResponseMessage importExcelTeam(MultipartFile file, String idClass) {
        TeExcelResponseMessage teExcelResponseMessage = new TeExcelResponseMessage();
        try {
            boolean isExcelFile = isExcelFile(file);
            if (!isExcelFile) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("File excel sai định dạng, vui lòng export mẫu để sử dụng !");
                return teExcelResponseMessage;
            }
            List<TeExcelImportTeam> listInput = teExcelImportService.importDataTeam(file, idClass);
            List<StudentClasses> listStudentUp = new ArrayList<>();
            if (listInput.size() == 0) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("File excel trống, vui lòng export mẫu để sử dụng import !");
                return teExcelResponseMessage;
            }
            ConcurrentHashMap<String, StudentClasses> mapStudent = new ConcurrentHashMap<>();
            addDataStudentDB(mapStudent, idClass);
//            if (listInput.size() != mapStudent.size()) {
//                teExcelResponseMessage.setStatus(false);
//                teExcelResponseMessage.setMessage("Import thất bại. Số lượng sinh viên trong file excel phải bằng với " +
//                        "số lượng sinh viên trong lớp !");
//                return teExcelResponseMessage;
//            }
            teExcelResponseMessage.setStatus(true);
            Map<String, String> teamRoles = new ConcurrentHashMap<>();
            AtomicBoolean checkValidate = new AtomicBoolean(true);
            teExcelResponseMessage.setMessage("");
            listInput.parallelStream().forEach(student -> {
                String regexRole = "^[Xx]?$";
                String regexName = "^[\\p{L}'\\-\\d]+(?:\\s[\\p{L}'\\-\\d]+)*$";
                String regexEmailExactly = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                if (student.getName().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Họ tên không được để trống !");
                    checkValidate.set(false);
                    return;
                } else {
                    if (!student.getName().matches(regexName)) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(" Họ tên phải là chữ cái và các chữ cách nhau 1 dấu cách !");
                        checkValidate.set(false);
                        return;
                    }
                }
                if (student.getEmail().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Email không được để trống !");
                    checkValidate.set(false);
                    return;
                } else {
                    if (!student.getEmail().matches(regexEmailExactly)) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(" Email sai định dạng !");
                        checkValidate.set(false);
                        return;
                    }
                }
                if (!student.getRole().matches(regexRole)) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(
                            " Vai trò chỉ được nhập X hoặc để trống !");
                    checkValidate.set(false);
                    return;
                } else if ("X".equalsIgnoreCase(student.getRole())) {
                    String existingRole = teamRoles.put(student.getNameTeam(), "X");
                    if ("X".equalsIgnoreCase(existingRole)) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(
                                " Không được chỉ định hơn 1 sinh viên làm trưởng nhóm trong cùng một nhóm !");
                        checkValidate.set(false);
                        return;
                    } else if (student.getNameTeam().isEmpty()) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(
                                " Tên nhóm không được để trống nếu sinh viên có vai trò là trưởng nhóm !");
                        checkValidate.set(false);
                        return;
                    }
                } else {
                    StudentClasses studentFind = mapStudent.get(student.getEmail());
                    if (studentFind == null) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(
                                " Email của sinh viên không tồn tại !");
                        checkValidate.set(false);
                        return;
                    }
                }
            });
            HashMap<String, StringBuilder> hashMap = new HashMap<>();
            StringBuilder messageStudentNotTeam = new StringBuilder();
            if (checkValidate.get()) {
                List<SimpleResponse> listInforStudent = teStudentClassesService.searchAllStudentByIdClass(idClass);
                List<Team> listTeamDB = checkGetListTeam(listInput, idClass);
                listTeamDB.forEach(team -> {
                    StringBuilder messTeam = new StringBuilder();
                    String key = team.getName();
                    if (hashMap.containsKey(key)) {
                        hashMap.clear();
                    }
                    if (team.getSubjectName().equals("")) {
                        messTeam.append("Đã cập nhật \"").append(team.getName()).append("\" - Gồm:");
                    } else {
                        messTeam.append("Đã cập nhật \"").append(team.getName()).append("\" với chủ đề")
                                .append(" ").append(team.getSubjectName()).append(" - Gồm:");
                    }
                    hashMap.put(key, messTeam);
                });
                ConcurrentHashMap<String, Team> mapTeam = new ConcurrentHashMap<>();
                addDataTeam(mapTeam, listTeamDB);
                teExcelResponseMessage.setMessage("");
                listTeamImportNew.parallelStream().forEach(student -> {
                    StudentClasses studentFind = mapStudent.get(student.getEmail());
                    Team teamFind = mapTeam.get(student.getNameTeam());
                    studentFind.setTeamId(teamFind != null ? teamFind.getId() : null);
                    studentFind.setRole(student.getRole().equalsIgnoreCase("X") ? RoleTeam.LEADER : RoleTeam.MEMBER);
                    listInforStudent.forEach(infor -> {
                        if (infor.getId().equals(studentFind.getStudentId())) {
                            if (teamFind != null) {
                                if (hashMap.containsKey(teamFind.getName())) {
                                    StringBuilder messageTeam = hashMap.get(teamFind.getName());
                                    messageTeam.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName());
                                    if (studentFind.getRole() == RoleTeam.LEADER) {
                                        messageTeam.append("  trưởng nhóm");
                                    }
                                    messageTeam.append(",");
                                }
                            } else {
                                messageStudentNotTeam.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName()).append(",");
                            }
                        }
                    });
                    listStudentUp.add(studentFind);
                });
                listTeamDB.clear();
                listInforStudent.clear();
                mapTeam.clear();
            }
            if (teExcelResponseMessage.getStatus()) {
                teStudentClassesRepository.saveAll(listStudentUp);
                teExcelResponseMessage.setMessage("Import nhóm thành công !");

                String codeClass = loggerUtil.getCodeClassByIdClass(idClass);
                String nameSemester = loggerUtil.getNameSemesterByIdClass(idClass);
                StringBuilder message = new StringBuilder();
                Map<String, StringBuilder> sortedHashMap = sortHashMapByKey(hashMap);
                sortedHashMap.forEach((key, value) -> {
                    if (value.length() > 0 && value.charAt(value.length() - 1) == ',') {
                        value.setCharAt(value.length() - 1, '.');
                    } else if (value.toString().endsWith("-Gồm")) {
                        int endIndex = value.length() - "-Gồm".length();
                        value.replace(endIndex, value.length(), ".");
                    }
                    message.append(value.append(". "));
                    hashMap.put(key, new StringBuilder().append(","));
                });
                if (messageStudentNotTeam.length() > 0 && messageStudentNotTeam.charAt(messageStudentNotTeam.length() - 1) == ',') {
                    messageStudentNotTeam.setCharAt(messageStudentNotTeam.length() - 1, ' ');
                    messageStudentNotTeam.append(" ").append(" không có nhóm.");
                }
                if (messageStudentNotTeam.isEmpty()) {
                    loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);
                } else {
                    loggerUtil.sendLogStreamClass(message.append(" Với ").append(messageStudentNotTeam.toString()).toString(), codeClass, nameSemester);
                }
            } else {
                teExcelResponseMessage.setMessage("Import nhóm thất bại. " + teExcelResponseMessage.getMessage());
            }
            listInput.clear();
            mapStudent.clear();
            listTeamImportNew.clear();
            hashMap.clear();
            file.getInputStream().close();
            return teExcelResponseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            teExcelResponseMessage.setStatus(false);
            teExcelResponseMessage.setMessage("Lỗi hệ thống, vui lòng F5 lại trang !");
            return teExcelResponseMessage;
        }
    }

    private Map<String, StringBuilder> sortHashMapByKey(HashMap<String, StringBuilder> unsortedMap) {
        Map<String, StringBuilder> sortedMap = new TreeMap<>(new KeyComparator());
        sortedMap.putAll(unsortedMap);
        unsortedMap.clear();
        return sortedMap;
    }

    private static class KeyComparator implements Comparator<String> {
        @Override
        public int compare(String key1, String key2) {
            int num1 = Integer.parseInt(key1.replaceAll("\\D", ""));
            int num2 = Integer.parseInt(key2.replaceAll("\\D", ""));
            return Integer.compare(num1, num2);
        }
    }

    private List<Team> checkGetListTeam(List<TeExcelImportTeam> listInput, String idClass) {
        List<Team> lisTeam = teTeamsRepositoty.getTeamByClassId(idClass);
        if (listInput == null) {
            return lisTeam;
        }//teTeamsRepositoty.getNameNhomAuto(idClass)
        AtomicReference<Integer> nameCount = new AtomicReference<>(1);
        List<TeExcelImportTeam> filteredList = listInput.stream()
                .filter(student -> "X".equalsIgnoreCase(student.getRole()) &&
                        student.getNameTeam() != null && !student.getNameTeam().isEmpty())
                        .filter(Objects::nonNull)
                .collect(Collectors.toList());
        filteredList.forEach(item -> {
            Team teamExists = lisTeam.stream()
                    .filter(db -> item.getNameTeam().equalsIgnoreCase(db.getName()))
                    .findAny().orElse(null);
            if (teamExists == null) {
                String nameTeamUpdate = "Nhóm " + nameCount.getAndSet(nameCount.get() + 1);
                listInput.forEach(input -> {
                    if (input.getNameTeam().equals(item.getNameTeam())) {
                        TeExcelImportTeam ex = new TeExcelImportTeam();
                        ex.setName(input.getName());
                        ex.setEmail(input.getEmail());
                        ex.setRole(input.getRole());
                        ex.setNameTeam(nameTeamUpdate);
                        ex.setSubjectTeam(input.getSubjectTeam());
                        listTeamImportNew.add(ex);
                    }
                });
                item.setNameTeam(nameTeamUpdate);
            } else {
                String nameTeamUpdate = "Nhóm " + nameCount.getAndSet(nameCount.get() + 1);
                listInput.forEach(input -> {
                    if (input.getNameTeam().equals(item.getNameTeam())) {
                        TeExcelImportTeam ex = new TeExcelImportTeam();
                        ex.setName(input.getName());
                        ex.setEmail(input.getEmail());
                        ex.setRole(input.getRole());
                        ex.setNameTeam(nameTeamUpdate);
                        ex.setSubjectTeam(input.getSubjectTeam());
                        listTeamImportNew.add(ex);
                    }
                });
                item.setNameTeam(item.getNameTeam());
            }
        });
        listInput.forEach(input -> {
            if (input.getNameTeam().equals("")) {
                TeExcelImportTeam ex = new TeExcelImportTeam();
                ex.setName(input.getName());
                ex.setEmail(input.getEmail());
                ex.setRole("1");
                ex.setNameTeam("");
                ex.setSubjectTeam("");
                listTeamImportNew.add(ex);
            }
        });
        Map<String, Team> teamMap = lisTeam.stream()
                .collect(Collectors.toMap(Team::getName, team -> team));
        for (TeExcelImportTeam teamFind : filteredList) {
            if (teamMap.get(teamFind.getNameTeam()) != null) {
                Team matchingTeam = teamMap.get(teamFind.getNameTeam());
                matchingTeam.setSubjectName(teamFind.getSubjectTeam());
                teamMap.put(matchingTeam.getName(), matchingTeam);
            } else {
                Team newTeam = new Team();
                newTeam.setClassId(idClass);
                newTeam.setName(teamFind.getNameTeam());
                newTeam.setSubjectName(teamFind.getSubjectTeam());
                teamMap.put(newTeam.getName(), newTeam);
            }
        }
        List<Team> updatedTeams = new ArrayList<>(teamMap.values());
        List<Team> updatedTeamsNE = teTeamsRepositoty.saveAll(updatedTeams);
        List<Team> missingTeams = updatedTeams.stream()
                .filter(team -> !filteredList.stream().anyMatch(student -> student.getNameTeam().equalsIgnoreCase(team.getName())))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        teTeamsRepositoty.deleteAll(missingTeams);
        listInput.clear();
        return teTeamsRepositoty.getTeamByClassId(idClass);
    }

    private void addDataStudentDB(ConcurrentHashMap<String, StudentClasses> map, String idClass) {
        List<StudentClasses> listStudent = teStudentClassesRepository.findStudentClassesByIdClass(idClass);
        getAllPutAllInforStudentDB(map, listStudent);
    }

    private void getAllPutAllInforStudentDB
            (ConcurrentHashMap<String, StudentClasses> map, List<StudentClasses> list) {
        for (StudentClasses student : list) {
            map.put(student.getEmail(), student);
        }
        list.clear();
    }

    private void addDataTeam(ConcurrentHashMap<String, Team> mapTeam, List<Team> listTeam) {
        getAllPutTeam(mapTeam, listTeam);
    }

    private void getAllPutTeam(ConcurrentHashMap<String, Team> mapTeam, List<Team> listTeam) {
        for (Team team : listTeam) {
            mapTeam.put(team.getName(), team);
        }
        listTeam.clear();
    }
}
