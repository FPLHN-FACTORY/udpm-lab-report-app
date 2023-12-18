package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdCreateClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdRandomClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateClassRequest;
import com.labreportapp.labreport.core.admin.model.response.AdActivityClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdListClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdSemesterAcResponse;
import com.labreportapp.labreport.core.admin.service.AdClassService;
import com.labreportapp.labreport.core.common.base.ImportExcelResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassSentStudentRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeSentStudentClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassSentStudentRespone;
import com.labreportapp.labreport.core.teacher.model.response.TePointStudentInforRespone;
import com.labreportapp.labreport.core.teacher.service.TeClassService;
import com.labreportapp.labreport.core.teacher.service.TePointSevice;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.repository.SemesterRepository;
import com.labreportapp.labreport.util.CallApiConsumer;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.labreport.util.SemesterHelper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/api/admin/class-managerment")
public class AdClassController {

    @Autowired
    private TeClassService teClassService;

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private AdClassService service;

    @Autowired
    private CallApiConsumer callApiConsumer;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    @Qualifier(SemesterRepository.NAME)
    private SemesterRepository semesterRepository;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private TePointSevice tePointSevice;

    @GetMapping("")
    public ResponseObject getAll() {
        return new ResponseObject(service.getAllClass());
    }

    @GetMapping("/get-all/id-semester")
    public ResponseObject getAllClassByIdSemester(final AdFindClassRequest adFindClass) {
        return new ResponseObject(service.getAllClassBySemester(adFindClass));
    }

    @GetMapping("/level")
    public ResponseObject getAllLevel() {
        return new ResponseObject(service.getAllLevel());
    }

    @GetMapping("/find-by-condition")
    public ResponseObject findAllByCondition(@RequestParam("codeClass") String code,
                                             @RequestParam("classPeriod") Long classPeriod,
                                             @RequestParam("idPerson") String idTeacher
    ) {
        return new ResponseObject(service.findClassByCondition(code, classPeriod, idTeacher));
    }

    @GetMapping("/semester/getAll")
    public ResponseObject getAllSemester() {
        List<AdSemesterAcResponse> listSemester = service.getAllSemester();
        return new ResponseObject(listSemester);
    }

    @GetMapping("/id-semester")
    public ResponseObject getListActivitySemester(final AdFindClassRequest teFindClass) {
        List<AdActivityClassResponse> list = service.getAllByIdSemester(teFindClass);
        return new ResponseObject(list);
    }

