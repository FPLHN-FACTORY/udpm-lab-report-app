package com.labreportapp.labreport.core.teacher.excel;

import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hieundph25894
 */
@Component
public class TeExcelImportPointService {

    public List<TeExcelImportPoint> importData(MultipartFile reapExcelDataFile, String idClass) throws IOException {
        List<TeExcelImportPoint> listPoint = new ArrayList<>();
        Workbook workbook = StreamingReader.builder()
                .bufferSize(4096)
                .rowCacheSize(50)
                .open(reapExcelDataFile.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);
        for (Row row : worksheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            if (row.getRowNum() == 1) {
                continue;
            }
            TeExcelImportPoint object = new TeExcelImportPoint();
            object.setName(String.valueOf(getCellValue(row.getCell(1))).trim());
            object.setEmail(String.valueOf(getCellValue(row.getCell(2))).trim());
            object.setCheckPointPhase1(String.valueOf(getCellValue(row.getCell(3))).trim());
            object.setCheckPointPhase2(String.valueOf(getCellValue(row.getCell(4))).trim());
            object.setFinalPoint(String.valueOf(getCellValue(row.getCell(5))).trim());
            listPoint.add(object);
        }
        return listPoint;
    }

    private static Object getCellValue(Cell cell) {
        try {
            switch (cell.getCellType()) {
                case NUMERIC -> {
                    return cell.getNumericCellValue();
                }
                case BOOLEAN -> {
                    return cell.getBooleanCellValue();
                }
                default -> {
                    return cell.getStringCellValue();
                }
            }
        } catch (Exception e) {
            return "";
        }
    }

}
