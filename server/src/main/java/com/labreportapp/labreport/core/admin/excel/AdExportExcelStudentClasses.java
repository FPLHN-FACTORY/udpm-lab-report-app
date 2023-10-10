package com.labreportapp.labreport.core.admin.excel;

import com.labreportapp.labreport.core.admin.model.response.AdExportExcelStudentsClassCustomResponse;
import com.labreportapp.labreport.core.student.model.response.StClassConfigurationResponse;
import com.labreportapp.labreport.core.student.repository.StClassConfigurationRepository;
import jakarta.servlet.http.HttpServletResponse;
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
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class AdExportExcelStudentClasses {

    @Autowired
    private StClassConfigurationRepository stClassConfigurationRepository;

    public ByteArrayOutputStream export(HttpServletResponse response, List<AdExportExcelStudentsClassCustomResponse> studentsInClass, Boolean isSample) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Danh sách sinh viên trong lớp");

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            XSSFFont font = (XSSFFont) workbook.createFont();

            CellStyle cellStyleCenter = workbook.createCellStyle();
            cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
            cellStyleCenter.setBorderTop(BorderStyle.THIN);
            cellStyleCenter.setBorderBottom(BorderStyle.THIN);
            cellStyleCenter.setBorderLeft(BorderStyle.THIN);
            cellStyleCenter.setBorderRight(BorderStyle.THIN);
            cellStyleCenter.setBorderTop(BorderStyle.THIN);
            cellStyleCenter.setBorderBottom(BorderStyle.THIN);
            cellStyleCenter.setBorderLeft(BorderStyle.THIN);
            cellStyleCenter.setBorderRight(BorderStyle.THIN);
            font.setFontHeight(12);
            cellStyle.setFont(font);

            CellStyle styleDataTable = workbook.createCellStyle();
            Font fontStyleDataTable = workbook.createFont();
            fontStyleDataTable.setBold(true);
            fontStyleDataTable.setColor(IndexedColors.WHITE.getIndex());
            fontStyleDataTable.setFontHeightInPoints((short) 13);
            styleDataTable.setAlignment(HorizontalAlignment.CENTER);
            styleDataTable.setVerticalAlignment(VerticalAlignment.CENTER);
            styleDataTable.setFont(fontStyleDataTable);
            styleDataTable.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            styleDataTable.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleDataTable.setBorderTop(BorderStyle.THIN);
            styleDataTable.setBorderBottom(BorderStyle.THIN);
            styleDataTable.setBorderLeft(BorderStyle.THIN);
            styleDataTable.setBorderRight(BorderStyle.THIN);

            Row row = sheet.createRow(0);

            Cell cell = row.createCell(0);
            cell.setCellValue("STT");
            cell.setCellStyle(styleDataTable);

            Cell cell2 = row.createCell(!isSample ? 2 : 1);
            cell2.setCellValue("Họ Và Tên");
            cell2.setCellStyle(styleDataTable);

            Cell cell3 = row.createCell(!isSample ? 3 : 2);
            cell3.setCellValue("Email");
            cell3.setCellStyle(styleDataTable);
            if (!isSample) {
                Cell cell1 = row.createCell(1);
                cell1.setCellValue("Tên tài khoản");
                cell1.setCellStyle(styleDataTable);

                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Trạng thái");
                cell4.setCellStyle(styleDataTable);
            }

            if (!isSample) {
                int rowNum = 1;
                int stt = 1;
                for (AdExportExcelStudentsClassCustomResponse studentsResponse : studentsInClass) {
                    Row empDataRow = sheet.createRow(rowNum++);

                    Cell empSttCell = empDataRow.createCell(0);
                    empSttCell.setCellStyle(cellStyle);
                    empSttCell.setCellValue(String.valueOf(stt++));

                    Cell empUserNameCell = empDataRow.createCell(1);
                    empUserNameCell.setCellStyle(cellStyle);
                    empUserNameCell.setCellValue(String.valueOf(studentsResponse.getUsername()));

                    Cell empName = empDataRow.createCell(2);
                    empName.setCellStyle(cellStyle);
                    empName.setCellValue(String.valueOf(studentsResponse.getName()));

                    Cell empEmail = empDataRow.createCell(3);
                    empEmail.setCellStyle(cellStyle);
                    empEmail.setCellValue(String.valueOf(studentsResponse.getEmail()));

                    Cell empStatus = empDataRow.createCell(4);
                    empStatus.setCellStyle(cellStyleCenter);
                    empStatus.setCellValue(studentsResponse.getStatusStudent().equals("0") ? "Đạt" : "Trượt");
                }
                for (int i = 0; i < 5; i++) {
                    sheet.autoSizeColumn(i, true);
                }
            } else {
                StClassConfigurationResponse configuration = stClassConfigurationRepository.getClassConfiguration();
                for (int i = 1; i <= configuration.getClassSizeMax(); i++) {
                    Row empDataRow = sheet.createRow(i);
                    Cell empSttCell = empDataRow.createCell(0);
                    empSttCell.setCellStyle(cellStyle);
                    empSttCell.setCellValue(String.valueOf(i));
                }
                for (int i = 0; i < 3; i++) {
                    sheet.autoSizeColumn(i, true);
                }
            }

            workbook.write(outputStream);
            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
