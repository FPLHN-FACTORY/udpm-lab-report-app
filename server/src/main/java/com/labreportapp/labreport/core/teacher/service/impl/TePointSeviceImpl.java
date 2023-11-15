package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportPoint;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportService;
import com.labreportapp.labreport.core.teacher.model.request.Base.TePointExcel;
import com.labreportapp.labreport.core.teacher.model.request.TeFindListPointRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindPointRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.labreport.core.teacher.model.response.TePointResponse;
import com.labreportapp.labreport.core.teacher.model.response.TePointStudentInforRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentStatusApiResponse;
import com.labreportapp.labreport.core.teacher.repository.TeClassConfigurationRepository;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TePointRepository;
import com.labreportapp.labreport.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.labreport.core.teacher.service.TePointSevice;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.labreport.entity.Point;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.infrastructure.constant.StatusTeam;
import com.labreportapp.labreport.util.LoggerUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Synchronized;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author hieundph25894
 */
@Service
public class TePointSeviceImpl implements TePointSevice {

    @Autowired
    private TePointRepository tePointRepository;

    @Autowired
    private TeStudentClassesRepository teStudentClassesRepository;

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private TeExcelImportService tePointImportService;

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private TeClassConfigurationRepository teClassConfigurationRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public List<TePointStudentInforRespone> getPointStudentByIdClass(String idClass) {
        List<TePointResponse> list = tePointRepository.getAllPointByIdClass(idClass);
        List<TeStudentStatusApiResponse> listInfor = teStudentClassesService.searchApiStudentClassesStatusByIdClass(idClass);
        List<TePointStudentInforRespone> listReturn = new ArrayList<>();
        ClassConfiguration classConfiguration = teClassConfigurationRepository.findAll().get(0);
        if (list == null && listInfor == null && classConfiguration == null) {
            return null;
        }
        list.forEach(point -> {
            listInfor.forEach(infor -> {
                if (point.getIdStudent().equals(infor.getIdStudent())) {
                    TePointStudentInforRespone student = new TePointStudentInforRespone();
                    student.setStt(point.getStt());
                    student.setIdStudentClasses(point.getIdStudentClass());
                    student.setIdPoint(point.getIdPoint());
                    student.setIdStudent(point.getIdStudent());
                    student.setUsername(infor.getUsername());
                    student.setNameStudent(infor.getName());
                    student.setStatusTeam(infor.getStatusTeam());
                    if (point.getNameTeam() == null) {
                        student.setNameTeam("");
                    } else {
                        student.setNameTeam(point.getNameTeam());
                    }
                    student.setEmailStudent(infor.getEmail());
                    if (point.getCheckPointPhase1() == null) {
                        student.setCheckPointPhase1(0.0);
                    } else {
                        student.setCheckPointPhase1(point.getCheckPointPhase1());
                    }
                    if (point.getCheckPointPhase2() == null) {
                        student.setCheckPointPhase2(0.0);
                    } else {
                        student.setCheckPointPhase2(point.getCheckPointPhase2());
                    }
                    if (point.getFinalPoint() == null) {
                        student.setFinalPoint(0.0);
                    } else {
                        student.setFinalPoint(point.getFinalPoint());
                    }
                    student.setIdClass(point.getIdClass());
                    student.setNumberOfSessionAttended(point.getSoBuoiDiHoc());
                    student.setNumberOfSession(point.getSoBuoiPhaiHoc());
                    student.setPointMin(classConfiguration.getPointMin());
                    student.setMaximumNumberOfBreaks(classConfiguration.getMaximumNumberOfBreaks());
                    listReturn.add(student);
                }
            });
        });
        return listReturn;
    }

