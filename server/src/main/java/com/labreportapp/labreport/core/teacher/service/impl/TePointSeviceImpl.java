package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportPoint;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportPointService;
import com.labreportapp.labreport.core.teacher.model.request.Base.TePointExcel;
import com.labreportapp.labreport.core.teacher.model.request.TeFindListPointRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindPointRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.labreport.core.teacher.model.response.TePointRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TePointRepository;
import com.labreportapp.labreport.core.teacher.service.TePointSevice;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.Point;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Synchronized;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hieundph25894
 */
@Service
public class TePointSeviceImpl implements TePointSevice {

    @Autowired
    private TePointRepository tePointRepository;

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private TeExcelImportPointService tePointImportService;

    @Autowired
    private TeClassRepository teClassRepository;

    @Override
    public List<TePointRespone> getPointStudentById(String idClass) {
        List<TePointRespone> list = tePointRepository.getAllPointByIdClass(idClass);
        return list;
    }

    @Override
    @Synchronized
    public List<Point> addOrUpdatePoint(TeFindListPointRequest request) {
        List<TeFindPointRequest> list = request.getListPoint();
        List<Point> listNew = new ArrayList<>();
        list.forEach(item -> {
            Optional<TePointRespone> obj = tePointRepository.getPointIdClassIdStudent(item);
            if (obj.isPresent()) {
                Point point = new Point();
                point.setId(obj.get().getId());
                point.setStudentId(obj.get().getIdStudent());
                point.setClassId(obj.get().getIdClass());
                point.setCheckPointPhase1(item.getCheckPointPhase1());
                point.setCheckPointPhase2(item.getCheckPointPhase2());
                point.setFinalPoint((item.getCheckPointPhase1() + item.getCheckPointPhase2()) / 2);
                listNew.add(point);
            } else {
                Point point = new Point();
                point.setStudentId(item.getIdStudent());
                point.setClassId(item.getIdClass());
                point.setCheckPointPhase1(item.getCheckPointPhase1());
                point.setCheckPointPhase2(item.getCheckPointPhase2());
                point.setFinalPoint((item.getCheckPointPhase1() + item.getCheckPointPhase2()) / 2);
                listNew.add(point);
            }
        });
        return tePointRepository.saveAll(listNew);
    }

