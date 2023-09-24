package com.labreportapp.labreport.core.admin.excel;

import com.labreportapp.labreport.core.admin.model.response.AdExportExcelClassCustom;
import com.labreportapp.labreport.util.LabReportUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author thangncph26123
 */
@Component
public class AdExportExcelClass {

    public ByteArrayOutputStream export(HttpServletResponse response, List<AdExportExcelClassCustom> listClass) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Danh sách lớp học");

            CellStyle cellStyle = workbook.createCellStyle();

            XSSFFont font = (XSSFFont) workbook.createFont();
            font.setFontHeight(12);
            cellStyle.setFont(font);

            // Header
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("STT");
            cell.setCellStyle(cellStyle);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("Mã lớp");
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Thời gian bắt đầu");
            cell2.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("Ca học");
            cell3.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Thời gian");
            cell4.setCellStyle(cellStyle);

            Cell cell5 = row.createCell(5);
            cell5.setCellValue("Sĩ số");
            cell5.setCellStyle(cellStyle);

            Cell cell6 = row.createCell(6);
            cell6.setCellValue("Level");
            cell6.setCellStyle(cellStyle);

            Cell cell7 = row.createCell(7);
            cell7.setCellValue("Giảng viên");
            cell7.setCellStyle(cellStyle);

            Cell cell8 = row.createCell(8);
            cell8.setCellValue("Hoạt động");
            cell8.setCellStyle(cellStyle);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //Set data
            int rowNum = 1;
            for (AdExportExcelClassCustom classResponse : listClass) {
                Row empDataRow = sheet.createRow(rowNum++);

                Cell empSttCell = empDataRow.createCell(0);
                empSttCell.setCellStyle(cellStyle);
                empSttCell.setCellValue(String.valueOf(classResponse.getStt()));

                Cell empCodeCell = empDataRow.createCell(1);
                empCodeCell.setCellStyle(cellStyle);
                empCodeCell.setCellValue(String.valueOf(classResponse.getCode()));

                Cell empThoiGianBatDauCell = empDataRow.createCell(2);
                empThoiGianBatDauCell.setCellStyle(cellStyle);
                empThoiGianBatDauCell.setCellValue(sdf.format(classResponse.getStartTime()));

                Cell empClassPeriodCell = empDataRow.createCell(3);
                empClassPeriodCell.setCellStyle(cellStyle);
                empClassPeriodCell.setCellValue(classResponse.getClassPeriod() != null ? String.valueOf(classResponse.getClassPeriod() + 1) : "");

                Cell empThoiGianCell = empDataRow.createCell(4);
                empThoiGianCell.setCellStyle(cellStyle);
                empThoiGianCell.setCellValue(LabReportUtils.convertMeetingPeriodToTime(classResponse.getClassPeriod() != null ? classResponse.getClassPeriod() : -1));

                Cell empSiSoCell = empDataRow.createCell(5);
                empSiSoCell.setCellStyle(cellStyle);
                empSiSoCell.setCellValue(String.valueOf(classResponse.getClassSize()));

                Cell empLevelCell = empDataRow.createCell(6);
                empLevelCell.setCellStyle(cellStyle);
                empLevelCell.setCellValue(classResponse.getNameLevel());

                Cell empGiangVienCell = empDataRow.createCell(7);
                empGiangVienCell.setCellStyle(cellStyle);
                empGiangVienCell.setCellValue(classResponse.getUserNameTeacher() != null ? classResponse.getUserNameTeacher() : "");

                Cell empHoatDongCell = empDataRow.createCell(8);
                empHoatDongCell.setCellStyle(cellStyle);
                empHoatDongCell.setCellValue(classResponse.getNameActivity());
            }

            workbook.write(outputStream);
            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}