package com.labreportapp.labreport.core.teacher.excel;

import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * @author hieundph25894
 */
@Component
public class TeExcelImportService {

    public List<TeExcelImportPoint> importDataPoint(MultipartFile reapExcelDataFile, String idClass) throws IOException {
        List<TeExcelImportPoint> listPoint = new ArrayList<>();
        Workbook workbook = StreamingReader.builder()
                .bufferSize(4096)
                .rowCacheSize(50)
                .open(reapExcelDataFile.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);
        for (Row row : worksheet) {
            if (row.getRowNum() == 0 || row.getRowNum() == 1 || row.getRowNum() == 2) {
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
        reapExcelDataFile.getInputStream().close();
        return listPoint;
    }

    public List<TeExcelImportTeam> importDataTeam(MultipartFile reapExcelDataFile, String idClass) throws IOException {
        List<TeExcelImportTeam> listTeam = new ArrayList<>();
        Workbook workbook = StreamingReader.builder()
                .bufferSize(4096)
                .rowCacheSize(50)
                .open(reapExcelDataFile.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);
        for (Row row : worksheet) {
            if (row.getRowNum() == 0 || row.getRowNum() == 1 || row.getRowNum() == 2) {
                continue;
            }
            TeExcelImportTeam object = new TeExcelImportTeam();
            object.setName(String.valueOf(getCellValue(row.getCell(1))).trim());
            object.setEmail(String.valueOf(getCellValue(row.getCell(2))).trim());
            object.setRole(String.valueOf(getCellValue(row.getCell(3))).trim());
            object.setNameTeam(String.valueOf(getCellValue(row.getCell(4))).trim());
            object.setSubjectTeam(String.valueOf(getCellValue(row.getCell(5))).trim());
            listTeam.add(object);
        }
        reapExcelDataFile.getInputStream().close();
        return listTeam;
    }

    public List<TeExcelImportMeetingRequest> importDataMeetingRequest(MultipartFile reapExcelDataFile) throws IOException {
        List<TeExcelImportMeetingRequest> listMeetingRequest = new ArrayList<>();
        Workbook workbook = StreamingReader.builder()
                .bufferSize(4096)
                .rowCacheSize(50)
                .open(reapExcelDataFile.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);
        for (Row row : worksheet) {
            if (row.getRowNum() == 0 || row.getRowNum() == 1 || row.getRowNum() == 2) {
                continue;
            }
            TeExcelImportMeetingRequest object = new TeExcelImportMeetingRequest();
            object.setMeetingDate(String.valueOf(getCellValue(row.getCell(0))));
            object.setNameMeetingPeriod(String.valueOf(getCellValue(row.getCell(1))).trim());
            object.setUserNameTeacher(String.valueOf(getCellValue(row.getCell(2))).trim());
            object.setTypeMeeting(String.valueOf(getCellValue(row.getCell(3))).trim());
            listMeetingRequest.add(object);
        }
        reapExcelDataFile.getInputStream().close();
        return listMeetingRequest;
    }

    private static Object getCellValue(Cell cell) {
        try {
            switch (cell.getCellType()) {
                case NUMERIC -> {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                        return sdf.format(cell.getDateCellValue());
                    } else {
                        return String.valueOf(cell.getNumericCellValue());
                    }
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
