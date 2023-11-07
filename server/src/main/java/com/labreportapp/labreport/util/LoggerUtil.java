package com.labreportapp.labreport.util;

import com.labreportapp.labreport.infrastructure.logger.LogService;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author todo thangncph26123
 */
@Component
public class LoggerUtil {

    @Autowired
    private LogService logService;

    @Autowired
    private WriteFileCSV writeFileCSV;

    public void sendLog(String content, String tenHocKy) {
        try {
            String pathFile = writeFileCSV.getPathFileActiveByApi(tenHocKy);
            LoggerObject loggerObject = writeFileCSV.createLoggerObject(content, pathFile);
            logService.sendLogMessage(loggerObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPathFile(String tenHocKy) {
        try {
            return writeFileCSV.getPathFileActiveByApi(tenHocKy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
