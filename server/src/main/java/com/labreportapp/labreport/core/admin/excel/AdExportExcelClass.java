package com.labreportapp.labreport.core.admin.excel;

import com.labreportapp.labreport.core.admin.model.response.AdExportExcelClassCustom;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.util.DateConverter;
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

    public ByteArrayOutputStream export(HttpServletResponse response, List<AdExportExcelClassCustom> listClass, List<SimpleResponse> listGiangVien, List<MeetingPeriod> listCaHoc) {
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
                empClassPeriodCell.setCellValue(classResponse.getClassPeriod() != null ? classResponse.getClassPeriod() : "");

                Cell empTimeCell = empDataRow.createCell(4);
                empTimeCell.setCellStyle(cellStyle);
                empTimeCell.setCellValue(classResponse.getStartHour() != null ? DateConverter.convertHourAndMinuteToString(classResponse.getStartHour(),
                        classResponse.getStartMinute(), classResponse.getEndHour(), classResponse.getEndMinute()) : "");

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

            Sheet sheetGiangVien = workbook.createSheet("Danh sách giảng viên");
            Row rowGiangVien = sheetGiangVien.createRow(0);

            Cell cellGiangVien = rowGiangVien.createCell(0);
            cellGiangVien.setCellValue("STT");
            cellGiangVien.setCellStyle(cellStyle);

            Cell cellGiangVien1 = rowGiangVien.createCell(1);
            cellGiangVien1.setCellValue("Họ và tên");
            cellGiangVien1.setCellStyle(cellStyle);

            Cell cellGiangVien2 = rowGiangVien.createCell(2);
            cellGiangVien2.setCellValue("Tài khoản");
            cellGiangVien2.setCellStyle(cellStyle);

            Cell cellGiangVien3 = rowGiangVien.createCell(3);
            cellGiangVien3.setCellValue("Email");
            cellGiangVien3.setCellStyle(cellStyle);

            int rowNumGiangVien = 1;
            for (SimpleResponse simpleResponse : listGiangVien) {
                Row empDataRow = sheetGiangVien.createRow(rowNumGiangVien++);

                Cell empSttCell = empDataRow.createCell(0);
                empSttCell.setCellStyle(cellStyle);
                empSttCell.setCellValue(String.valueOf(rowNumGiangVien++));

                Cell empHoVaTenCell = empDataRow.createCell(1);
                empHoVaTenCell.setCellStyle(cellStyle);
                empHoVaTenCell.setCellValue(simpleResponse.getName());

                Cell empTaiKhoanCell = empDataRow.createCell(2);
                empTaiKhoanCell.setCellStyle(cellStyle);
                empTaiKhoanCell.setCellValue(simpleResponse.getUserName());

                Cell empEmailCell = empDataRow.createCell(3);
                empEmailCell.setCellStyle(cellStyle);
                empEmailCell.setCellValue(simpleResponse.getEmail());
            }

            Sheet sheetCaHoc = workbook.createSheet("Danh sách ca học");
            Row rowCaHoc = sheetCaHoc.createRow(0);

            Cell cellCaHoc = rowCaHoc.createCell(0);
            cellCaHoc.setCellValue("STT");
            cellCaHoc.setCellStyle(cellStyle);

            Cell cellCaHoc1 = rowCaHoc.createCell(1);
            cellCaHoc1.setCellValue("Tên ca");
            cellCaHoc1.setCellStyle(cellStyle);

            Cell cellCaHoc2 = rowCaHoc.createCell(2);
            cellCaHoc2.setCellValue("Thời gian");
            cellCaHoc2.setCellStyle(cellStyle);

            int rowNumCaHoc = 1;
            for (MeetingPeriod meetingPeriod : listCaHoc) {
                Row empDataRow = sheetCaHoc.createRow(rowNumCaHoc++);

                Cell empSttCell = empDataRow.createCell(0);
                empSttCell.setCellStyle(cellStyle);
                empSttCell.setCellValue(String.valueOf(rowNumCaHoc++));

                Cell empNameCell = empDataRow.createCell(1);
                empNameCell.setCellStyle(cellStyle);
                empNameCell.setCellValue(meetingPeriod.getName());

                Cell empThoiGianCell = empDataRow.createCell(2);
                empThoiGianCell.setCellStyle(cellStyle);
                empThoiGianCell.setCellValue(DateConverter.convertHourAndMinuteToString(meetingPeriod.getStartHour(),
                        meetingPeriod.getStartMinute(),
                        meetingPeriod.getEndHour(),
                        meetingPeriod.getEndMinute()));
            }

            workbook.write(outputStream);
            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
