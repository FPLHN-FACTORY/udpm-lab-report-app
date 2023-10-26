package com.labreportapp.labreport.core.admin.excel;

import com.labreportapp.labreport.core.admin.model.response.AdExcelMemberFactoryCustom;
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
import java.util.List;

/**
 * @author thangncph26123
 */
@Component
public class AdExportExcelMemberFactory {

    public ByteArrayOutputStream exportExcel(HttpServletResponse response, List<AdExcelMemberFactoryCustom> list) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Danh sách thành viên trong xưởng");

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
            cell1.setCellValue("Họ và tên");
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Tên tài khoản");
            cell2.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("Vai trò");
            cell3.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Team");
            cell4.setCellStyle(cellStyle);

            Cell cell5 = row.createCell(5);
            cell5.setCellValue("Trạng thái");
            cell5.setCellStyle(cellStyle);

            int rowNum = 1;

            for (AdExcelMemberFactoryCustom xx : list) {
                Row empDataRow = sheet.createRow(rowNum++);

                Cell empSttCell = empDataRow.createCell(0);
                empSttCell.setCellStyle(cellStyle);
                empSttCell.setCellValue(String.valueOf(xx.getStt()));

                Cell empNameCell = empDataRow.createCell(1);
                empNameCell.setCellStyle(cellStyle);
                empNameCell.setCellValue(xx.getName());

                Cell empUserNameCell = empDataRow.createCell(2);
                empUserNameCell.setCellStyle(cellStyle);
                empUserNameCell.setCellValue(xx.getUserName());

                Cell empRoleCell = empDataRow.createCell(3);
                empRoleCell.setCellStyle(cellStyle);
                empRoleCell.setCellValue(xx.getRole());

                Cell empTeamCell = empDataRow.createCell(4);
                empTeamCell.setCellStyle(cellStyle);
                empTeamCell.setCellValue(xx.getTeam());

                Cell empStatusCell = empDataRow.createCell(5);
                empStatusCell.setCellStyle(cellStyle);
                empStatusCell.setCellValue(xx.getStatus() == 0 ? "Hoạt động" : "Không hoạt động");
            }

            workbook.write(outputStream);
            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
