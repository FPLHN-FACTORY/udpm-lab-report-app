package com.labreportapp.labreport.core.admin.excel;

import com.github.pjfanning.xlsx.StreamingReader;
import com.labreportapp.labreport.core.admin.model.response.AdImportExcelMemberFactoryResponse;
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
 * @author thangncph26123
 */
@Component
public class AdImportExcelMemberFactoryService {

    public List<AdImportExcelMemberFactoryResponse> importDataMemberFactory(MultipartFile reapExcelDataFile) throws IOException {
        List<AdImportExcelMemberFactoryResponse> listMemberFactory = new ArrayList<>();
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
                AdImportExcelMemberFactoryResponse object = new AdImportExcelMemberFactoryResponse();
                object.setName(cellName.trim());
                object.setEmail(cellEmail.trim());
                listMemberFactory.add(object);
            }
        }

        return listMemberFactory;
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
