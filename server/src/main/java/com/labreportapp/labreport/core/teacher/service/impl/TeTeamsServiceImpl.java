package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.excel.TeExcelImportService;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportTeam;
import com.labreportapp.labreport.core.teacher.model.request.TeCreateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.request.TeTeamUpdateStudentClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMemberProjectRepository;
import com.labreportapp.labreport.core.teacher.repository.TeProjectRepository;
import com.labreportapp.labreport.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.labreport.core.teacher.repository.TeTeamsRepositoty;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.core.teacher.service.TeTeamsService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.entity.Team;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.labreport.util.RandomString;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.RoleMemberProject;
import com.labreportapp.portalprojects.infrastructure.constant.StatusProject;
import com.labreportapp.portalprojects.infrastructure.constant.StatusWork;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Synchronized;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
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

    @Override
    public List<TeTeamsRespone> getAllTeams(final TeFindStudentClasses teFindStudentClasses) {
        return teTeamsRepositoty.findTeamsByIdClass(teFindStudentClasses);
    }

    @Override
    @Transactional
    public Team createTeam(@Valid TeCreateTeamsRequest request) {
        List<TeTeamUpdateStudentClassRequest> studentClassesRequest = request.getListStudentClasses();
        int checkLeader = 0;
        for (int i = 0; i < studentClassesRequest.size(); i++) {
            if (studentClassesRequest.get(i).getRole().equals("0") || studentClassesRequest.get(i).getRole() == "0") {
                checkLeader++;
            }
        }
        if (checkLeader > 1) {
            throw new RestApiException(Message.UNIQUE_LEADER_TEAM);
        }
        Team team = new Team();
        team.setName("Nhóm " + teTeamsRepositoty.getNameNhomAuto(request.getClassId()));
        team.setSubjectName(request.getSubjectName());
        team.setClassId(request.getClassId());
        Optional<TeDetailClassResponse> objClass = teClassRepository.findClassById(request.getClassId());
        if (objClass.isPresent()) {
            if (objClass.get().getAllowUseTrello() == 0) {
                Project project = new Project();
                project.setCode("PRJ_" + RandomString.random());
                project.setName("Project" + RandomString.random());
                project.setStartTime(new Date().getTime());
                project.setEndTime(new Date().getTime() + 90 * 86400000);
                project.setStatusProject(StatusProject.DANG_DIEN_RA);
                project.setBackgroundColor("rgb(38, 144, 214)");
                Project projectNew = teProjectRepository.save(project);
                team.setProjectId(projectNew.getId());
                TeFindStudentClasses teFindStudentClasses = new TeFindStudentClasses();
                teFindStudentClasses.setIdClass(request.getClassId());
                teFindStudentClasses.setIdTeam(team.getId());
                List<TeTeamUpdateStudentClassRequest> listStudentClasses = request.getListStudentClasses();
                List<MemberProject> listMemberProject = new ArrayList<>();
                if (listStudentClasses != null) {
                    listStudentClasses.forEach(student -> {
                        MemberProject memberProject = new MemberProject();
                        memberProject.setMemberId(student.getIdStudent());
                        memberProject.setEmail(student.getEmail());
                        memberProject.setProjectId(project.getId());
                        if (student.getRole() == null) {
                            memberProject.setRole(RoleMemberProject.DEV);
                        } else {
                            memberProject.setRole(student.getRole().equals("0") ? RoleMemberProject.MANAGER : RoleMemberProject.DEV);
                        }
                        memberProject.setStatusWork(StatusWork.DANG_LAM);
                        listMemberProject.add(memberProject);
                    });
                    teMemberProjectRepository.saveAll(listMemberProject);
                }
            }
        }
        Team teamCreate = teTeamsRepositoty.save(team);
        List<StudentClasses> listStudentClasses = teStudentClassesRepository.findStudentClassesByIdClass(request.getClassId());
        List<StudentClasses> studentClassesNew = new ArrayList<>();
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
                }
            });
        });
        teStudentClassesRepository.saveAll(studentClassesNew);
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
        for (int i = 0; i < studentClassesRequest.size(); i++) {
            if (studentClassesRequest.get(i).getRole().equals("0") || studentClassesRequest.get(i).getRole() == "0") {
                checkLeader++;
            }
        }
        if (checkLeader > 1) {
            throw new RestApiException(Message.UNIQUE_LEADER_TEAM);
        }
        team.setSubjectName(request.getSubjectName());
        Optional<TeDetailClassResponse> objClass = teClassRepository.findClassById(team.getClassId());
        ConcurrentHashMap<String, StudentClasses> mapStudent = new ConcurrentHashMap<>();
        addDataStudentDB(mapStudent, team.getClassId());
        if (objClass.isPresent()) {
            if (objClass.get().getAllowUseTrello() == 0) {
                List<MemberProject> listMemberProjectUpdate = new ArrayList<>();
                if (team.getProjectId() == null || team.getProjectId().equals("")) {
                    Project project = new Project();
                    project.setCode("PRJ_" + RandomString.random());
                    project.setName("Project" + RandomString.random());
                    project.setStartTime(new Date().getTime());
                    project.setEndTime(new Date().getTime() + 90 * 86400000);
                    project.setStatusProject(StatusProject.DANG_DIEN_RA);
                    project.setBackgroundColor("rgb(38, 144, 214)");
                    Project projectNew = teProjectRepository.save(project);
                    team.setProjectId(projectNew.getId());
                    List<MemberProject> listMemberFind = teMemberProjectRepository.findMemberProjectByProjectId(team.getProjectId());
                    studentClassesRequest.forEach(student -> {
                        MemberProject memberProjectExist = listMemberFind.stream()
                                .filter(db -> db.getMemberId().equals(mapStudent.get(student.getEmail()).getStudentId()))
                                .findAny().orElse(null);
                        MemberProject memberProject = new MemberProject();
                        memberProject.setMemberId(mapStudent.get(student.getEmail()).getStudentId());
                        memberProject.setEmail(student.getEmail());
                        memberProject.setProjectId(project.getId());
                        memberProject.setRole(student.getRole().equals("0") ? RoleMemberProject.MANAGER : RoleMemberProject.DEV);
                        memberProject.setStatusWork(StatusWork.DANG_LAM);
                        if (memberProjectExist != null) {
                            memberProject.setId(memberProjectExist.getId());
                        }
                        listMemberProjectUpdate.add(memberProject);

                    });
                    teMemberProjectRepository.saveAll(listMemberProjectUpdate);
                } else {
                    Optional<Project> projectFind = teProjectRepository.findById(team.getProjectId());
                    if (projectFind.isPresent()) {
                        ConcurrentHashMap<String, MemberProject> mapMember = new ConcurrentHashMap<>();
                        addDataMemberProject(mapMember, projectFind.get().getId());
                        studentClassesRequest.forEach(student -> {
                            StudentClasses studentClasses = mapStudent.get(student.getEmail());
                            if (studentClasses != null) {
                                MemberProject memberProject = mapMember.get(studentClasses.getStudentId());
                                if (memberProject == null) {
                                    MemberProject memberProjectNew = new MemberProject();
                                    memberProjectNew.setMemberId(mapStudent.get(student.getEmail()).getStudentId());
                                    memberProjectNew.setEmail(student.getEmail());
                                    memberProjectNew.setProjectId(projectFind.get().getId());
                                    memberProjectNew.setRole(student.getRole().equals("0") ? RoleMemberProject.MANAGER : RoleMemberProject.DEV);
                                    memberProjectNew.setStatusWork(StatusWork.DANG_LAM);
                                    listMemberProjectUpdate.add(memberProjectNew);
                                } else {
                                    memberProject.setId(memberProject.getId());
                                    memberProject.setMemberId(mapStudent.get(student.getEmail()).getStudentId());
                                    memberProject.setEmail(student.getEmail());
                                    memberProject.setProjectId(projectFind.get().getId());
                                    memberProject.setRole(student.getRole().equals("0") ? RoleMemberProject.MANAGER : RoleMemberProject.DEV);
                                    memberProject.setStatusWork(StatusWork.DANG_LAM);
                                    listMemberProjectUpdate.add(memberProject);
                                }
                            }
                        });
                        teMemberProjectRepository.saveAll(listMemberProjectUpdate);
                        if (studentClassesDeleteIdTeamRequest.size() != 0) {
                            List<MemberProject> listMemberFind = teMemberProjectRepository.findMemberProjectByProjectId(team.getProjectId());
                            List<MemberProject> listMemberDelete = new ArrayList<>();
                            studentClassesDeleteIdTeamRequest.forEach(student -> {
                                listMemberFind.forEach(member -> {
                                    if (member.getMemberId().equals(student.getIdStudent())) {
                                        MemberProject memberProjectDele = new MemberProject();
                                        memberProjectDele.setId(member.getId());
                                        listMemberDelete.add(memberProjectDele);
                                    }
                                });
                            });
                            teMemberProjectRepository.deleteAll(listMemberDelete);
                        }
                    }
                }
            }
        }
        List<StudentClasses> listStudentClassesUpdate = new ArrayList<>();
        List<StudentClasses> listStudentClass = teStudentClassesRepository.findStudentClassesByIdClass(team.getClassId());
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
                    studentClasses.setTeamId(team.getId());
                    listStudentClassesUpdate.add(studentClasses);
                }
            });
        });
        List<StudentClasses> listDeleteStudenClasses = new ArrayList<>();
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
                    listStudentClassesUpdate.add(st);
                }
            });
        });
        teStudentClassesRepository.saveAll(listStudentClassesUpdate);
        return teTeamsRepositoty.save(team);
    }

    @Override
    @Transactional
    public String deleteTeamById(String idTeam) {
        Team team = teTeamsRepositoty.findById(idTeam).get();
        if (team != null) {
            List<StudentClasses> listStudentClass = teStudentClassesRepository.findAllStudentClassesByIdTeam(idTeam);
            listStudentClass.forEach(item -> {
                item.setTeamId(null);
                teStudentClassesRepository.save(item);
            });
            teTeamsRepositoty.delete(team);
            if (team.getProjectId() != null) {
                Optional<Project> projectFind = teProjectRepository.findById(team.getProjectId());
                if (projectFind.isPresent()) {
                    List<MemberProject> listMemberFind = teMemberProjectRepository.findMemberProjectByProjectId(team.getProjectId());
                    if (listMemberFind.size() != 0) {
                        teMemberProjectRepository.deleteAll(listMemberFind);
                    }
                    teProjectRepository.delete(projectFind.get());
                }
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
        }
        if (type.equals("dataCenterTable")) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        }
        if (type.equals("note")) {
            fontStyle.setColor(Font.COLOR_RED);
            fontStyle.setFontHeightInPoints((short) 11);
            cellStyle.setFont(fontStyle);
        }
        return cellStyle;
    }

    private List<TeExcelImportTeam> listTeamImportNew = new ArrayList<>();

    @Override
    @Transactional
    @Synchronized
    public TeExcelResponseMessage importExcelTeam(MultipartFile file, String idClass) {
        TeExcelResponseMessage teExcelResponseMessage = new TeExcelResponseMessage();
        try {
            List<TeExcelImportTeam> listInput = teExcelImportService.importDataTeam(file, idClass);
            List<StudentClasses> listStudentUp = new ArrayList<>();
            if (listInput.size() == 0) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("File excel trống, vui lòng export lại excel để sử dụng import !");
                return teExcelResponseMessage;
            }
            ConcurrentHashMap<String, StudentClasses> mapStudent = new ConcurrentHashMap<>();
            addDataStudentDB(mapStudent, idClass);
            if (listInput.size() != mapStudent.size()) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("Import thất bại. Số lượng sinh viên trong file excel phải bằng với số lượng sinh viên trong lớp !");
                return teExcelResponseMessage;
            }
            teExcelResponseMessage.setStatus(true);
            Map<String, String> teamRoles = new ConcurrentHashMap<>();
            AtomicBoolean checkValidate = new AtomicBoolean(true);
            AtomicBoolean checkDuplication = new AtomicBoolean(false);
            teExcelResponseMessage.setMessage("");
            listInput.parallelStream().forEach(student -> {
                String regexRole = "^[Xx]?$";
                String regexName = "^[^!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/\\\\]*$";
                String regexEmailExactly = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                if (student.getName().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Tên sinh viên không được để trống.");
                    checkValidate.set(false);
                    return;
                } else {
                    if (!student.getName().matches(regexName)) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(" Tên sinh viên sai định dạng.");
                        checkValidate.set(false);
                        return;
                    }
                }
                if (student.getEmail().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Email không được để trống.");
                    checkValidate.set(false);
                    return;
                } else {
                    if (!student.getEmail().matches(regexEmailExactly)) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(teExcelResponseMessage.getMessage() + " Email sai định dạng.");
                        checkValidate.set(false);
                        return;
                    }
                }
                if (!student.getRole().matches(regexRole)) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(teExcelResponseMessage.getMessage() +
                            " Vai trò chỉ được nhập X hoặc để trống.");
                    checkValidate.set(false);
                    return;
                } else if ("X".equalsIgnoreCase(student.getRole())) {
                    String existingRole = teamRoles.put(student.getNameTeam(), "X");
                    if ("X".equalsIgnoreCase(existingRole)) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(teExcelResponseMessage.getMessage() +
                                " Có hai sinh viên làm leader trong cùng một nhóm.");
                        checkValidate.set(false);
                        return;
                    } else if (student.getNameTeam().isEmpty()) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(teExcelResponseMessage.getMessage() +
                                " Tên nhóm không được để trống nếu sinh viên có vai trò là trưởng nhóm.");
                        checkValidate.set(false);
                        return;
                    }
                } else {
                    StudentClasses studentFind = mapStudent.get(student.getEmail());
                    if (studentFind == null) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(teExcelResponseMessage.getMessage() +
                                " Email của sinh viên không tồn tại.");
                        checkValidate.set(false);
                        return;
                    }
                }
            });
            if (checkValidate.get()) {
                List<Team> listTeamDB = checkGetListTeam(listInput, idClass);
                ConcurrentHashMap<String, Team> mapTeam = new ConcurrentHashMap<>();
                addDataTeam(mapTeam, listTeamDB);
                teExcelResponseMessage.setMessage("");
                listTeamImportNew.parallelStream().forEach(student -> {
                    StudentClasses studentFind = mapStudent.get(student.getEmail());
                    Team teamFind = mapTeam.get(student.getNameTeam());
                    studentFind.setTeamId(teamFind != null ? teamFind.getId() : null);
                    studentFind.setRole(student.getRole().equalsIgnoreCase("X") ? RoleTeam.LEADER : RoleTeam.MEMBER);
                    listStudentUp.add(studentFind);
                });
            }
            if (teExcelResponseMessage.getStatus() == true) {
                teStudentClassesRepository.saveAll(listStudentUp);
                teExcelResponseMessage.setMessage("Import nhóm thành công !");
                return teExcelResponseMessage;
            } else {
                teExcelResponseMessage.setMessage("Import nhóm thất bại. " + teExcelResponseMessage.getMessage());
                return teExcelResponseMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            teExcelResponseMessage.setStatus(false);
            teExcelResponseMessage.setMessage("Lỗi hệ thống, vui lòng F5 lại trang !");
            return teExcelResponseMessage;
        }
    }

    private List<Team> checkGetListTeam(List<TeExcelImportTeam> listInput, String idClass) {
        List<Team> lisTeam = teTeamsRepositoty.getTeamByClassId(idClass);
        if (listInput == null) {
            return lisTeam;
        }
        AtomicReference<Integer> nameCount = new AtomicReference<>(teTeamsRepositoty.getNameNhomAuto(idClass));
        List<TeExcelImportTeam> filteredList = listInput.stream()
                .filter(student -> "X".equalsIgnoreCase(student.getRole()) &&
                        student.getNameTeam() != null && !student.getNameTeam().isEmpty())
                .collect(Collectors.toList());
        filteredList.forEach(item -> {
            Team teamExists = lisTeam.stream()
                    .filter(db -> item.getNameTeam().equalsIgnoreCase(db.getName()))
                    .findAny().orElse(null);
            String nameTeam = item.getNameTeam();
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
                listInput.forEach(input -> {
                    if (input.getNameTeam().equals(item.getNameTeam())) {
                        TeExcelImportTeam ex = new TeExcelImportTeam();
                        ex.setName(input.getName());
                        ex.setEmail(input.getEmail());
                        ex.setRole(input.getRole());
                        ex.setNameTeam(item.getNameTeam());
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
        createProjectAndAddMember(listInput, updatedTeams, idClass);
        List<Team> updatedTeamsNE = teTeamsRepositoty.saveAll(updatedTeams);
        List<Team> missingTeams = updatedTeams.stream()
                .filter(team -> !filteredList.stream().anyMatch(student -> student.getNameTeam().equalsIgnoreCase(team.getName())))
                .collect(Collectors.toList());
        teTeamsRepositoty.deleteAll(missingTeams);
        return updatedTeamsNE;
    }

    private void createProjectAndAddMember(List<TeExcelImportTeam> listInput, List<Team> listTeam, String idClass) {
        ConcurrentHashMap<String, StudentClasses> mapStudent = new ConcurrentHashMap<>();
        addDataStudentDB(mapStudent, idClass);
        Optional<TeDetailClassResponse> objClass = teClassRepository.findClassById(idClass);
        if (objClass.isPresent()) {
            if (objClass.get().getAllowUseTrello() == 0) {
                List<MemberProject> listMemberProjectUpdate = new ArrayList<>();
                listTeam.forEach(team -> {
                    if (team.getProjectId() == null || team.getProjectId().equals("")) {
                        Project project = new Project();
                        project.setCode("PRJ_" + RandomString.random());
                        project.setName("Project" + RandomString.random());
                        project.setStartTime(new Date().getTime());
                        project.setEndTime(new Date().getTime() + 90 * 86400000);
                        project.setStatusProject(StatusProject.DANG_DIEN_RA);
                        project.setBackgroundColor("rgb(38, 144, 214)");
                        Project projectNew = teProjectRepository.save(project);
                        team.setProjectId(projectNew.getId());
                        listInput.forEach(student -> {
                            if (student.getNameTeam().equals(team.getName())) {
                                MemberProject memberProject = new MemberProject();
                                memberProject.setMemberId(mapStudent.get(student.getEmail()).getStudentId());
                                memberProject.setEmail(student.getEmail());
                                memberProject.setProjectId(project.getId());
                                memberProject.setRole(student.getRole().equals("0") ? RoleMemberProject.MANAGER : RoleMemberProject.DEV);
                                memberProject.setStatusWork(StatusWork.DANG_LAM);
                                listMemberProjectUpdate.add(memberProject);
                            }
                        });
                    } else {
                        Optional<Project> projectFind = teProjectRepository.findById(team.getProjectId());
                        if (projectFind.isPresent()) {
                            ConcurrentHashMap<String, MemberProject> mapMember = new ConcurrentHashMap<>();
                            addDataMemberProject(mapMember, projectFind.get().getId());
                            listInput.forEach(student -> {
                                if (student.getNameTeam().equals(team.getName())) {
                                    StudentClasses studentClasses = mapStudent.get(student.getEmail());
                                    if (studentClasses != null) {
                                        MemberProject memberProject = mapMember.get(studentClasses.getStudentId());
                                        if (memberProject == null) {
                                            memberProject.setMemberId(mapStudent.get(student.getEmail()).getStudentId());
                                            memberProject.setEmail(student.getEmail());
                                            memberProject.setProjectId(projectFind.get().getId());
                                            memberProject.setRole(student.getRole().equals("0") ? RoleMemberProject.MANAGER : RoleMemberProject.DEV);
                                            memberProject.setStatusWork(StatusWork.DANG_LAM);
                                        } else {
                                            memberProject.setRole(student.getRole().equals("0") ? RoleMemberProject.MANAGER : RoleMemberProject.DEV);
                                        }
                                        listMemberProjectUpdate.add(memberProject);
                                    }
                                }
                            });
                        }
                    }
                });
                teMemberProjectRepository.saveAll(listMemberProjectUpdate);
            }
        }
//        else {
//            return false;
//        }
//        return true;
    }

    private void addDataMemberProject(ConcurrentHashMap<String, MemberProject> map, String idProject) {
        List<MemberProject> listMember = teMemberProjectRepository.findMemberProjectByProjectId(idProject);
        getAllPutAllMemberProject(map, listMember);
    }

    private void getAllPutAllMemberProject
            (ConcurrentHashMap<String, MemberProject> map, List<MemberProject> list) {
        for (MemberProject student : list) {
            map.put(student.getMemberId(), student);
        }
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
    }

    private void addDataTeam(ConcurrentHashMap<String, Team> mapTeam, List<Team> listTeam) {
        getAllPutTeam(mapTeam, listTeam);
    }

    private void getAllPutTeam(ConcurrentHashMap<String, Team> mapTeam, List<Team> listTeam) {
        for (Team team : listTeam) {
            mapTeam.put(team.getName(), team);
        }
    }
}
