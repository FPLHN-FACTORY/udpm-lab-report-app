package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.service.StMyClassService;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.CallApiConsumer;
import com.labreportapp.labreport.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/student/my-class")
public class StMyClassController {

    @Autowired
    private StMyClassService stMyClassService;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private CallApiConsumer callApiConsumer;

    @GetMapping
    public ResponseObject getAllClass(final StFindClassRequest request) {
        request.setStudentId(labReportAppSession.getUserId());
        return new ResponseObject(stMyClassService.getAllClass(request));
    }

    @GetMapping("/get-all-student-classes")
    public ResponseObject getAllStudentClasses(@RequestParam("idClass") String idClass) {
        return new ResponseObject(stMyClassService.getAllStudentClasses(idClass));
    }

    @DeleteMapping("/leave")
    public ResponseObject leaveClass(final StClassRequest req) {
        stMyClassService.leaveClass(req);
        return new ResponseObject(null);
    }

    @GetMapping("/level")
    public ResponseObject getAllSimpleEntityProj() {
        return new ResponseObject(stMyClassService.getAllSimpleEntityProj());
    }

    @GetMapping("/history")
    public ResponseEntity<?> showHistory(@RequestParam(name = "page", defaultValue = "0") Integer page,
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
}