    @Override
    @Synchronized
    public ByteArrayOutputStream exportExcel(HttpServletResponse response, String idClass) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            List<TePointRespone> listPointIdClass = getPointStudentById(idClass);
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchApiStudentClassesByIdClass(idClass);
            List<TePointExcel> listExcel = new ArrayList<>();
            listPointIdClass.forEach((item1) -> {
                listStudent.forEach((item2) -> {
                    if (item2.getIdStudent().equals(item1.getIdStudent())) {
                        TePointExcel point = new TePointExcel();
                        point.setIdPoint(item1.getId());
                        point.setIdStudent(item1.getIdStudent());
                        point.setName(item2.getName());
                        point.setEmail(item2.getEmail());
                        point.setCheckPointPhase1(item1.getCheckPointPhase1());
                        point.setCheckPointPhase2(item1.getCheckPointPhase2());
                        point.setFinalPoint(item1.getFinalPoint());
                        listExcel.add(point);
                    }
                });
            });
            Class objClass = teClassRepository.findById(idClass).get();
            Font fontTitle = workbook.createFont();
            Sheet sheet = workbook.createSheet("Bảng điểm");
            fontTitle.setBold(true);
            fontTitle.setFontHeightInPoints((short) 20);
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleStyle.setFont(fontTitle);
            Row titleRow = sheet.createRow(0);
            Cell cellTitle = titleRow.createCell(0);
            cellTitle.setCellValue("DANH SÁCH ĐIỂM SINH VIÊN LỚP " + objClass.getCode());
            cellTitle.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            Font fontHeader = workbook.createFont();
            fontHeader.setBold(true);
            fontHeader.setColor(IndexedColors.WHITE.getIndex());
            fontHeader.setFontHeightInPoints((short) 13);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setFont(fontHeader);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
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
            Row headerRow = sheet.createRow(1);
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
            CellStyle boderStyle = workbook.createCellStyle();
            boderStyle.setBorderTop(BorderStyle.THIN);
            boderStyle.setBorderBottom(BorderStyle.THIN);
            boderStyle.setBorderLeft(BorderStyle.THIN);
            boderStyle.setBorderRight(BorderStyle.THIN);
            CellStyle dataStyleCenter = workbook.createCellStyle();
            dataStyleCenter.setAlignment(HorizontalAlignment.CENTER);
            dataStyleCenter.setBorderTop(BorderStyle.THIN);
            dataStyleCenter.setBorderBottom(BorderStyle.THIN);
            dataStyleCenter.setBorderLeft(BorderStyle.THIN);
            dataStyleCenter.setBorderRight(BorderStyle.THIN);
            int rowIndex = 2;
            int index = 1;
            for (TePointExcel data : listExcel) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(index++);
                dataRow.getCell(0).setCellStyle(dataStyleCenter);
                dataRow.createCell(1).setCellValue(data.getName());
                dataRow.getCell(1).setCellStyle(boderStyle);
                dataRow.createCell(2).setCellValue(data.getEmail());
                dataRow.getCell(2).setCellStyle(boderStyle);
                dataRow.createCell(3).setCellValue(data.getCheckPointPhase1());
                dataRow.getCell(3).setCellStyle(dataStyleCenter);
                dataRow.createCell(4).setCellValue(data.getCheckPointPhase2());
                dataRow.getCell(4).setCellStyle(dataStyleCenter);
                dataRow.createCell(5).setCellValue(data.getFinalPoint());
                dataRow.getCell(5).setCellStyle(dataStyleCenter);
            }
            workbook.write(response.getOutputStream());
            workbook.close();
            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TeExcelResponseMessage importExcel(MultipartFile file, String idClass) {
        TeExcelResponseMessage teExcelResponseMessage = new TeExcelResponseMessage();
        try {
            List<TeExcelImportPoint> list = tePointImportService.importData(file, idClass);
            if (list.size() == 0) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("file excel trống");
                return teExcelResponseMessage;
            }
            ConcurrentHashMap<String, SimpleResponse> mapPointStudent = new ConcurrentHashMap<>();
            addDataMapsPointStudent(mapPointStudent, idClass);
            if (list.size() != mapPointStudent.size()) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("số lượng sinh viên trong file excel phải bằng với số lượng sinh viên trong lớp");
                return teExcelResponseMessage;
            }
            ConcurrentHashMap<String, Point> mapPointStudentDB = new ConcurrentHashMap<>();
            addDataMapsPointStudentDB(mapPointStudentDB, idClass);
            ConcurrentHashMap<String, Point> pointUpdate = new ConcurrentHashMap<>();
            teExcelResponseMessage.setStatus(true);
            list.parallelStream().forEach(point -> {
                String regexName = "^[^!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/\\\\]*$";
                String regexEmail = "^[a-zA-Z0-9._%+-]+@fpt.edu.vn$";
                String regexDouble = "^(?:[0-9](?:\\.\\d*)?|10(?:\\.0*)?)$";
                if (point.getName().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("tên sinh viên không được để trống");
                    return;
                }
                if (!point.getName().matches(regexName)) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("tên sinh viên sai định dạng");
                    return;
                }
                if (point.getEmail().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("email không được để trống");
                    return;
                }
                if (point.getCheckPointPhase1().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("điểm giai đoạn 1 không được để trống");
                    return;
                }
                if (point.getCheckPointPhase2().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("điểm giai đoạn 2 không được để trống");
                    return;
                }
                if (!point.getCheckPointPhase1().matches(regexDouble)) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("điểm giai đoạn 1 và giai đoạn 2 phải là số từ 0 -> 10");
                    return;
                }
                if (!point.getCheckPointPhase2().matches(regexDouble)) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("điểm giai đoạn 1 và giai đoạn 2 phải là số từ 0 -> 10");
                    return;
                }
                SimpleResponse simpleResponse = mapPointStudent.get(point.getEmail());
                if (simpleResponse == null) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("email của sinh viên không tồn tại");
                    return;
                }
                Point pointUpd = mapPointStudentDB.get(simpleResponse.getId());
                pointUpd.setCheckPointPhase1(Double.parseDouble((point.getCheckPointPhase1())));
                pointUpd.setCheckPointPhase2(Double.parseDouble((point.getCheckPointPhase2())));
                pointUpd.setFinalPoint(Double.parseDouble(String.valueOf((Double.parseDouble((point.getCheckPointPhase1())) + Double.parseDouble((point.getCheckPointPhase2()))) / 2)));
                pointUpdate.put(pointUpd.getStudentId(), pointUpd);
            });
            if (teExcelResponseMessage.getStatus() == true) {
                tePointRepository.saveAll(pointUpdate.values());
                return teExcelResponseMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            teExcelResponseMessage.setStatus(false);
            teExcelResponseMessage.setMessage("lỗi hệ thống");
            return teExcelResponseMessage;
        }
        return teExcelResponseMessage;
    }

    public void addDataMapsPointStudent(ConcurrentHashMap<String, SimpleResponse> mapAll, String idClass) {
        List<SimpleResponse> listStudent = teStudentClassesService.searchAllStudentByIdClass(idClass);
        getAllPutMapPoint(mapAll, listStudent);
    }

    public void getAllPutMapPoint
            (ConcurrentHashMap<String, SimpleResponse> mapSimple, List<SimpleResponse> listStudent) {
        for (SimpleResponse student : listStudent) {
            mapSimple.put(student.getEmail().toLowerCase(), student);
        }
    }

    public void addDataMapsPointStudentDB(ConcurrentHashMap<String, Point> mapPointDB, String idClass) {
        List<Point> listPoint = tePointRepository.getAllPointByIdClassImport(idClass);
        getAllPutMapPointDB(mapPointDB, listPoint);
    }

    public void getAllPutMapPointDB(ConcurrentHashMap<String, Point> mapPoint, List<Point> listPointDB) {
        for (Point point : listPointDB) {
            mapPoint.put(point.getStudentId(), point);
        }
    }

}