    @Override
    @Synchronized
    @Transactional
    public List<TePointStudentInforRespone> addOrUpdatePoint(TeFindListPointRequest request) {
        List<TeFindPointRequest> listRequest = request.getListPoint();
        List<Point> listPointDB = tePointRepository.getAllByClassId(request.getIdClass());
        List<StudentClasses> listStudentClass = teStudentClassesRepository.findStudentClassesByIdClass(request.getIdClass());
        List<StudentClasses> listStudentClassUp = new ArrayList<>();
        List<Point> listPointAddOrUp = new ArrayList<>();

        List<SimpleResponse> listInforStudent = teStudentClassesService.searchAllStudentByIdClass(request.getIdClass());
        StringBuilder message = new StringBuilder();
        if (listPointDB.size() <= 0) {
            listRequest.forEach(item -> {
                Point point = new Point();
                point.setStudentId(item.getIdStudent());
                point.setClassId(item.getIdClass());
                point.setCheckPointPhase1(item.getCheckPointPhase1());
                point.setCheckPointPhase2(item.getCheckPointPhase2());
                point.setFinalPoint((item.getCheckPointPhase1() + item.getCheckPointPhase2()) / 2);
                point.setCreatedDate(new Date().getTime());
                point.setLastModifiedDate(new Date().getTime());
                listInforStudent.forEach(infor -> {
                    if (infor.getId().equals(point.getStudentId())) {
                        message.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName())
                                .append(" <i style=\"color: black\">(").append(point.getCheckPointPhase1()).append(" và ").append(point.getCheckPointPhase2()).append(")</i>").append(",");
                    }
                });
                listPointAddOrUp.add(point);
            });
        } else {
            listPointDB.forEach(itemDB -> {
                listRequest.forEach(item -> {
                    if (itemDB.getStudentId().equals(item.getIdStudent())) {
                        Point point = new Point();
                        point.setId(itemDB.getId());
                        point.setStudentId(itemDB.getStudentId());
                        point.setClassId(itemDB.getClassId());
                        point.setCheckPointPhase1(item.getCheckPointPhase1());
                        point.setCheckPointPhase2(item.getCheckPointPhase2());
                        point.setFinalPoint((item.getCheckPointPhase1() + item.getCheckPointPhase2()) / 2);
                        point.setCreatedDate(itemDB.getCreatedDate());
                        listInforStudent.forEach(infor -> {
                            if (infor.getId().equals(item.getIdStudent())
                            ) {
                                if (!itemDB.getCheckPointPhase1().equals(item.getCheckPointPhase1()) || !itemDB.getCheckPointPhase2().equals(item.getCheckPointPhase2())) {
                                    message.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName()).
                                            append(" từ <i style=\"color: black\">(").append(itemDB.getCheckPointPhase1()).append(" và ").append(itemDB.getCheckPointPhase2()).append(")</i>")
                                            .append(" thành <i style=\"color: red\">(").append(point.getCheckPointPhase1()).append(" và ").append(point.getCheckPointPhase2()).append(")</i>").append(",");
                                }
                            }
                        });
                        listPointAddOrUp.add(point);
                    }
                });
            });
        }
        listRequest.forEach(item -> {
            listStudentClass.forEach(student -> {
                if (student.getStudentId().equals(item.getIdStudent())) {
                    StudentClasses studentClasses = new StudentClasses();
                    studentClasses.setId(student.getId());
                    studentClasses.setStudentId(student.getStudentId());
                    studentClasses.setEmail(student.getEmail());
                    studentClasses.setTeamId(student.getTeamId());
                    studentClasses.setClassId(student.getClassId());
                    studentClasses.setRole(student.getRole());
                    studentClasses.setStatus(item.getStatusTeam() == 0 ? StatusTeam.ACTIVE : StatusTeam.INACTIVE);
                    studentClasses.setStatusStudentFeedBack(student.getStatusStudentFeedBack());
                    listStudentClassUp.add(studentClasses);
                }
            });
        });
        if (message.length() > 0) {
            message.insert(0, "Đã cập nhật điểm giai đoạn 1, giai đoạn 2 của sinh viên: ");
            if (message.charAt(message.length() - 1) == ',') {
                message.setCharAt(message.length() - 1, '.');
            }
        } else {
            message.append("Đã cập nhật điểm giai đoạn 1, giai đoạn 2 cho sinh viên nhưng không có sự thay đổi.");
        }
        String codeClass = loggerUtil.getCodeClassByIdClass(request.getIdClass());
        String nameSemester = loggerUtil.getNameSemesterByIdClass(request.getIdClass());
        loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);
        tePointRepository.saveAll(listPointAddOrUp);
        teStudentClassesRepository.saveAll(listStudentClassUp);
        listInforStudent.clear();
        listPointAddOrUp.clear();
        listPointDB.clear();
        listRequest.clear();
        listStudentClass.clear();
        listStudentClassUp.clear();
        return getPointStudentByIdClass(request.getIdClass());

    }

    private Sheet configTitle(Workbook workbook, String name) {
        Font fontTitle = workbook.createFont();
        Sheet sheet = workbook.createSheet("Bảng điểm");
        Row titleRow = sheet.createRow(0);
        Cell cellTitle = titleRow.createCell(0);
        cellTitle.setCellValue("DANH SÁCH ĐIỂM SINH VIÊN LỚP " + name);
        cellTitle.setCellStyle(chooseCellStyle("title", workbook));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 8000);
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 7000);
        Row headerRow = sheet.createRow(2);
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("STT");
        cell0.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("Tên sinh viên");
        cell1.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell2 = headerRow.createCell(2);
        cell2.setCellValue("Email");
        cell2.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell3 = headerRow.createCell(3);
        cell3.setCellValue("Điểm giai đoạn 1");
        cell3.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell4 = headerRow.createCell(4);
        cell4.setCellValue("Điểm giai đoạn 2");
        cell4.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell5 = headerRow.createCell(5);
        cell5.setCellValue("Điểm giai đoạn cuối");
        cell5.setCellStyle(chooseCellStyle("titleTable", workbook));
        return sheet;
    }

    private CellStyle chooseCellStyle(String type, Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font fontStyle = workbook.createFont();
        fontStyle.setFontName("Times New Roman");
        if (type.equals("title")) {
            fontStyle.setBold(true);
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
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        if (type.equals("dataCenterTable")) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFont(fontStyle);
        }
        return cellStyle;
    }

    @Override
    @Synchronized
    public ByteArrayOutputStream exportExcel(HttpServletResponse response, String idClass) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            List<TePointStudentInforRespone> listPointIdClass = getPointStudentByIdClass(idClass);
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchApiStudentClassesByIdClass(idClass);
            List<TePointExcel> listExcel = new ArrayList<>();
            listPointIdClass.forEach((item1) -> {
                listStudent.forEach((item2) -> {
                    if (item2.getIdStudent().equals(item1.getIdStudent())) {
                        TePointExcel point = new TePointExcel();
                        point.setIdPoint(item1.getIdPoint());
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
            Sheet sheet = configTitle(workbook, objClass.getCode());
            int rowIndex = 3;
            int index = 1;
            for (TePointExcel data : listExcel) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(index++);
                dataRow.getCell(0).setCellStyle(chooseCellStyle("dataCenterTable", workbook));
                dataRow.createCell(1).setCellValue(data.getName());
                dataRow.getCell(1).setCellStyle(chooseCellStyle("dataTable", workbook));
                dataRow.createCell(2).setCellValue(data.getEmail());
                dataRow.getCell(2).setCellStyle(chooseCellStyle("dataTable", workbook));
                dataRow.createCell(3).setCellValue(data.getCheckPointPhase1());
                dataRow.getCell(3).setCellStyle(chooseCellStyle("dataCenterTable", workbook));
                dataRow.createCell(4).setCellValue(data.getCheckPointPhase2());
                dataRow.getCell(4).setCellStyle(chooseCellStyle("dataCenterTable", workbook));
                dataRow.createCell(5).setCellValue(data.getFinalPoint());
                dataRow.getCell(5).setCellStyle(chooseCellStyle("dataCenterTable", workbook));
            }
            for (int i = 0; i < 6; i++) {
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

    private boolean isExcelFile(MultipartFile file) {
        Workbook workbookCheck = null;
        try {
            workbookCheck = new XSSFWorkbook(file.getInputStream());
        } catch (Exception e) {
            try {
                workbookCheck = new HSSFWorkbook(file.getInputStream());
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Synchronized
    @Transactional
    public TeExcelResponseMessage importExcel(MultipartFile file, String idClass) {
        TeExcelResponseMessage teExcelResponseMessage = new TeExcelResponseMessage();
        try {
            boolean isExcelFile = isExcelFile(file);
            if (!isExcelFile) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("File excel sai định dạng, vui lòng export mẫu để sử dụng !");
                return teExcelResponseMessage;
            }
            List<TeExcelImportPoint> listInput = tePointImportService.importDataPoint(file, idClass);
            if (listInput.size() == 0) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("File excel trống, vui lòng export mẫu để sử dụng !");
                return teExcelResponseMessage;
            }
            ConcurrentHashMap<String, SimpleResponse> mapPointStudent = new ConcurrentHashMap<>();
            addDataMapsPointStudent(mapPointStudent, idClass);
//            if (listInput.size() != mapPointStudent.size()) {
//                teExcelResponseMessage.setStatus(false);
//                teExcelResponseMessage.setMessage("Import thất bại. Số lượng sinh viên, số lượng điểm trong " +
//                        "file excel phải bằng với số lượng sinh viên trong lớp, vui lòng export mẫu để sử dụng !");
//                return teExcelResponseMessage;
//            }
            ConcurrentHashMap<String, Point> mapPointStudentDB = new ConcurrentHashMap<>();
            addDataMapsPointStudentDB(mapPointStudentDB, idClass);
            ConcurrentHashMap<String, Point> pointUpdate = new ConcurrentHashMap<>();
            teExcelResponseMessage.setStatus(true);
            teExcelResponseMessage.setMessage("");

            ConcurrentLinkedQueue<String> messagesConcurrent = new ConcurrentLinkedQueue<>();
            listInput.parallelStream().forEach(point -> {
                String regexName = "^[\\p{L}'\\-\\d]+(?:\\s[\\p{L}'\\-\\d]+)*$";
                String regexEmailExactly = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                String regexDouble = "^(?:[0-9](?:\\.\\d*)?|10(?:\\.0*)?)$";
                if (point.getName().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Họ tên không được để trống !");
                    return;
                } else {
                    if (!point.getName().matches(regexName)) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(" Họ tên phải là chữ cái và các chữ cách nhau 1 dấu cách !");
                        return;
                    }
                }
                if (point.getCheckPointPhase1().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Điểm giai đoạn 1 không được để trống !");
                    return;
                }
                if (point.getCheckPointPhase2().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Điểm giai đoạn 2 không được để trống !");
                    return;
                }
                if (!point.getCheckPointPhase1().matches(regexDouble) || !point.getCheckPointPhase2().matches(regexDouble)) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Điểm giai đoạn 1 và giai đoạn 2 phải là số từ 0 tới 10 !");
                    return;
                }
                SimpleResponse simpleResponse = mapPointStudent.get(point.getEmail());
                if (point.getEmail().isEmpty()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Email không được để trống !");
                    return;
                } else if (!point.getEmail().matches(regexEmailExactly)) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Email sai định dạng !");
                    return;
                } else {
                    if (simpleResponse == null) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(" Email của sinh viên không tồn tại !");
                        return;
                    }
                }
                Point pointUpd = mapPointStudentDB.get(simpleResponse.getId());
                double phase1 = Double.parseDouble((point.getCheckPointPhase1()));
                double phase2 = Double.parseDouble((point.getCheckPointPhase2()));
                if (pointUpd != null) {
                    if (pointUpd.getCheckPointPhase1() != phase1 || pointUpd.getCheckPointPhase2() != phase2) {
                        messagesConcurrent.add(" " + simpleResponse.getName() + " - " + simpleResponse.getUserName() +
                                " từ <i style=\"color: black\">(" + pointUpd.getCheckPointPhase1() + " và " + pointUpd.getCheckPointPhase2() + ")"
                                + "</i> thành  <i style=\"color: red\">(" + phase1 + " và " + phase2 + ")</i>,");
                    }
                    pointUpd.setCheckPointPhase1(phase1);
                    pointUpd.setCheckPointPhase2(phase2);
                    pointUpd.setFinalPoint(Double.parseDouble(String.valueOf((phase1 + phase2) / 2)));
                    pointUpdate.put(pointUpd.getStudentId(), pointUpd);
                } else {
                    Point pointNew = new Point();
                    pointNew.setClassId(idClass);
                    pointNew.setCheckPointPhase1(phase1);
                    pointNew.setCheckPointPhase2(phase2);
                    pointNew.setStudentId(simpleResponse.getId());
                    pointNew.setFinalPoint(Double.parseDouble(String.valueOf((phase1 + phase2) / 2)));
                    pointUpdate.put(pointNew.getStudentId(), pointNew);
                    messagesConcurrent.add(" " + simpleResponse.getName() + " - " + simpleResponse.getUserName() +
                            " là <i style=\"color: red\">(" + phase1 + " và " + phase2 + ")</i>,");
                }
            });
            if (teExcelResponseMessage.getStatus()) {
                tePointRepository.saveAll(pointUpdate.values());
                teExcelResponseMessage.setMessage("Import điểm thành công !");

                String codeClass = loggerUtil.getCodeClassByIdClass(idClass);
                String nameSemester = loggerUtil.getNameSemesterByIdClass(idClass);
                StringBuilder message = new StringBuilder();
                StringJoiner messageJoiner = new StringJoiner(" ");
                messagesConcurrent.forEach(messageJoiner::add);
                if (messageJoiner.length() > 0) {
                    message.append("Đã cập nhật điểm giai đoạn 1, giai đoạn 2 cho sinh viên: ").append(messageJoiner.toString());
                    if (message.charAt(message.length() - 1) == ',') {
                        message.setCharAt(message.length() - 1, '.');
                    }
                } else {
                    message.append("Đã cập nhật điểm giai đoạn 1, giai đoạn 2 cho sinh viên nhưng không có sự thay đổi.");
                }
                loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);
            } else {
                teExcelResponseMessage.setMessage("Import điểm thất bại. " + teExcelResponseMessage.getMessage());
            }
            listInput.clear();
            mapPointStudent.clear();
            mapPointStudentDB.clear();
            messagesConcurrent.clear();
            file.getInputStream().close();
            return teExcelResponseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            teExcelResponseMessage.setStatus(false);
            teExcelResponseMessage.setMessage("Lỗi hệ thống vui lòng F5 lại trang !");
            return teExcelResponseMessage;
        }
    }

    public void addDataMapsPointStudent(ConcurrentHashMap<String, SimpleResponse> mapAll, String idClass) {
        List<SimpleResponse> listStudent = teStudentClassesService.searchAllStudentByIdClass(idClass);
        getAllPutMapPoint(mapAll, listStudent);
    }

    public void getAllPutMapPoint
            (ConcurrentHashMap<String, SimpleResponse> mapSimple, List<SimpleResponse> listStudent) {
        for (SimpleResponse student : listStudent) {
            mapSimple.put(student.getEmail(), student);
        }
        listStudent.clear();
    }

    public void addDataMapsPointStudentDB(ConcurrentHashMap<String, Point> mapPointDB, String idClass) {
        List<Point> listPoint = tePointRepository.getAllPointByIdClassImport(idClass);
        getAllPutMapPointDB(mapPointDB, listPoint);
    }

    public void getAllPutMapPointDB(ConcurrentHashMap<String, Point> mapPoint, List<Point> listPointDB) {
        for (Point point : listPointDB) {
            mapPoint.put(point.getStudentId(), point);
        }
        listPointDB.clear();
    }


}
