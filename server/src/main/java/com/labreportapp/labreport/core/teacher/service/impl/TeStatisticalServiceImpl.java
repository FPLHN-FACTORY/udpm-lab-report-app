package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.model.request.TeFindClassStatisticalRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeCountClassReponse;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeamColor;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.labreport.core.teacher.repository.TeTeamsRepositoty;
import com.labreportapp.labreport.core.teacher.service.TeStatisticalService;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.Team;
import com.labreportapp.labreport.util.SemesterHelper;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.Random;

/**
 * @author hieundph25894
 */
@Service
public class TeStatisticalServiceImpl implements TeStatisticalService {

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private TeTeamsRepositoty teTeamsRepositoty;

    @Autowired
    private TeMeetingRepository teMeetingRepository;

    public short getRandomValidBrightColor(List<Short> listColor) {
        Random random = new Random();
        int randomIndex = random.nextInt(listColor.size());
        return listColor.get(randomIndex);
    }

    public List<Short> getExcelColors() {
        List<Short> excelColors = new ArrayList<>();
        for (IndexedColors color : IndexedColors.values()) {
            short index = color.getIndex();
            if (index != IndexedColors.BLACK.getIndex() && index != IndexedColors.WHITE.getIndex()
                    && index != IndexedColors.AUTOMATIC.getIndex()) {
                excelColors.add(index);
            }
        }
        return excelColors;
    }

    public short getColorForTeamId(String teamId, List<TeamColor> listTeamColor) {
        for (TeamColor item : listTeamColor) {
            if (teamId != null && !teamId.isEmpty() && teamId.equals(item.getId())) {
                return item.getColor();
            }
        }
        return IndexedColors.WHITE.getIndex();
    }

