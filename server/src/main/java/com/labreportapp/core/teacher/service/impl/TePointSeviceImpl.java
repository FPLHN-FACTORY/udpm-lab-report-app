package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.ServerApplication;
import com.labreportapp.core.teacher.model.request.Base.TePointExcel;
import com.labreportapp.core.teacher.model.request.TeFindListPointRequest;
import com.labreportapp.core.teacher.model.request.TeFindPointRequest;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.response.TePointRespone;
import com.labreportapp.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.core.teacher.repository.TePointRepository;
import com.labreportapp.core.teacher.service.TePointSevice;
import com.labreportapp.core.teacher.service.TeStudentClassesService;
import com.labreportapp.entity.Point;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Synchronized;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Service
public class TePointSeviceImpl implements TePointSevice {

    @Autowired
    private TePointRepository tePointRepository;

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Override
    public List<TePointRespone> getPointStudentById(String idClass) {
        List<TePointRespone> list = tePointRepository.getAllPointByIdClass(idClass);
        if (list == null) {
            return null;
        }
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
    public void exportExcel(HttpServletResponse response, String idClass) {
        try (Workbook workbook = new XSSFWorkbook()) {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=BangDiem_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            List<TePointRespone> listPointIdClass = getPointStudentById(idClass);
            TeFindStudentClasses teFindStudentClasses = new TeFindStudentClasses();
            teFindStudentClasses.setIdClass(idClass);
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchStudentClassesByIdClass(teFindStudentClasses);
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
            int index = 1;
            for (TePointExcel data : listExcel) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(index++);
                dataRow.createCell(1).setCellValue(data.getName());
                dataRow.createCell(2).setCellValue(data.getEmail());
                dataRow.createCell(3).setCellValue(data.getCheckPointPhase1());
                dataRow.createCell(4).setCellValue(data.getCheckPointPhase2());
                dataRow.createCell(5).setCellValue(data.getFinalPoint());
            }
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
