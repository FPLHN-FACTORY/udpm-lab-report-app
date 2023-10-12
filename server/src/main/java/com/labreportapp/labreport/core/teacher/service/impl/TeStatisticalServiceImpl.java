package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.model.request.TeFindClassStatisticalRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeCountClassReponse;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.service.TeStatisticalService;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
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

    @Override
    @Synchronized
    public ByteArrayOutputStream exportExcel(HttpServletResponse response, String idClass) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            List<TePointStudentInforRespone> listPointIdClass = getPointStudentByIdClass(idClass);
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchApiStudentClassesByIdClass(idClass);
            List<TePointExcel> listExcel = new ArrayList<>();
            listPointIdClass.forEach((item1) -> {
                listStudent.forEach((item2) -> {
                    if (item2.getIdStudent().equals(item1.getIdStudent())) {
                        TePointExcel point = new TePointExcel();
                        point.setIdPoint(item1.getIdPoint());
                        point.setIdStudent(item1.getIdStudent());
                        point.setName(item2.getName());
                        point.setEmail(item2.getEmail());
                        point.setCheckPointPhase1(item1.getCheckPointPhase1());
                        point.setCheckPointPhase2(item1.getCheckPointPhase2());
                        point.setFinalPoint(item1.getFinalPoint());
                        listExcel.add(point);
                    }
                });
            });
            Class objClass = teClassRepository.findById(idClass).get();
            Sheet sheet = configTitle(workbook, objClass.getCode());
            int rowIndex = 3;
            int index = 1;
            for (TePointExcel data : listExcel) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(index++);
                dataRow.getCell(0).setCellStyle(chooseCellStyle("dataCenterTable", workbook));
                dataRow.createCell(1).setCellValue(data.getName());
                dataRow.getCell(1).setCellStyle(chooseCellStyle("dataTable", workbook));
                dataRow.createCell(2).setCellValue(data.getEmail());
                dataRow.getCell(2).setCellStyle(chooseCellStyle("dataTable", workbook));
                dataRow.createCell(3).setCellValue(data.getCheckPointPhase1());
                dataRow.getCell(3).setCellStyle(chooseCellStyle("dataCenterTable", workbook));
                dataRow.createCell(4).setCellValue(data.getCheckPointPhase2());
                dataRow.getCell(4).setCellStyle(chooseCellStyle("dataCenterTable", workbook));
                dataRow.createCell(5).setCellValue(data.getFinalPoint());
                dataRow.getCell(5).setCellStyle(chooseCellStyle("dataCenterTable", workbook));
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

    private Sheet configTitle(Workbook workbook, String name) {
        Font fontTitle = workbook.createFont();
        Sheet sheet = workbook.createSheet("Bảng điểm");
        Row titleRow = sheet.createRow(0);
        Cell cellTitle = titleRow.createCell(0);
        cellTitle.setCellValue("DANH SÁCH ĐIỂM SINH VIÊN LỚP " + name);
        cellTitle.setCellStyle(chooseCellStyle("title", workbook));
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
        cell0.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("Tên sinh viên");
        cell1.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell2 = headerRow.createCell(2);
        cell2.setCellValue("Email");
        cell2.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell3 = headerRow.createCell(3);
        cell3.setCellValue("Điểm giai đoạn 1");
        cell3.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell4 = headerRow.createCell(4);
        cell4.setCellValue("Điểm giai đoạn 2");
        cell4.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell5 = headerRow.createCell(5);
        cell5.setCellValue("Điểm giai đoạn cuối");
        cell5.setCellStyle(chooseCellStyle("titleTable", workbook));
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
        return cellStyle;
    }
}
