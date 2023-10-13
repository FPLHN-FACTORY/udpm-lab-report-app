package com.labreportapp.labreport.core.admin.excel;

import com.github.pjfanning.xlsx.StreamingReader;
import com.labreportapp.labreport.core.admin.model.response.AdImportExcelClassResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author thangncph26123
 */
@Component
public class AdImportExcelClass {

    public static List<AdImportExcelClassResponse> importData(MultipartFile reapExcelDataFile) throws IOException {
        List<AdImportExcelClassResponse> listClass = new ArrayList<>();
        Workbook workbook = StreamingReader.builder()
                .bufferSize(4096)
                .rowCacheSize(50)
                .open(reapExcelDataFile.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);
        for (Row row :
                worksheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            if (row.getCell(1).getCellType() != CellType.STRING) {
                continue;
            }
            AdImportExcelClassResponse classNew = new AdImportExcelClassResponse();
            classNew.setCode(String.valueOf(getCellValue(row.getCell(1))).trim());
            String classPeriodStr = String.valueOf(getCellValue(row.getCell(3))).trim();
            classNew.setClassPeriod(classPeriodStr);
            classNew.setUsernameTeacher(String.valueOf(getCellValue(row.getCell(7))).trim());
            listClass.add(classNew);
        }
        return listClass;
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
