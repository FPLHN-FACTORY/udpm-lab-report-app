package com.labreportapp.infrastructure.configexcel;

import com.labreportapp.infrastructure.configexcel.Base.Excel;
import com.labreportapp.infrastructure.configexcel.Base.ExcelRequest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class ExcelService {

    public void exportExcel(ExcelRequest request) throws IOException {
        List<Excel> list = request.getListExcel();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bảng điểm");
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 13);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 8000);
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 7000);
        Row headerRow = sheet.createRow(0);
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("STT");
        cell0.setCellStyle(headerStyle);
        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("Tên sinh viên");
        cell1.setCellStyle(headerStyle);
        Cell cell2 = headerRow.createCell(2);
        cell2.setCellValue("Email");
        cell2.setCellStyle(headerStyle);
        Cell cell3 = headerRow.createCell(3);
        cell3.setCellValue("Điểm giai đoạn 1");
        cell3.setCellStyle(headerStyle);
        Cell cell4 = headerRow.createCell(4);
        cell4.setCellValue("Điểm giai đoạn 2");
        cell4.setCellStyle(headerStyle);
        Cell cell5 = headerRow.createCell(5);
        cell5.setCellValue("Điểm giai đoạn cuối");
        cell5.setCellStyle(headerStyle);
        int rowIndex = 1;
        for (Excel data : list) {
            Row dataRow = sheet.createRow(rowIndex++);
            dataRow.createCell(0).setCellValue(rowIndex - 1);
            dataRow.createCell(1).setCellValue(data.getName());
            dataRow.createCell(2).setCellValue(data.getEmail());
            dataRow.createCell(3).setCellValue(data.getCheckPointPhase1());
            dataRow.createCell(4).setCellValue(data.getCheckPointPhase2());
            dataRow.createCell(5).setCellValue(data.getFinalPoint());
        }
        File file = new File(System.getProperty("user.home") + "/Downloads/bang_diem.xlsx");
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
    }
}
