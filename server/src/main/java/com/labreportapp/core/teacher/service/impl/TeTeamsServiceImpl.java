package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeCreateTeamsRequest;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.request.TeTeamUpdateStudentClassRequest;
import com.labreportapp.core.teacher.model.request.TeUpdateTeamsRequest;
import com.labreportapp.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.core.teacher.repository.TeTeamsRepositoty;
import com.labreportapp.core.teacher.service.TeTeamsService;
import com.labreportapp.entity.StudentClasses;
import com.labreportapp.entity.Team;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.constant.RoleTeam;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    @Synchronized
    public void exportExcelTeam(HttpServletResponse response, String idClass) {
//        try (Workbook workbook = new XSSFWorkbook()) {
//            response.setContentType("application/octet-stream");
//            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//            String currentDateTime = dateFormatter.format(new Date());
//            String headerKey = "Content-Disposition";
//            String headerValue = "attachment; filename=BangDiem_" + currentDateTime + ".xlsx";
//            response.setHeader(headerKey, headerValue);
//            List<TePointRespone> listPointIdClass = getPointStudentById(idClass);
//            TeFindStudentClasses teFindStudentClasses = new TeFindStudentClasses();
//            teFindStudentClasses.setIdClass(idClass);
//            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchStudentClassesByIdClass(teFindStudentClasses);
//            List<TePointExcel> listExcel = new ArrayList<>();
//            listPointIdClass.forEach((item1) -> {
//                listStudent.forEach((item2) -> {
//                    if (item2.getIdStudent().equals(item1.getIdStudent())) {
//                        TePointExcel point = new TePointExcel();
//                        point.setIdPoint(item1.getId());
//                        point.setIdStudent(item1.getIdStudent());
//                        point.setName(item2.getName());
//                        point.setEmail(item2.getEmail());
//                        point.setCheckPointPhase1(item1.getCheckPointPhase1());
//                        point.setCheckPointPhase2(item1.getCheckPointPhase2());
//                        point.setFinalPoint(item1.getFinalPoint());
//                        listExcel.add(point);
//                    }
//                });
//            });
//            Sheet sheet = workbook.createSheet("Bảng điểm");
//            CellStyle headerStyle = workbook.createCellStyle();
//            Font font = workbook.createFont();
//            font.setBold(true);
//            font.setColor(IndexedColors.WHITE.getIndex());
//            font.setFontHeightInPoints((short) 13);
//            headerStyle.setAlignment(HorizontalAlignment.CENTER);
//            headerStyle.setFont(font);
//            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            sheet.setColumnWidth(0, 2000);
//            sheet.setColumnWidth(1, 8000);
//            sheet.setColumnWidth(2, 8000);
//            sheet.setColumnWidth(3, 7000);
//            sheet.setColumnWidth(4, 7000);
//            sheet.setColumnWidth(5, 7000);
//            Row headerRow = sheet.createRow(0);
//            Cell cell0 = headerRow.createCell(0);
//            cell0.setCellValue("STT");
//            cell0.setCellStyle(headerStyle);
//            Cell cell1 = headerRow.createCell(1);
//            cell1.setCellValue("Tên sinh viên");
//            cell1.setCellStyle(headerStyle);
//            Cell cell2 = headerRow.createCell(2);
//            cell2.setCellValue("Email");
//            cell2.setCellStyle(headerStyle);
//            Cell cell3 = headerRow.createCell(3);
//            cell3.setCellValue("Điểm giai đoạn 1");
//            cell3.setCellStyle(headerStyle);
//            Cell cell4 = headerRow.createCell(4);
//            cell4.setCellValue("Điểm giai đoạn 2");
//            cell4.setCellStyle(headerStyle);
//            Cell cell5 = headerRow.createCell(5);
//            cell5.setCellValue("Điểm giai đoạn cuối");
//            cell5.setCellStyle(headerStyle);
//            int rowIndex = 1;
//            int index = 1;
//            for (TePointExcel data : listExcel) {
//                Row dataRow = sheet.createRow(rowIndex++);
//                dataRow.createCell(0).setCellValue(index++);
//                dataRow.createCell(1).setCellValue(data.getName());
//                dataRow.createCell(2).setCellValue(data.getEmail());
//                dataRow.createCell(3).setCellValue(data.getCheckPointPhase1());
//                dataRow.createCell(4).setCellValue(data.getCheckPointPhase2());
//                dataRow.createCell(5).setCellValue(data.getFinalPoint());
//            }
//            workbook.write(response.getOutputStream());
//            workbook.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
