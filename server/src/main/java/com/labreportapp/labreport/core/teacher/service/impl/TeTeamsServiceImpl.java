package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.excel.TeExcelImportService;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportTeam;
import com.labreportapp.labreport.core.teacher.model.request.TeCreateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.request.TeTeamUpdateStudentClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.labreport.core.teacher.repository.TeTeamsRepositoty;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.core.teacher.service.TeTeamsService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.entity.Team;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
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

    @Override
    public List<TeTeamsRespone> getAllTeams(final TeFindStudentClasses teFindStudentClasses) {
        return teTeamsRepositoty.findTeamsByIdClass(teFindStudentClasses);
    }

    @Override
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
        team.setCode(request.getCode());
        team.setName(request.getName());
        team.setSubjectName(request.getSubjectName());
        team.setClassId(request.getClassId());
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
//        if (studentClassesNew.size() == 1) {
//            StudentClasses studentClassesOne = studentClassesNew.get(0);
//            studentClassesOne.setRole(RoleTeam.LEADER);
//        }
        teStudentClassesRepository.saveAll(studentClassesNew);
        return teamCreate;
    }

    @Override
    public Team updateTeam(@Valid TeUpdateTeamsRequest request) {
        Optional<Team> teamFilter = teTeamsRepositoty.findById(request.getId());
        if (teamFilter == null) {
            throw new RestApiException(Message.TEAM_NOT_EXISTS);
        }
        Team team = teamFilter.get();
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
        team.setCode(request.getCode());
        team.setName(request.getName());
        team.setSubjectName(request.getSubjectName());
        List<StudentClasses> studentClassesNew = new ArrayList<>();
        studentClassesRequest.forEach(item -> {
            StudentClasses studentClasses = teStudentClassesRepository.findStudentClassesById(item.getIdStudentClass());
            if (studentClasses != null) {
                if ("0".equals(item.getRole())) {
                    studentClasses.setRole(RoleTeam.LEADER);
                } else {
                    studentClasses.setRole(RoleTeam.MEMBER);
                }
                studentClasses.setTeamId(team.getId());
                studentClassesNew.add(studentClasses);
            }
        });
        teStudentClassesRepository.saveAll(studentClassesNew);
        List<StudentClasses> studentClassesDeleteId = new ArrayList<>();
        List<TeTeamUpdateStudentClassRequest> studentClassesDeleteIdTeamRequest = request.getListStudentClassesDeleteIdTeam();
        studentClassesDeleteIdTeamRequest.forEach(item -> {
            StudentClasses studentClasses = teStudentClassesRepository.findStudentClassesById(item.getIdStudentClass());
            if (studentClasses != null) {
                studentClasses.setTeamId(null);
                studentClassesDeleteId.add(studentClasses);
            }
        });
        teStudentClassesRepository.saveAll(studentClassesDeleteId);
        return teTeamsRepositoty.save(team);
    }

    @Override
    public String deleteTeamById(String idTeam) {
        Team team = teTeamsRepositoty.findById(idTeam).get();
        if (team != null) {
            List<StudentClasses> listStudentClass = teStudentClassesRepository.findAllStudentClassesByIdTeam(idTeam);
            listStudentClass.forEach(item -> {
                item.setTeamId(null);
                teStudentClassesRepository.save(item);
            });
            teTeamsRepositoty.delete(team);
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
                dataRow.createCell(3).setCellValue(data.getRole().equals("0") ? "X" : "");
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

    @Override
    public TeExcelResponseMessage importExcelTeam(MultipartFile file, String idClass) {
        TeExcelResponseMessage teExcelResponseMessage = new TeExcelResponseMessage();
        try {
            List<TeExcelImportTeam> listInput = teExcelImportService.importDataTeam(file, idClass);
            List<StudentClasses> listStudentUp = new ArrayList<>();
            if (listInput.size() == 0) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("file excel trống");
                return teExcelResponseMessage;
            }
            List<Team> listTeamDB = checkGetListTeam(listInput, idClass);
            ConcurrentHashMap<String, StudentClasses> mapStudent = new ConcurrentHashMap<>();
            addDataStudentDB(mapStudent, idClass);
            if (listInput.size() != mapStudent.size()) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("số lượng sinh viên trong file excel phải bằng với số lượng sinh viên trong lớp");
                return teExcelResponseMessage;
            }
            teExcelResponseMessage.setStatus(true);
            Map<String, String> teamRoles = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, Team> mapTeam = new ConcurrentHashMap<>();
            addDataTeam(mapTeam, listTeamDB);
            listInput.parallelStream().forEach(student -> {
                String regexRole = "^[Xx]?$";
                String regexName = "^[^!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/\\\\]*$";
                String regexEmailExactly = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                if (student.getName().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("tên sinh viên không được để trống");
                    return;
                }
                if (!student.getName().matches(regexName)) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("tên sinh viên sai định dạng");
                    return;
                }
                if (student.getEmail().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("email không được để trống");
                    return;
                }
                if (!student.getEmail().matches(regexEmailExactly)) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("email sai định dạng");
                    return;
                }
                if (!student.getRole().matches(regexRole)) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("vai trò chỉ được nhập X hoặc để trống");
                    return;
                }
                if ("X".equalsIgnoreCase(student.getRole())) {
                    String existingRole = teamRoles.put(student.getNameTeam(), "X");
                    if ("X".equalsIgnoreCase(existingRole)) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage("Có hai sinh viên làm leader trong cùng một nhóm");
                        return;
                    }
                }
                StudentClasses studentFind = mapStudent.get(student.getEmail());
                if (studentFind == null) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("email của sinh viên không tồn tại");
                    return;
                } else {
                    Team teamFind = mapTeam.get(student.getNameTeam());
                    if (teamFind != null) {
                        studentFind.setTeamId(teamFind.getId());
                        studentFind.setRole(student.getRole().equalsIgnoreCase("X") ? RoleTeam.LEADER : RoleTeam.MEMBER);
                        listStudentUp.add(studentFind);
                    }
                }
            });
            if (teExcelResponseMessage.getStatus() == true) {
                teStudentClassesRepository.saveAll(listStudentUp);
                return teExcelResponseMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            teExcelResponseMessage.setStatus(false);
            teExcelResponseMessage.setMessage("lỗi hệ thống");
            return teExcelResponseMessage;
        }
        return teExcelResponseMessage;
    }

    private List<Team> checkGetListTeam(List<TeExcelImportTeam> listRequest, String idClass) {
        List<Team> lisTeam = teTeamsRepositoty.getTeamByClassId(idClass);
        if (listRequest == null) {
            return lisTeam;
        }
        List<TeExcelImportTeam> filteredList = listRequest.stream()
                .filter(student -> "X".equalsIgnoreCase(student.getRole()) && student.getNameTeam() != null && !student.getNameTeam().isEmpty())
                .collect(Collectors.toList());
        Map<String, Team> teamMap = lisTeam.stream()
                .collect(Collectors.toMap(Team::getName, team -> team));
        for (TeExcelImportTeam teamFind : filteredList) {
            if (teamMap.get(teamFind.getNameTeam()) != null) {
                Team matchingTeam = teamMap.get(teamFind.getNameTeam());
                matchingTeam.setSubjectName(teamFind.getSubjectTeam());
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
                .collect(Collectors.toList());
        teTeamsRepositoty.deleteAll(missingTeams);
        return updatedTeamsNE;
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
