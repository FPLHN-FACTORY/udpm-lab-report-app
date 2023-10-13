package com.labreportapp.labreport.core.admin.excel;

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

@Component
public class AdExcelImportService {

    public List<AdImportExcelStudentClasses> importDataStudentClasses(MultipartFile reapExcelDataFile) throws IOException {
        List<AdImportExcelStudentClasses> listStudent = new ArrayList<>();
        Workbook workbook = StreamingReader.builder()
                .bufferSize(4096)
                .rowCacheSize(50)
                .open(reapExcelDataFile.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);

        for (Row row : worksheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            String cellName = String.valueOf(getCellValue(row.getCell(1)));
            String cellEmail = String.valueOf(getCellValue(row.getCell(2)));

            if (cellName.isBlank() && cellEmail.isBlank()) {
                continue;
            }
            if (!cellName.isBlank() || !cellEmail.isBlank()) {
                AdImportExcelStudentClasses object = new AdImportExcelStudentClasses();
                object.setName(cellName.trim());
                object.setEmail(cellEmail.trim());
                listStudent.add(object);
            }
        }

        return listStudent;
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
