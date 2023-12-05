package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportMeetingRequest;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportService;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleMeetingClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleNowToTime;
import com.labreportapp.labreport.core.teacher.model.request.TeScheduleUpdateMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateHomeWorkAndNoteInMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailMeetingTeamReportRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailTeamReportRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeScheduleMeetingClassResponse;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeHomeWorkRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingPeriodRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRequestRepository;
import com.labreportapp.labreport.core.teacher.repository.TeNoteRepository;
import com.labreportapp.labreport.core.teacher.repository.TeReportRepository;
import com.labreportapp.labreport.core.teacher.repository.TeTeamsRepositoty;
import com.labreportapp.labreport.core.teacher.service.TeMeetingService;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.HomeWork;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.entity.MeetingRequest;
import com.labreportapp.labreport.entity.Note;
import com.labreportapp.labreport.entity.Report;
import com.labreportapp.labreport.entity.Team;
import com.labreportapp.labreport.infrastructure.apiconstant.ActorConstants;
import com.labreportapp.labreport.infrastructure.constant.StatusClass;
import com.labreportapp.labreport.infrastructure.constant.StatusMeeting;
import com.labreportapp.labreport.infrastructure.constant.StatusMeetingRequest;
import com.labreportapp.labreport.infrastructure.constant.TypeMeeting;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.DateConverter;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.labreport.util.RandomString;
import com.labreportapp.labreport.util.SemesterHelper;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TeMeetingServiceImpl implements TeMeetingService {

    @Autowired
    private TeMeetingRepository teMeetingRepository;

    @Autowired
    private TeHomeWorkRepository teHomeWorkRepository;

    @Autowired
    private TeNoteRepository teNoteRepository;

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private TeMeetingRequestRepository teMeetingRequestRepository;

    @Autowired
    private TeReportRepository teReportRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private TeMeetingPeriodRepository teMeetingPeriodRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private TeTeamsRepositoty teTeamsRepositoty;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private TeExcelImportService teExcelImportService;


    @Override
    public TeMeetingResponse searchMeetingAndCheckAttendanceByIdMeeting(TeFindMeetingRequest request) {
        Optional<TeMeetingResponse> meeting = teMeetingRepository.searchMeetingByIdMeeting(request);
        if (meeting.isEmpty()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        TeMeetingResponse meetingFind = meeting.get();
        LocalDate dateNow = LocalDate.now();
        LocalDate dateMeeting = Instant.ofEpochMilli(meetingFind.getMeetingDate()).atZone(ZoneId.systemDefault()).toLocalDate();
        if (dateNow.isBefore(dateMeeting)) {
            throw new RestApiException(Message.MEETING_HAS_NOT_COME);
        } else if (dateNow.isAfter(dateMeeting)) {
            throw new RestApiException(Message.MEETING_IS_OVER);
        } else {
            Optional<MeetingPeriod> period = teMeetingPeriodRepository.findById(meetingFind.getIdMeetingPeriod());
            if (period.isEmpty()) {
                throw new RestApiException(Message.MEETING_PERIOD_NOT_EXITS);
            }
            Date currentDate = new Date();
            Date caHocStartTime = new Date(
                    currentDate.getYear(),
                    currentDate.getMonth(),
                    currentDate.getDate(),
                    period.get().getStartHour(),
                    period.get().getStartMinute()
            );
            Date caHocEndTime = new Date(
                    currentDate.getYear(),
                    currentDate.getMonth(),
                    currentDate.getDate(),
                    period.get().getEndHour(),
                    period.get().getEndMinute()
            );
            if (caHocStartTime.before(currentDate) && currentDate.before(caHocEndTime)) {
                return meetingFind;
            } else {
                throw new RestApiException(Message.MEETING_EDIT_ATTENDANCE_FAILD);
            }
        }
    }

    @Override
    public List<TeMeetingCustomResponse> searchMeetingByIdClass(TeFindMeetingRequest request) {
        List<TeMeetingResponse> list = teMeetingRepository.findMeetingByIdClass(request);
        List<String> idTeacherList = list.stream()
                .map(TeMeetingResponse::getIdTeacher)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = callApiIdentity.handleCallApiGetListUserByListId(idTeacherList);
        List<TeMeetingCustomResponse> listReturn = new ArrayList<>();
        list.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdTeacher().equals(respone.getId())) {
                    TeMeetingCustomResponse obj = new TeMeetingCustomResponse();
                    obj.setId(reposi.getId());
                    obj.setName(reposi.getName());
                    obj.setDescriptions(reposi.getDescriptions());
                    obj.setMeetingDate(reposi.getMeetingDate());
                    obj.setMeetingPeriod(reposi.getMeetingPeriod());
                    obj.setStartHour(reposi.getStartHour());
                    obj.setStartMinute(reposi.getStartMinute());
                    obj.setEndHour(reposi.getEndHour());
                    obj.setEndMinute(reposi.getEndMinute());
                    obj.setIdClass(reposi.getIdClass());
                    obj.setTypeMeeting(reposi.getTypeMeeting());
                    obj.setIdTeacher(reposi.getIdTeacher());
                    obj.setUserNameTeacher(respone.getUserName());
                    obj.setStatusMeeting(reposi.getStatusMeeting());
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }

    @Override
    public TeDetailMeetingTeamReportRespone searchMeetingByIdMeeting(TeFindMeetingRequest request) {
        Optional<TeMeetingResponse> meeting = teMeetingRepository.searchMeetingByIdMeeting(request);
        if (meeting.isEmpty()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        TeMeetingResponse meetingResponse = meeting.get();
        TeFindMeetingRequest requestIdClass = new TeFindMeetingRequest();
        requestIdClass.setIdClass(meetingResponse.getIdClass());
        requestIdClass.setIdMeeting(meetingResponse.getId());
        List<TeDetailTeamReportRespone> listTeam = teMeetingRepository.findTeamReportByIdMeetingIdClass(requestIdClass);
        TeDetailMeetingTeamReportRespone objReturn = new TeDetailMeetingTeamReportRespone();
        objReturn.setId(meetingResponse.getId());
        objReturn.setIdClass(meetingResponse.getIdClass());
        objReturn.setIdTeacher(meetingResponse.getIdTeacher());
        objReturn.setName(meetingResponse.getName());
        objReturn.setDescriptions(meetingResponse.getDescriptions());
        objReturn.setMeetingDate(meetingResponse.getMeetingDate());
        objReturn.setTypeMeeting(meetingResponse.getTypeMeeting());
        objReturn.setMeetingPeriod(meetingResponse.getMeetingPeriod());
        objReturn.setStatusClass(meetingResponse.getStatusClass());
        LocalDate dateNow = LocalDate.now();
        LocalDate dateMeeting = Instant.ofEpochMilli(meetingResponse.getMeetingDate()).atZone(ZoneId.systemDefault()).toLocalDate();
        if (dateNow.isBefore(dateMeeting)) {
            objReturn.setListTeamReport(new ArrayList<>());
        } else if (dateNow.isAfter(dateMeeting)) {
            objReturn.setListTeamReport(listTeam);
        } else {
            Optional<MeetingPeriod> period = teMeetingPeriodRepository.findById(meetingResponse.getIdMeetingPeriod());
            if (period.isEmpty()) {
                throw new RestApiException(Message.MEETING_PERIOD_NOT_EXITS);
            }
            Date currentDate = new Date();
            Date caHocStartTime = new Date(
                    currentDate.getYear(),
                    currentDate.getMonth(),
                    currentDate.getDate(),
                    period.get().getStartHour(),
                    period.get().getStartMinute()
            );
            if (caHocStartTime.before(currentDate)) {
                objReturn.setListTeamReport(new ArrayList<>());
            } else {
                objReturn.setListTeamReport(listTeam);
            }
        }
        return objReturn;
    }

    @Override
    public TeHomeWorkAndNoteMeetingResponse searchDetailMeetingTeamByIdMeIdTeam(TeFindMeetingRequest request) {
        Optional<TeHomeWorkAndNoteMeetingResponse> object = teMeetingRepository.searchDetailMeetingTeamByIdMeIdTeam(request);
        if (!object.isPresent()) {
            return null;
        }
        return object.get();
    }

    @Override
    @Synchronized
    @Transactional
    public TeHomeWorkAndNoteMeetingResponse updateDetailMeetingTeamByIdMeIdTeam(TeUpdateHomeWorkAndNoteInMeetingRequest request) {
        Optional<Meeting> meeting = teMeetingRepository.findMeetingById(request.getIdMeeting());
        if (meeting.isEmpty()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        Optional<Class> classFind = teClassRepository.findById(meeting.get().getClassId());
        if (classFind.isEmpty()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        if (classFind.get().getStatusClass().equals(StatusClass.LOCK)) {
            throw new RestApiException("Lớp học đã khóa, không thể gửi yêu cầu !");
        }
        Optional<Team> team = teTeamsRepositoty.findById(request.getIdTeam());
        String codeClass = loggerUtil.getCodeClassByIdClass(meeting.get().getClassId());
        String nameSemester = loggerUtil.getNameSemesterByIdClass(meeting.get().getClassId());
        String nameMeeting = meeting.get().getName();
        String nameTeam = team.isPresent() ? team.get().getName() : "";
        StringBuilder stringHw = new StringBuilder();
        StringBuilder stringNote = new StringBuilder();
        StringBuilder stringReport = new StringBuilder();

        Note noteNew = new Note();
        noteNew.setMeetingId(request.getIdMeeting());
        noteNew.setTeamId(request.getIdTeam());
        noteNew.setDescriptions(request.getDescriptionsNote());
        if (request.getIdNote() != null) {
            Optional<Note> objectNote = teNoteRepository.findById(request.getIdNote());
            if (objectNote.isPresent()) {
                noteNew.setId(objectNote.get().getId());
                String note = "";
                if (objectNote.get().getDescriptions() != null) {
                    note = objectNote.get().getDescriptions();
                }
                if (!request.getDescriptionsNote().equals("") && note.equals("")) {
                    stringNote.append("Đã thêm nhận xét (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsNote()).append("\". ");
                } else if (!request.getDescriptionsNote().equals("") && !request.getDescriptionsNote().equals(note)) {
                    stringNote.append("Đã cập nhật nhận xét (").append(nameMeeting).append(" - ").append(nameTeam).append(") từ \"").append(objectNote.get().getDescriptions()).append("\" thành \"").append(request.getDescriptionsNote()).append("\". ");
                } else if (request.getDescriptionsNote().equals("") && !note.equals("")) {
                    stringNote.append("Đã xóa nhận xét (").append(nameMeeting).append(" - ").append(nameTeam).append("). ");
                }
            }
        } else {
            if (!request.getDescriptionsNote().equals("")) {
                stringNote.append("Đã thêm nhận xét (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsNote()).append("\". ");
            }
        }
        teNoteRepository.save(noteNew);

        HomeWork homeWorkNew = new HomeWork();
        homeWorkNew.setMeetingId(request.getIdMeeting());
        homeWorkNew.setTeamId(request.getIdTeam());
        homeWorkNew.setDescriptions(request.getDescriptionsHomeWork());
        if (request.getIdHomeWork() != null) {
            Optional<HomeWork> objectHW = teHomeWorkRepository.findById(request.getIdHomeWork());
            if (objectHW.isPresent()) {
                homeWorkNew.setId(objectHW.get().getId());
                String homeW = "";
                if (objectHW.get().getDescriptions() != null) {
                    homeW = objectHW.get().getDescriptions();
                }
                if (!request.getDescriptionsHomeWork().equals("") && homeW.equals("")) {
                    stringHw.append("Đã thêm bài tập về nhà (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsHomeWork()).append("\". ");
                } else if (!request.getDescriptionsHomeWork().equals("") && !request.getDescriptionsHomeWork().equals(homeW)) {
                    stringHw.append("Đã cập nhật bài tập về nhà (").append(nameMeeting).append(" - ").append(nameTeam).append(") từ \"").append(homeW).append("\" thành \"").append(request.getDescriptionsHomeWork()).append("\". ");
                } else if (request.getDescriptionsHomeWork().equals("") && !homeW.equals("")) {
                    stringHw.append("Đã xóa bài tập về nhà (").append(nameMeeting).append(" - ").append(nameTeam).append("). ");
                }
            }
        } else {
            if (!request.getDescriptionsHomeWork().equals("")) {
                stringHw.append("Đã thêm bài tập về nhà (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsHomeWork()).append("\". ");
            }
        }
        teHomeWorkRepository.save(homeWorkNew);

        Report reportNew = new Report();
        reportNew.setMeetingId(request.getIdMeeting());
        reportNew.setTeamId(request.getIdTeam());
        reportNew.setDescriptions(request.getDescriptionsReport());
        if (request.getIdReport() != null) {
            Optional<Report> objectReport = teReportRepository.findById(request.getIdReport());
            if (objectReport.isPresent()) {
                reportNew.setId(objectReport.get().getId());
                String report = "";
                if (objectReport.get().getDescriptions() != null) {
                    report = objectReport.get().getDescriptions();
                }
                if (!request.getDescriptionsReport().equals("") && report.equals("")) {
                    stringReport.append("Đã thêm báo cáo (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsReport()).append("\". ");
                } else if (!request.getDescriptionsReport().equals("") && !request.getDescriptionsReport().equals(report)) {
                    stringReport.append("Đã cập nhật báo cáo (").append(nameMeeting).append(" - ").append(nameTeam).append(") từ \"").append(objectReport.get().getDescriptions()).append("\" thành \"").append(request.getDescriptionsReport()).append("\". ");
                } else if (request.getDescriptionsReport().equals("") && !report.equals("")) {
                    stringReport.append("Đã xóa báo cáo (").append(nameMeeting).append(" - ").append(nameTeam).append("). ");
                }
            }
        } else {
            if (!request.getDescriptionsReport().equals("")) {
                stringReport.append("Đã thêm báo báo (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsReport()).append("\". ");
            }
        }
        teReportRepository.save(reportNew);
        TeFindMeetingRequest teFind = new TeFindMeetingRequest();
        teFind.setIdTeam(request.getIdTeam());
        teFind.setIdMeeting(request.getIdMeeting());
        loggerUtil.sendLogStreamClass(stringNote.toString() + stringHw.toString() + stringReport.toString(),
                codeClass, nameSemester);
        Optional<TeHomeWorkAndNoteMeetingResponse> objectFind = teMeetingRepository.searchDetailMeetingTeamByIdMeIdTeam(teFind);
        return objectFind.orElse(null);
    }

    @Override
    public List<TeScheduleMeetingClassResponse> searchScheduleToDayByIdTeacherAndMeetingDate() {
        TeFindScheduleMeetingClassRequest request = new TeFindScheduleMeetingClassRequest();
        request.setIdTeacher(labReportAppSession.getUserId());
        System.err.println("////////////");
        System.err.println(request.getIdTeacher());
        List<TeScheduleMeetingClassResponse> list = teMeetingRepository.searchScheduleToDayByIdTeacherAndMeetingDate(request);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    @Override
    public PageableObject<TeScheduleMeetingClassResponse> searchScheduleNowToByIdTeacher(TeFindScheduleNowToTime request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<TeScheduleMeetingClassResponse> list = teMeetingRepository.searchScheduleNowToTimeByIdTeacher(request, pageable);
        if (list == null) {
            return null;
        }
        return new PageableObject<>(list);
    }

    @Override
    @Transactional
    @Synchronized
    public List<TeScheduleMeetingClassResponse> updateAddressMeeting(TeScheduleUpdateMeetingRequest request) {
        List<TeUpdateMeetingRequest> list = request.getListMeeting();
        if (list.size() == 0) {
            throw new RestApiException(Message.SCHEDULE_TODAY_IS_EMPTY);
        }
        StringBuffer stringBuffer = new StringBuffer();
        TeFindScheduleMeetingClassRequest req = new TeFindScheduleMeetingClassRequest();
        req.setIdTeacher(labReportAppSession.getUserId());
        List<TeScheduleMeetingClassResponse> listMeetingToday = searchScheduleToDayByIdTeacherAndMeetingDate();
        List<Meeting> listNew = new ArrayList<>();
        String nameSemester = semesterHelper.getNameSemesterCurrent();
        list.forEach(item -> {
            listMeetingToday.forEach(db -> {
                if (db.getIdMeeting().equals(item.getIdMeeting())) {
                    if (!item.getMeetingAddress().equals(db.getMeetingAddress()) && item.getMeetingAddress() != null && !item.getMeetingAddress().equals("") && !db.getMeetingAddress().equals("")) {
                        stringBuffer.append("Đã cập nhật link học trực truyến " + db.getMeetingName() + " từ \"" + db.getMeetingAddress() + "\" thành \"" + item.getMeetingAddress().trim() + "\". ");
                        loggerUtil.sendLogStreamClass(stringBuffer.toString(),
                                item.getCodeClass(), nameSemester);
                    } else if (db.getMeetingAddress().equals("") && !item.getMeetingAddress().equals("")) {
                        stringBuffer.append("Đã thêm link học trực truyến " + db.getMeetingName() + " là \"" + item.getMeetingAddress().trim() + "\". ");
                        loggerUtil.sendLogStreamClass(stringBuffer.toString(),
                                item.getCodeClass(), nameSemester);
                    } else if (item.getMeetingAddress().equals("")) {
                        stringBuffer.append("Đã xóa link học trực truyến " + db.getMeetingName() + ". ");
                        loggerUtil.sendLogStreamClass(stringBuffer.toString(),
                                item.getCodeClass(), nameSemester);
                    }
                    Meeting meeting = new Meeting();
                    meeting.setId(db.getIdMeeting());
                    meeting.setName(db.getMeetingName());
                    meeting.setTeacherId(labReportAppSession.getUserId());
                    meeting.setMeetingDate(db.getMeetingDate());
                    meeting.setMeetingPeriod(db.getIdMeetingPeriod());
                    meeting.setDescriptions(db.getDescriptionsMeeting());
                    meeting.setClassId(db.getIdClass());
                    meeting.setStatusMeeting(db.getStatusMeeting().equals("0") ? StatusMeeting.BUOI_HOC : StatusMeeting.BUOI_NGHI);
                    meeting.setTypeMeeting(db.getTypeMeeting() == 0 ? TypeMeeting.ONLINE : TypeMeeting.OFFLINE);
                    meeting.setNotes(db.getNotes());
                    meeting.setAddress(item.getMeetingAddress() == null ? "" : item.getMeetingAddress().trim());
                    listNew.add(meeting);
                }
            });
        });
        teMeetingRepository.saveAll(listNew);
        TeFindScheduleMeetingClassRequest find = new TeFindScheduleMeetingClassRequest();
        find.setIdTeacher(labReportAppSession.getUserId());
        return teMeetingRepository.searchScheduleToDayByIdTeacherAndMeetingDate(find);
    }

    private List<TeMeetingCustomToAttendanceResponse> sortASCListAttendanceObj
            (List<TeMeetingCustomToAttendanceResponse> list) {
        List<TeMeetingCustomToAttendanceResponse> sortedList = list.stream()
                .sorted(Comparator.comparing(TeMeetingCustomToAttendanceResponse::getMeetingDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(TeMeetingCustomToAttendanceResponse::getMeetingPeriod))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        list.clear();
        return sortedList;
    }

    @Override
    public List<TeMeetingCustomToAttendanceResponse> listMeetingAttendanceAllByIdClass(String idClass) {
        List<TeMeetingCustomToAttendanceResponse> listMeeting = teMeetingRepository.findMeetingCustomToAttendanceByIdClass(idClass);
        if (listMeeting.size() == 0) {
            return null;
        }
        List<TeMeetingCustomToAttendanceResponse> listReturn = sortASCListAttendanceObj(listMeeting);
        listMeeting.clear();
        return listReturn;
    }

    @Override
    @Synchronized
    public ByteArrayOutputStream exportExcelMeeting(HttpServletResponse response, String idClass) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Class objClass = teClassRepository.findById(idClass).get();
            Sheet sheet = configTitle(workbook, objClass.getCode());
            workbook.write(response.getOutputStream());
            workbook.close();
            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Sheet configTitle(Workbook workbook, String name) {
        Sheet sheet = workbook.createSheet("Danh sách");
        Row titleRow = sheet.createRow(0);
        Cell cellTitle = titleRow.createCell(0);
        cellTitle.setCellValue("DANH SÁCH BUỔI HỌC YÊU CẦU THÊM LỚP " + name);
        cellTitle.setCellStyle(chooseCellStyle("title", workbook));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));

        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 8000);
        sheet.setColumnWidth(3, 7000);
        Row headerRow = sheet.createRow(2);

        Cell cell2 = headerRow.createCell(0);
        cell2.setCellValue("Ngày học");
        cell2.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell3 = headerRow.createCell(1);
        cell3.setCellValue("Ca học");
        cell3.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell4 = headerRow.createCell(2);
        cell4.setCellValue("Tài khoản giảng viên");
        cell4.setCellStyle(chooseCellStyle("titleTable", workbook));
        Cell cell5 = headerRow.createCell(3);
        cell5.setCellValue("Hình thức");
        cell5.setCellStyle(chooseCellStyle("titleTable", workbook));

        Cell cell8 = titleRow.createCell(5);
        cell8.setCellValue("Lưu ý:");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 8));
        cell8.setCellStyle(chooseCellStyle("note", workbook));
        Row dataRow2 = sheet.createRow(1);
        Cell leaderCell = dataRow2.createCell(5);
        leaderCell.setCellValue("Ngày dạy phải có định dạng \"dd/MM/yyyy\"");
        leaderCell.setCellStyle(chooseCellStyle("dataCenterTable", workbook));
        leaderCell.setCellStyle(chooseCellStyle("note", workbook));

        Cell leaderCell1 = headerRow.createCell(5);
        leaderCell1.setCellValue("Hình thức có 2 loại \"Online\" và \"Offline\" ");
        leaderCell1.setCellStyle(chooseCellStyle("dataCenterTable", workbook));
        leaderCell1.setCellStyle(chooseCellStyle("note", workbook));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 8));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 8));
        for (int i = 5; i <= 8; i++) {
            sheet.autoSizeColumn(i, true);
        }
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
        if (type.equals("note")) {
            fontStyle.setColor(Font.COLOR_RED);
            fontStyle.setFontHeightInPoints((short) 11);
            cellStyle.setFont(fontStyle);
        }
        return cellStyle;
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
    @Transactional
    @Synchronized
    public TeExcelResponseMessage importExcelMeeting(MultipartFile file, String idClass) {
        TeExcelResponseMessage teExcelResponseMessage = new TeExcelResponseMessage();
        try {
            Optional<Class> classFind = teClassRepository.findById(idClass);
            if (classFind.isEmpty()) {
                throw new RestApiException(Message.CLASS_NOT_EXISTS);
            }
            if (classFind.get().getStatusClass().equals(StatusClass.LOCK)) {
                throw new RestApiException("Lớp học đã khóa, không thể gửi yêu cầu !");
            }
            boolean isExcelFile = isExcelFile(file);
            if (!isExcelFile) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("File excel sai định dạng, vui lòng export mẫu để sử dụng !");
                return teExcelResponseMessage;
            }
            List<TeExcelImportMeetingRequest> listInput = teExcelImportService.importDataMeetingRequest(file);
            List<MeetingRequest> listMeetingSend = new ArrayList<>();
            if (listInput.size() == 0) {
                teExcelResponseMessage.setStatus(false);
                teExcelResponseMessage.setMessage("File excel trống, vui lòng thêm buổi học cần yêu cầu để sử dụng import !");
                return teExcelResponseMessage;
            }
            List<MeetingPeriod> listMeetingPeriod = teMeetingPeriodRepository.findAll();
            List<MeetingRequest> listMeetingRequestFind = teMeetingRequestRepository.getAllByClassIdAndStatusReject(idClass);
            ConcurrentHashMap<String, MeetingPeriod> mapMeetingPeriod = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, SimpleResponse> mapGiangVien = new ConcurrentHashMap<>();
            addDataMeetingPeriod(mapMeetingPeriod, listMeetingPeriod);
            addDataInMapGiangVien(mapGiangVien);
            teExcelResponseMessage.setStatus(true);
            AtomicBoolean checkValidate = new AtomicBoolean(true);
            teExcelResponseMessage.setMessage("");
            StringBuilder message = new StringBuilder();
            message.append("Đã gửi yêu cầu tạo buổi học: ");
            listInput.parallelStream().forEach(meeting -> {
                String regexType = "^(Online|Offline)$";
                String regexDate = "^(?:(?:(?:0[1-9]|1\\d|2[0-8])/(0[1-9]|1[0-2])|(?:29|30)/(0[13-9]|1[0-2])|31/(0[13578]|1[02]))/(\\d{4})|(?:29/02/(\\d{2}(?:00|[2468][048]|[13579][26]))))$";
                if (meeting.getMeetingDate().isBlank()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage("Ngày học không được để trống !");
                    checkValidate.set(false);
                    return;
                } else {
                    if (!meeting.getMeetingDate().matches(regexDate)) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(" Ngày học sai định dạng !");
                        checkValidate.set(false);
                        return;
                    }
                }
                if (meeting.getNameMeetingPeriod().isBlank()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Ca học không được để trống !");
                    checkValidate.set(false);
                    return;
                } else {
                    MeetingPeriod meetingPeriodCheck = mapMeetingPeriod.get(meeting.getNameMeetingPeriod().toLowerCase());
                    if (meetingPeriodCheck == null) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(" Ca học không tồn tại !");
                        checkValidate.set(false);
                        return;
                    }
                }
                if (meeting.getTypeMeeting().isBlank()) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Hình thức học không được để trống !");
                    checkValidate.set(false);
                    return;
                } else {
                    if (!meeting.getTypeMeeting().matches(regexType)) {
                        teExcelResponseMessage.setStatus(false);
                        teExcelResponseMessage.setMessage(" Hình thức học phải là \"Online\" hoặc \"Offline\" !");
                        checkValidate.set(false);
                        return;
                    }
                }
                MeetingPeriod meetingPeriod = mapMeetingPeriod.get(meeting.getNameMeetingPeriod().toLowerCase());
                SimpleResponse teacher = mapGiangVien.get(meeting.getUserNameTeacher().toLowerCase());
                if (teacher == null) {
                    teExcelResponseMessage.setStatus(false);
                    teExcelResponseMessage.setMessage(" Giảng viên không tồn tại !");
                    checkValidate.set(false);
                    return;
                }
                message.append(" Ngày: ").append(meeting.getMeetingDate()).append(", ").append(meetingPeriod.getName())
                        .append(", ").append(" giảng viên: ").append(teacher.getName()).append(" - ").append(teacher.getUserName())
                        .append(", hình thức: ").append(meeting.getTypeMeeting()).append(",");
                MeetingRequest meetingRequest = new MeetingRequest();
                meetingRequest.setClassId(idClass);
                meetingRequest.setMeetingDate(DateConverter.convertDateToLongOneHourOneMinutes(meeting.getMeetingDate()));
                meetingRequest.setName(RandomString.random());
                meetingRequest.setMeetingPeriod(meetingPeriod.getId());
                meetingRequest.setStatusMeetingRequest(StatusMeetingRequest.CHO_PHE_DUYET);
                meetingRequest.setTypeMeeting(meeting.getTypeMeeting().equals("Online") ? TypeMeeting.ONLINE : TypeMeeting.OFFLINE);
                meetingRequest.setTeacherId(teacher.getId());
                Optional<MeetingRequest> findMeeting = getMeetingRequestByDateTimeAndCa(listMeetingRequestFind, meetingRequest.getMeetingDate(), meetingRequest.getMeetingPeriod());
                findMeeting.ifPresent(request -> {
                    meetingRequest.setId(request.getId());
                });
                listMeetingSend.add(meetingRequest);
            });
            if (message.length() > 0 && message.charAt(message.length() - 1) == ',') {
                message.setCharAt(message.length() - 1, '.');
            }
            if (teExcelResponseMessage.getStatus()) {
                teMeetingRequestRepository.saveAll(listMeetingSend);
                teMeetingRequestRepository.updateNameMeetingRequest(idClass);
                teExcelResponseMessage.setMessage("Gửi yêu cầu thành công !");
                if (!message.isEmpty()) {
                    String codeClass = loggerUtil.getCodeClassByIdClass(idClass);
                    String nameSemester = loggerUtil.getNameSemesterByIdClass(idClass);
                    loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);
                }
            } else {
                teExcelResponseMessage.setMessage("Gửi yêu cầu thất bại. " + teExcelResponseMessage.getMessage());
            }
            listInput.clear();
            mapMeetingPeriod.clear();
            mapGiangVien.clear();
            file.getInputStream().close();
            return teExcelResponseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            teExcelResponseMessage.setStatus(false);
            teExcelResponseMessage.setMessage("Lỗi hệ thống, vui lòng F5 lại trang !");
            return teExcelResponseMessage;
        }
    }

    public Optional<MeetingRequest> getMeetingRequestByDateTimeAndCa(
            List<MeetingRequest> listMeetingRequestFind,
            Long meetingDate,
            String ca) {
        return listMeetingRequestFind.stream()
                .filter(meetingRequest ->
                        DateConverter.convertDateToStringNotTime(meetingRequest.getMeetingDate())
                                .equals(DateConverter.convertDateToStringNotTime(meetingDate))
                                && meetingRequest.getMeetingPeriod().equals(ca))
                .findFirst();
    }

    private void addDataMeetingPeriod(ConcurrentHashMap<String, MeetingPeriod> mapMeetingPeriod, List<MeetingPeriod> listMeetingPeriod) {
        getAllPutMeetingPeriod(mapMeetingPeriod, listMeetingPeriod);
    }

    private void getAllPutMeetingPeriod(ConcurrentHashMap<String, MeetingPeriod> mapMeetingPeriod, List<MeetingPeriod> listMeetingPeriod) {
        for (MeetingPeriod meeting : listMeetingPeriod) {
            mapMeetingPeriod.put(meeting.getName().toLowerCase(), meeting);
        }
        listMeetingPeriod.clear();
    }

    public void addDataInMapGiangVien(ConcurrentHashMap<String, SimpleResponse> mapAll) {
        List<SimpleResponse> giangVienHuongDanList = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_TEACHER);
        getALlPutMapGiangVien(mapAll, giangVienHuongDanList);
    }

    public void getALlPutMapGiangVien(ConcurrentHashMap<String, SimpleResponse> mapSimple, List<SimpleResponse> listGiangVien) {
        for (SimpleResponse simple : listGiangVien) {
            mapSimple.put(simple.getUserName().toLowerCase(), simple);
        }
    }

}
