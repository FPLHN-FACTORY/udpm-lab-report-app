package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.model.request.TeFindClassStatisticalRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeCountClassReponse;
import com.labreportapp.labreport.core.teacher.model.response.TeCountMeetingAndSheetRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeHwNoteReportListRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeamColor;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.labreport.core.teacher.repository.TeTeamsRepositoty;
import com.labreportapp.labreport.core.teacher.service.TeStatisticalService;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.Team;
import com.labreportapp.labreport.util.SemesterHelper;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
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
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TeStatisticalServiceImpl implements TeStatisticalService {

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private TeTeamsRepositoty teTeamsRepositoty;

    @Autowired
    private TeMeetingRepository teMeetingRepository;

    public short getRandomValidBrightColor(List<Short> listColor) {
        Random random = new Random();
        int randomIndex = random.nextInt(listColor.size());
        return listColor.get(randomIndex);
    }

    public List<Short> getExcelColors() {
        List<Short> excelColors = new ArrayList<>();
        for (IndexedColors color : IndexedColors.values()) {
            short index = color.getIndex();
            if (index == IndexedColors.LIGHT_YELLOW.getIndex()
                    || index == IndexedColors.LIGHT_GREEN.getIndex() || index == IndexedColors.LIGHT_TURQUOISE.getIndex()
                    || index == IndexedColors.LIGHT_ORANGE.getIndex() || index == IndexedColors.LIME.getIndex()
                    || index == IndexedColors.YELLOW.getIndex() || index == IndexedColors.LIGHT_BLUE.getIndex()
                    || index == IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex() || index == IndexedColors.LIGHT_TURQUOISE1.getIndex()
                    || index == IndexedColors.PINK.getIndex() || index == IndexedColors.YELLOW1.getIndex()
                    || index == IndexedColors.ROSE.getIndex()
                    || index == IndexedColors.SKY_BLUE.getIndex() || index == IndexedColors.LEMON_CHIFFON.getIndex()
                    || index == IndexedColors.BRIGHT_GREEN1.getIndex()) {
                excelColors.add(index);
            }
        }
        return excelColors;
    }

    public short getColorForTeamId(String teamId, List<TeamColor> listTeamColor) {
        for (TeamColor item : listTeamColor) {
            if (teamId != null && !teamId.isEmpty() && teamId.equals(item.getId())) {
                return item.getColor();
            }
        }
        return IndexedColors.WHITE.getIndex();
    }

    @Override
    @Synchronized
    public ByteArrayOutputStream exportExcelStatistical(HttpServletResponse response, String idClass) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Optional<Class> objClassOptional = teClassRepository.findById(idClass);
            if (!objClassOptional.isPresent()) {
                throw new RestApiException(Message.CLASS_NOT_EXISTS);
            }
            List<Team> listTeam = teTeamsRepositoty.getTeamByClassId(idClass);
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchApiStudentClassesByIdClass(idClass);
            TeFindMeetingRequest request = new TeFindMeetingRequest();
            request.setIdClass(idClass);

            List<TeamColor> listTeamColor = new ArrayList<>();
            var ref = new Object() {
                List<Short> listShortColor = getExcelColors();
            };
            listTeam.forEach(team -> {
                if (ref.listShortColor.size() == 0) {
                    ref.listShortColor = getExcelColors();
                }
                TeamColor color = new TeamColor();
                color.setId(team.getId());
                color.setCode(team.getCode());
                color.setName(team.getName());
                color.setSubjectName(team.getSubjectName());
                color.setClassId(team.getClassId());
                color.setProjectId(team.getProjectId());
                color.setColor(getRandomValidBrightColor(ref.listShortColor));
                ref.listShortColor.removeIf(item -> item.equals(color.getColor()));
                listTeamColor.add(color);
            });
            listTeamColor.forEach(tem -> {
                int start = -1;
                int end = -1;
                for (int i = 0; i < listStudent.size(); i++) {
                    TeStudentCallApiResponse student = listStudent.get(i);
                    if (student.getIdTeam() != null && tem.getId().equals(student.getIdTeam())) {
                        if (start == -1) {
                            start = i;
                        }
                        end = i;
                    } else if (student.getIdTeam() == null || student.getIdTeam().equals("")) {
                        continue;
                    }
                }
                if (start != -1 && end != -1) {
                    tem.setRowStartMeger(start + 5);
                    tem.setRowEndMeger(end + 5);
                }
            });
            List<TeHwNoteReportListRespone> listMeetingHwNoRep = teMeetingRepository.searchHwNoteReport(idClass);
            Class objClass = objClassOptional.get();
            TeCountMeetingAndSheetRespone responseSheet = configTitle(workbook, objClass);
            Sheet sheet = responseSheet.getSheet();

            int rowIndex = 5;
            int index = 1;
            for (TeStudentCallApiResponse data : listStudent) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(index++);
                dataRow.getCell(0).setCellStyle(chooseCellStyle("dataCenterTableColor", workbook,
                        getColorForTeamId(data.getIdTeam(), listTeamColor)));

                dataRow.createCell(1).setCellValue(data.getName());
                dataRow.getCell(1).setCellStyle(data.getRole() != null && data.getIdTeam() != null && data.getRole().equals("0") ?
                        chooseCellStyle("dataTableLead", workbook, getColorForTeamId(data.getIdTeam(), listTeamColor)) :
                        chooseCellStyle("dataTable", workbook, getColorForTeamId(data.getIdTeam(), listTeamColor))
                );
                dataRow.createCell(2).setCellValue(data.getEmail());
                dataRow.getCell(2).setCellStyle(chooseCellStyle("dataTable", workbook,
                        getColorForTeamId(data.getIdTeam(), listTeamColor)));

                dataRow.createCell(3).setCellValue(data.getStatusStudent() != null
                        && data.getStatusStudent().equals("0") ? "Đạt" : "Trượt");
                dataRow.getCell(3).setCellStyle(data.getStatusStudent() != null
                        && data.getStatusStudent().equals("0") ? chooseCellStyle("dat", workbook,
                        getColorForTeamId(data.getIdTeam(), listTeamColor)) : chooseCellStyle("truot", workbook,
                        getColorForTeamId(data.getIdTeam(), listTeamColor)));

                dataRow.createCell(4).setCellValue(data.getNameTeam());
                dataRow.getCell(4).setCellStyle(chooseCellStyle("dataCenterTable", workbook,
                        IndexedColors.WHITE.getIndex()));

                dataRow.createCell(5).setCellValue(data.getSubjectName());
                dataRow.getCell(5).setCellStyle(chooseCellStyle("dataTableDoc", workbook,
                        IndexedColors.WHITE.getIndex()));

                List<TeHwNoteReportListRespone> listHomeWorkNoteReportAddList = new ArrayList<>();
                listMeetingHwNoRep.forEach(item -> {
                    if (item.getIdTeam() != null && item.getIdTeam().equals(data.getIdTeam())) {
                        listHomeWorkNoteReportAddList.add(item);
                    }
                });
                List<TeHwNoteReportListRespone> listMeetingSort = listHomeWorkNoteReportAddList.stream()
                        .sorted(Comparator.comparing(TeHwNoteReportListRespone::getMeetingDate)
                                .thenComparing(TeHwNoteReportListRespone::getNameMeeting))
                        .collect(Collectors.toList());

                AtomicInteger collFill = new AtomicInteger(6);
                listMeetingSort.forEach(item -> {
                    if (data.getIdTeam() != null && data.getIdTeam().equals(item.getIdTeam())) {
                        dataRow.createCell(collFill.get()).setCellValue(
                                item.getDescriptionsNote() != null && !item.getDescriptionsNote().equals("")
                                        ? item.getDescriptionsNote() : "");
                        dataRow.getCell(collFill.get()).setCellStyle(
                                item.getDescriptionsNote() != null && !item.getDescriptionsNote().equals("") ?
                                        chooseCellStyle("dataTableDoc", workbook, IndexedColors.WHITE.getIndex())
                                        : chooseCellStyle("null", workbook, IndexedColors.WHITE.getIndex()));

                        dataRow.createCell(collFill.get() + 1).setCellValue(
                                item.getDescriptionsHomeWork() != null && !item.getDescriptionsHomeWork().equals("") ? item.getDescriptionsHomeWork() : "");
                        dataRow.getCell(collFill.get() + 1).setCellStyle(
                                item.getDescriptionsHomeWork() != null && !item.getDescriptionsHomeWork().equals("") ?
                                        chooseCellStyle("dataTableDoc", workbook,
                                                IndexedColors.WHITE.getIndex()) : chooseCellStyle("null", workbook,
                                        IndexedColors.WHITE.getIndex()));

                        dataRow.createCell(collFill.get() + 2).setCellValue(
                                item.getDescriptionsReport() != null ? item.getDescriptionsReport() : "");
                        dataRow.getCell(collFill.get() + 2).setCellStyle(
                                item.getDescriptionsReport() != null && !item.getDescriptionsReport().equals("") ?
                                        chooseCellStyle("dataTableDoc", workbook, IndexedColors.WHITE.getIndex())
                                        : chooseCellStyle("null", workbook, IndexedColors.WHITE.getIndex()));
                        collFill.set(collFill.get() + 3);
                    }
                });
            }
            Integer checkColMeger = responseSheet.getCountMeeting();
            listTeamColor.forEach(tem -> {
                if (tem.getRowEndMeger() != null && tem.getRowStartMeger() != null) {
                    if (tem.getRowEndMeger() - tem.getRowStartMeger() > 1) {
                        for (int i = 4; i < checkColMeger; i++) {
                            CellRangeAddress mergedRegion = new CellRangeAddress(
                                    tem.getRowStartMeger(), tem.getRowEndMeger(), i, i);
                            sheet.addMergedRegion(mergedRegion);
                            megerBoder(mergedRegion, sheet);
                        }
                    }
                }
            });
            for (int i = 1; i < checkColMeger; i++) {
                sheet.autoSizeColumn(i, true);
            }
            workbook.write(response.getOutputStream());
            workbook.close();
            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private TeCountMeetingAndSheetRespone configTitle(Workbook workbook, Class classDetail) {
        Font fontTitle = workbook.createFont();
        Sheet sheet = workbook.createSheet(classDetail.getCode());
        Row titleRow = sheet.createRow(0);
        Cell cellTitle = titleRow.createCell(0);
        cellTitle.setCellValue("THỐNG KÊ CHI TIẾT LỚP " + classDetail.getCode());
        cellTitle.setCellStyle(chooseCellStyle("title", workbook, IndexedColors.WHITE.getIndex()));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));
        sheet.setColumnWidth(0, 1300);

        sheet.createFreezePane(5, 5);
        Row headerRow = sheet.createRow(2);
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("STT");
        cell0.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));
        sheet.addMergedRegion(new CellRangeAddress(2, 4, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(2, 4, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(2, 4, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(2, 4, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(2, 4, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(2, 4, 5, 5));
        megerBoder(new CellRangeAddress(2, 4, 0, 0), sheet);
        megerBoder(new CellRangeAddress(2, 4, 1, 1), sheet);
        megerBoder(new CellRangeAddress(2, 4, 2, 2), sheet);
        megerBoder(new CellRangeAddress(2, 4, 3, 3), sheet);
        megerBoder(new CellRangeAddress(2, 4, 4, 4), sheet);
        megerBoder(new CellRangeAddress(2, 4, 5, 5), sheet);
        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("Họ tên");
        cell1.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));

        Cell cell2 = headerRow.createCell(2);
        cell2.setCellValue("Email");
        cell2.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));

        Cell cell3 = headerRow.createCell(3);
        cell3.setCellValue("Trạng thái");
        cell3.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));

        Cell cell4 = headerRow.createCell(4);
        cell4.setCellValue("Nhóm");
        cell4.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));

        Cell cell5 = headerRow.createCell(5);
        cell5.setCellValue("Chủ đề");
        cell5.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));


        Row rowMeeting = sheet.createRow(3);
        Row rowInforMeeting = sheet.createRow(4);
        AtomicInteger checkMeger = new AtomicInteger(6);
        TeFindMeetingRequest request = new TeFindMeetingRequest();
        request.setIdClass(classDetail.getId());
        List<TeMeetingResponse> listMeeting = teMeetingRepository.findMeetingByIdClass(request);
        List<TeMeetingResponse> listMeetingSort = listMeeting.stream()
                .sorted(Comparator.comparing(TeMeetingResponse::getMeetingDate)
                        .thenComparing(TeMeetingResponse::getName))
                .collect(Collectors.toList());

        AtomicInteger collTitle = new AtomicInteger(6);

        if (listMeetingSort.size() > 0) {
            listMeetingSort.forEach(item -> {
                Cell cell6 = rowMeeting.createCell(collTitle.get());
                cell6.setCellValue(item.getName());
                CellRangeAddress mergedRegion6 = new CellRangeAddress(3, 3, collTitle.get(), collTitle.get() + 2);
                sheet.addMergedRegion(mergedRegion6);
                cell6.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));
                megerBoder(mergedRegion6, sheet);

                Cell cellNote = rowInforMeeting.createCell(collTitle.get());
                cellNote.setCellValue("Nhận xét");
                cellNote.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));

                Cell cellHw = rowInforMeeting.createCell(collTitle.get() + 1);
                cellHw.setCellValue("Yêu cầu về nhà");
                cellHw.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));

                Cell cellReport = rowInforMeeting.createCell(collTitle.get() + 2);
                cellReport.setCellValue("Báo cáo");
                cellReport.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));
                collTitle.set(collTitle.get() + 3);
                checkMeger.set(checkMeger.get() + 3);
            });
        } else {
            Cell cell6 = rowMeeting.createCell(collTitle.get());
            CellRangeAddress cellMeger = new CellRangeAddress(3, 4, collTitle.get(), collTitle.get() + 1);
            sheet.addMergedRegion(cellMeger);
            cell6.setCellValue("Không có buổi học");
            cell6.setCellStyle(chooseCellStyle("titleMeetingNull", workbook, IndexedColors.WHITE.getIndex()));
            megerBoder(cellMeger, sheet);
        }
        Cell cellTitleHw = headerRow.createCell(6);
        cellTitleHw.setCellValue("Theo dõi tiến độ");
        CellRangeAddress cellTitleMeger = new CellRangeAddress(2, 2, 6,
                checkMeger.get() > 6 ? checkMeger.get() - 1 : 7);
        sheet.addMergedRegion(cellTitleMeger);
        cellTitleHw.setCellStyle(chooseCellStyle("titleThongKe", workbook, IndexedColors.WHITE.getIndex()));
        megerBoder(cellTitleMeger, sheet);
        return new TeCountMeetingAndSheetRespone(sheet, checkMeger.get());
    }

    private void megerBoder(CellRangeAddress cellRangeAddress, Sheet sheet) {
        RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet);
    }

    private CellStyle chooseCellStyle(String type, Workbook workbook, short color) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font fontStyle = workbook.createFont();
        fontStyle.setFontName("Times New Roman");
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        if (type.equals("title")) {
            fontStyle.setBold(true);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            fontStyle.setFontHeightInPoints((short) 20);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("titleTable")) {
            fontStyle.setBold(true);
            fontStyle.setColor(IndexedColors.WHITE.getIndex());
            fontStyle.setFontHeightInPoints((short) 13);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFont(fontStyle);
            cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        }
        if (type.equals("dataTable")) {
            cellStyle.setFillForegroundColor(color);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("dataTableLead")) {
            fontStyle.setBold(true);
            cellStyle.setFillForegroundColor(color);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("dataCenterTableColor")) {
            cellStyle.setFillForegroundColor(color);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("dataCenterTable")) {
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("dat")) {
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            fontStyle.setColor(IndexedColors.GREEN.getIndex());
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("truot")) {
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            fontStyle.setColor(IndexedColors.RED.getIndex());
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("titleThongKe")) {
            fontStyle.setBold(true);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("titleMeetingNull")) {
            fontStyle.setBold(true);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            fontStyle.setColor(IndexedColors.RED.getIndex());
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("null")) {
            fontStyle.setBold(true);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("dataTableDoc")) {
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        return cellStyle;
    }

    @Override
    public TeCountClassReponse findCount(TeFindClassStatisticalRequest request) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (request.getIdSemester().equalsIgnoreCase("")) {
            if (idSemesterCurrent != null) {
                request.setIdSemester(idSemesterCurrent);
                request.setIdActivity("");
            } else {
                request.setIdSemester("");
            }
        }
        TeCountClassReponse response = teClassRepository.findCount(request);
        return response;
    }


}