    @PostMapping("/add")
    public ResponseObject createClass(@RequestBody AdCreateClassRequest request) {
        return new ResponseObject(service.createClass(request));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateClass(@RequestBody AdUpdateClassRequest request, @PathVariable String id) {
        return new ResponseObject(service.updateClass(request, id));
    }

    @GetMapping("/getAllSearch")
    public ResponseObject searchTeClass(final AdFindClassRequest adFindClass) {
        PageableObject<AdListClassCustomResponse> pageList = service.searchClass(adFindClass);
        return new ResponseObject(pageList);
    }

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportExcel(HttpServletResponse response, final AdFindClassRequest request) {
        try {
            ByteArrayOutputStream file = service.exportExcelClass(response, request);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "sample.xlsx");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(file.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/information-class/{id}")
    public ResponseObject adDetailClassById(@PathVariable("id") String id) {
        return new ResponseObject(service.adFindClassById(id));
    }

    @PostMapping("/random-class")
    public ResponseObject randomClass(@RequestBody AdRandomClassRequest request) {
        return new ResponseObject(service.randomClass(request));
    }

    @PostMapping("/import-excel/{idSemester}")
    public ResponseObject importListExcel(@RequestParam("multipartFile") MultipartFile multipartFile, @PathVariable("idSemester") String idSemester) throws IOException {
        ImportExcelResponse importExcelResponse = service.importExcelClass(multipartFile, idSemester);
        return new ResponseObject(importExcelResponse);
    }

    @PutMapping("/sent-st")
    public ResponseObject sentStudentClassesToClass(@RequestBody TeSentStudentClassRequest request) {
        return new ResponseObject(teStudentClassesService.updateSentStudentClassesToClass(request));
    }

    @PutMapping("/kick-st")
    public ResponseObject kichStudentClasses(@RequestBody TeSentStudentClassRequest request) {
        return new ResponseObject(teStudentClassesService.updateKickStudentClasses(request));
    }

    @GetMapping("/class-sent")
    public ResponseObject classSentStudent(final TeFindClassSentStudentRequest request) {
        PageableObject<TeClassSentStudentRespone> pageList = teClassService.findClassBySentStudent(request);
        return new ResponseObject(pageList);
    }

    @GetMapping("/filter-class")
    public ResponseObject listClassIdActivityIdSemester(final AdFindClassRequest request) {
        return new ResponseObject(service.listClass(request));
    }

    @GetMapping("/download-log-all")
    public ResponseEntity<Resource> downloadCsvAll(@RequestParam(name = "idClass", defaultValue = "") String idClass) {
        String nameSemester = loggerUtil.getNameSemesterByIdClass(idClass);
        String pathFile = loggerUtil.getPathFileSendLogStreamClass(nameSemester);
        return callApiConsumer.handleCallApiDowloadFileLog(pathFile);
    }

    @GetMapping("/history-all")
    public ResponseEntity<?> showHistoryAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "50") Integer size,
                                            @RequestParam(name = "idClass", defaultValue = "") String idClass
    ) {
        String nameSemester = loggerUtil.getNameSemesterByIdClass(idClass);
        String pathFile = loggerUtil.getPathFileSendLogStreamClass(nameSemester);
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setCodeClass(loggerUtil.getCodeClassByIdClass(idClass));
        loggerObject.setPathFile(pathFile);
        return new ResponseEntity<>(callApiConsumer.handleCallApiReadFileLog(loggerObject, page, size), HttpStatus.OK);
    }

    @GetMapping("/semester-current")
    public ResponseObject getSemesterCurrent() {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        return new ResponseObject(idSemesterCurrent);
    }

    @GetMapping("/semester-current-name")
    public ResponseObject getSemesterCurrentName() {
        String nameSemesterCurrent = semesterHelper.getNameSemesterCurrent();
        return new ResponseObject(nameSemesterCurrent);
    }

    @GetMapping("/download-log-luong")
    public ResponseEntity<Resource> downloadCsvLuong(@RequestParam(name = "idSemester", defaultValue = "") String idSemester) {
        String nameSemester = semesterRepository.findById(idSemester).get().getName();
        String pathFile = loggerUtil.getPathFileSendLogStreamClass(nameSemester);
        return callApiConsumer.handleCallApiDowloadFileLog(pathFile);
    }

    @GetMapping("/history-log-luong")
    public ResponseEntity<?> showHistoryLogLuong(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(name = "size", defaultValue = "50") Integer size,
                                                 @RequestParam(name = "idSemester", defaultValue = "") String idSemester
    ) {
        String nameSemester = semesterRepository.findById(idSemester).get().getName();
        String pathFile = loggerUtil.getPathFileSendLogStreamClass(nameSemester);
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        return new ResponseEntity<>(callApiConsumer.handleCallApiReadFileLog(loggerObject, page, size), HttpStatus.OK);
    }

    @GetMapping("/class/get-point/{idClass}")
    public ResponseObject getPointByIdClass(@PathVariable("idClass") String idClass) {
        List<TePointStudentInforRespone> list = tePointSevice.getPointStudentByIdClass(idClass);
        return new ResponseObject(list);
    }

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv(@RequestParam(name = "idSemester", defaultValue = "") String idSemester) {
        String nameSemester = semesterRepository.findById(idSemester).get().getName();
        String pathFile = loggerUtil.getPathFileSendLogScreen(nameSemester);
        return callApiConsumer.handleCallApiDowloadFileLog(pathFile);
    }

    @GetMapping("/history")
    public ResponseEntity<?> showHistory(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "50") Integer size,
                                         @RequestParam(name = "idSemester", defaultValue = "") String idSemester
    ) {
        String nameSemester = semesterRepository.findById(idSemester).get().getName();
        String pathFile = loggerUtil.getPathFileSendLogScreen(nameSemester);
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        return new ResponseEntity<>(callApiConsumer.handleCallApiReadFileLog(loggerObject, page, size), HttpStatus.OK);
    }

    @GetMapping("/get-class/{id}")
    public ResponseObject detailClass(@PathVariable("id") String id) {
        return new ResponseObject(service.findClassById(id));
    }

    @PostMapping("/send-mail-to-student")
    public ResponseObject sendMailToStudent() {
        return new ResponseObject(service.sendMailToStudent());
    }
}