    @Override
    @Synchronized
    public ByteArrayOutputStream exportExcelStatistical(HttpServletResponse response, String idClass) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            List<Team> listTeam = teTeamsRepositoty.getTeamByClassId(idClass);
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchApiStudentClassesByIdClass(idClass);
            TeFindMeetingRequest request = new TeFindMeetingRequest();
            request.setIdClass(idClass);
            List<TeMeetingResponse> listMeeting = teMeetingRepository.findMeetingByIdClass(request);
            List<TeamColor> listTeamColor = new ArrayList<>();
            List<Short> listShortColor = getExcelColors();
            listTeam.forEach(team -> {
                TeamColor color = new TeamColor();
                color.setId(team.getId());
                color.setCode(team.getCode());
                color.setName(team.getName());
                color.setSubjectName(team.getSubjectName());
                color.setClassId(team.getClassId());
                color.setProjectId(team.getProjectId());
                color.setColor(getRandomValidBrightColor(listShortColor));
                listShortColor.removeIf(item -> item.equals(color.getColor()));
                listTeamColor.add(color);
            });
            List<TeHomeWorkAndNoteMeetingResponse> listHwNoteReport = teMeetingRepository.searchHwNoteReport(idClass);
            Class objClass = teClassRepository.findById(idClass).get();
            Sheet sheet = configTitle(workbook, objClass.getCode());
            int rowIndex = 3;
            int index = 1;
            for (TeStudentCallApiResponse data : listStudent) {
                Row dataRow = sheet.createRow(rowIndex++);

                dataRow.createCell(0).setCellValue(index++);
                dataRow.getCell(0).setCellStyle(chooseCellStyle("dataCenterTableColor", workbook,
                        getColorForTeamId(data.getIdTeam(), listTeamColor)));

                dataRow.createCell(1).setCellValue(data.getName());
                dataRow.getCell(1).setCellStyle(data.getRole() != null && data.getRole().equals("0") ?
                        chooseCellStyle("dataTableLead", workbook, getColorForTeamId(data.getIdTeam(), listTeamColor)) :
                        chooseCellStyle("dataTable", workbook, getColorForTeamId(data.getIdTeam(), listTeamColor))
                );
                dataRow.createCell(2).setCellValue(data.getEmail());
                dataRow.getCell(2).setCellStyle(chooseCellStyle("dataTable", workbook,
                        getColorForTeamId(data.getIdTeam(), listTeamColor)));

                dataRow.createCell(3).setCellValue(data.getStatusStudent() != null
                        && data.getStatusStudent().equals("0") ? "Đạt" : "Trượt");
                dataRow.getCell(3).setCellStyle(data.getStatusStudent() != null
                        && data.getStatusStudent().equals("0") ? chooseCellStyle("dat", workbook,
                        getColorForTeamId(data.getIdTeam(), listTeamColor)) : chooseCellStyle("truot", workbook,
                        getColorForTeamId(data.getIdTeam(), listTeamColor)));
                dataRow.createCell(4).setCellValue(data.getNameTeam());
                dataRow.getCell(4).setCellStyle(chooseCellStyle("dataCenterTable", workbook, IndexedColors.WHITE.getIndex()));
                dataRow.createCell(5).setCellValue(data.getSubjectName());
                dataRow.getCell(5).setCellStyle(chooseCellStyle("dataCenterTable", workbook, IndexedColors.WHITE.getIndex()));

//                sheet.addMergedRegion(new CellRangeAddress(
//                        rowIndex++, rowIndex+3, 5, 5));
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
        Font fontTitle = workbook.createFont();
        Sheet sheet = workbook.createSheet(name);
        Row titleRow = sheet.createRow(0);
        Cell cellTitle = titleRow.createCell(0);
        cellTitle.setCellValue("THỐNG KÊ CHI TIẾT LỚP " + name);
        cellTitle.setCellStyle(chooseCellStyle("title", workbook, IndexedColors.WHITE.getIndex()));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 8000);
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 7000);
        Row headerRow = sheet.createRow(2);
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("STT");
        cell0.setCellStyle(chooseCellStyle("titleTable", workbook, IndexedColors.WHITE.getIndex()));
        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("Họ tên");
        cell1.setCellStyle(chooseCellStyle("titleTable", workbook, IndexedColors.WHITE.getIndex()));
        Cell cell2 = headerRow.createCell(2);
        cell2.setCellValue("Email");
        cell2.setCellStyle(chooseCellStyle("titleTable", workbook, IndexedColors.WHITE.getIndex()));

        Cell cell3 = headerRow.createCell(3);
        cell3.setCellValue("Trạng thái");
        cell3.setCellStyle(chooseCellStyle("titleTable", workbook, IndexedColors.WHITE.getIndex()));

        Cell cell4 = headerRow.createCell(4);
        cell4.setCellValue("Tên nhóm");
        cell4.setCellStyle(chooseCellStyle("titleTable", workbook, IndexedColors.WHITE.getIndex()));

        Cell cell5 = headerRow.createCell(5);
        cell5.setCellValue("Chủ đề");
        cell5.setCellStyle(chooseCellStyle("titleTable", workbook, IndexedColors.WHITE.getIndex()));
        return sheet;
    }

    private CellStyle chooseCellStyle(String type, Workbook workbook, short color) {
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
            cellStyle.setFillForegroundColor(color);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("dataTableLead")) {
            fontStyle.setBold(true);
            cellStyle.setFillForegroundColor(color);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("dataCenterTableColor")) {
            cellStyle.setFillForegroundColor(color);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
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
        if (type.equals("dat")) {
//            fontStyle.setColor(IndexedColors.GREEN.getIndex());
//            cellStyle.setFillForegroundColor(color);
//            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("truot")) {
//            fontStyle.setColor(IndexedColors.RED.getIndex());
//            cellStyle.setFillForegroundColor(color);
//            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        return cellStyle;
    }

    @Override
    public TeCountClassReponse findCount(TeFindClassStatisticalRequest request) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (request.getIdSemester().equalsIgnoreCase("")) {
            if (idSemesterCurrent != null) {
                request.setIdSemester(idSemesterCurrent);
                request.setIdActivity("");
            } else {
                request.setIdSemester("");
            }
        }
        TeCountClassReponse response = teClassRepository.findCount(request);
        return response;
    }


}
