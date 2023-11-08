package com.labreportapp.labreport.util;

import com.labreportapp.labreport.infrastructure.logger.LogService;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author todo thangncph26123
 */
@Component
public class LoggerUtil {

    @Autowired
    private LogService logService;

    @Autowired
    private WriteFileCSV writeFileCSV;

    @Autowired
    @Qualifier(ClassRepository.NAME)
    private ClassRepository classRepository;

    public void sendLogScreen(String content, String tenHocKy) {
        try {
            if (Objects.nonNull(content)) {
                String pathFile = writeFileCSV.getPathFileSendLogScreen(tenHocKy);
                LoggerObject loggerObject = writeFileCSV.createLoggerObject(content, null, pathFile);
                logService.sendLogMessage(loggerObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendLogStreamClass(String content, String codeClass, String tenHocKy) {
        try {
            if (Objects.nonNull(content)) {
                String pathFile = writeFileCSV.getPathFileSendLogStreamClass(tenHocKy);
                LoggerObject loggerObject = writeFileCSV.createLoggerObject(content, codeClass, pathFile);
                logService.sendLogMessage(loggerObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPathFileSendLogScreen(String tenHocKy) {
        try {
            return writeFileCSV.getPathFileSendLogScreen(tenHocKy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPathFileSendLogStreamClass(String tenHocKy) {
        try {
            return writeFileCSV.getPathFileSendLogStreamClass(tenHocKy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNameSemesterByIdClass(String idClass) {
        return classRepository.getNameSemesterByIdClass(idClass);
    }
}
