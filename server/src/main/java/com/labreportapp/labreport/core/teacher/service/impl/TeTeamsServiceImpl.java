package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.model.request.TeCreateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.request.TeTeamUpdateStudentClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateTeamsRequest;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<StudentClasses> studentClassesNew = new ArrayList<>();
        studentClassesRequest.forEach(item -> {
            StudentClasses studentClasses = teStudentClassesRepository.findStudentClassesById(item.getIdStudentClass());
            if (studentClasses != null) {
                if ("0".equals(item.getRole())) {
                    studentClasses.setRole(RoleTeam.LEADER);
                } else {
                    studentClasses.setRole(RoleTeam.MEMBER);
                }
                studentClasses.setClassId(request.getClassId());
                studentClasses.setTeamId(teamCreate.getId());
                studentClassesNew.add(studentClasses);
            }
        });
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

    public String codeSplit(String name, String username) {
        int countSpace = (name.split(" ", -1).length - 1);
        int lastSpaceIndex = name.lastIndexOf(" ");
        int wordCount = (lastSpaceIndex >= 0) ? (name.substring(lastSpaceIndex + 1).length()) : 0;
        int nameIndexCut = countSpace + wordCount;
        String codeShow = username.substring(nameIndexCut).toUpperCase();
        return codeShow;
    }

    @Override
    @Synchronized
    public ByteArrayOutputStream exportExcelTeam(HttpServletResponse response, String idClass) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchApiStudentClassesByIdClass(idClass);
            Class objClass = teClassRepository.findById(idClass).get();
            Sheet sheet = workbook.createSheet("Danh sách");
            Row titleRow = sheet.createRow(0);
            Cell cellTitle = titleRow.createCell(0);
            cellTitle.setCellValue("DANH SÁCH NHÓM TRONG LỚP " + objClass.getCode());
            cellTitle.setCellStyle(chooseCellStyle("title",workbook));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            Row headerRow = sheet.createRow(1);
            Cell cell0 = headerRow.createCell(0);
            cell0.setCellValue("STT");
            cell0.setCellStyle(chooseCellStyle("titleTable",workbook));
            Cell cell2 = headerRow.createCell(1);
            cell2.setCellValue("Họ và tên");
            cell2.setCellStyle(chooseCellStyle("titleTable",workbook));
            Cell cell3 = headerRow.createCell(2);
            cell3.setCellValue("Email");
            cell3.setCellStyle(chooseCellStyle("titleTable",workbook));
            Cell cell4 = headerRow.createCell(3);
            cell4.setCellValue("Vai trò");
            cell4.setCellStyle(chooseCellStyle("titleTable",workbook));
            Cell cell5 = headerRow.createCell(4);
            cell5.setCellValue("Nhóm");
            cell5.setCellStyle(chooseCellStyle("titleTable",workbook));
            Cell cell6 = headerRow.createCell(5);
            cell6.setCellValue("Chủ đề");
            cell6.setCellStyle(chooseCellStyle("titleTable",workbook));
            int rowIndex = 2;
            int index = 1;
            for (TeStudentCallApiResponse data : listStudent) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(index++);
                dataRow.getCell(0).setCellStyle(chooseCellStyle("dataCenterTable",workbook));
                dataRow.createCell(1).setCellValue(data.getName());
                dataRow.getCell(1).setCellStyle(chooseCellStyle("dataTable",workbook));
                dataRow.createCell(2).setCellValue(data.getEmail());
                dataRow.getCell(2).setCellStyle(chooseCellStyle("dataTable",workbook));
                dataRow.createCell(3).setCellValue(data.getRole().equals("0") ? "X" : "");
                dataRow.getCell(3).setCellStyle(chooseCellStyle("dataCenterTable",workbook));
                dataRow.createCell(4).setCellValue(data.getNameTeam());
                dataRow.getCell(4).setCellStyle(chooseCellStyle("dataTable",workbook));
                dataRow.createCell(5).setCellValue(data.getSubjectName());
                dataRow.getCell(5).setCellStyle(chooseCellStyle("dataTable",workbook));
            }
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i,true);
            }
            workbook.write(response.getOutputStream());
            workbook.close();
            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        return cellStyle;
    }
}
