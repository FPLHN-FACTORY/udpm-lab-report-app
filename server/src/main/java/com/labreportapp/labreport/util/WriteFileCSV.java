package com.labreportapp.labreport.util;

import com.labreportapp.labreport.infrastructure.constant.ConfigurationsConstant;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author todo thangncph26123
 */
@Service
public class WriteFileCSV {

    @Autowired
    private LabReportAppSession session;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public LoggerObject writerLoggerObjectIsNotData(LoggerObject loggerObject) {
        Field[] fields = loggerObject.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(loggerObject) == null) {
                    field.set(loggerObject, ConfigurationsConstant.KHONG_CO_DU_LIEU);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return loggerObject;
    }

    public String switchAuthor(String api) {
        if (api.startsWith("/admin")) {
            return "Quản trị viên";
        }
        if (api.startsWith("/teacher")) {
            return "Giảng viên";
        }
        if (api.startsWith("/student")) {
            return "Sinh viên";
        }
        return "";
    }

    public LoggerObject createLoggerObject(String content, String pathFile) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = simpleDateFormat.format(currentDate);

        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        loggerObject.setContent(content);
        loggerObject.setIp(httpServletRequest.getRemoteAddr());
        loggerObject.setMail(session.getEmail() == null ? "" : session.getEmail());
        loggerObject.setCreateDate(formattedDate);
        loggerObject.setMethod(httpServletRequest.getMethod());
        loggerObject.setAuthor(switchAuthor(httpServletRequest.getRequestURI()));

        writerLoggerObjectIsNotData(loggerObject);
        return loggerObject;
    }

    public String getPropertiesRead(String constant) {
        PropertiesReader po = new PropertiesReader();
        return po.getPropertyConfig(constant);
    }

    public String covertFileName(String fileOld) {
        String chuoiKhongDau = removeDiacritics(fileOld);
        String chuoiChuThuong = chuoiKhongDau.toLowerCase();
        String output = chuoiChuThuong.replaceAll("[^a-zA-Z0-9]", "_");
        String fileNew = output.replace(" ", "_");

        return fileNew;
    }

    public static String removeDiacritics(String text) {
        text = text.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
                .replaceAll("[èéẹẻẽêềếệểễ]", "e")
                .replaceAll("[ìíịỉĩ]", "i")
                .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
                .replaceAll("[ùúụủũưừứựửữ]", "u")
                .replaceAll("[ỳýỵỷỹ]", "y")
                .replaceAll("[đ]", "d")
                .replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A")
                .replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E")
                .replaceAll("[ÌÍỊỈĨ]", "I")
                .replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O")
                .replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U")
                .replaceAll("[ỲÝỴỶỸ]", "Y")
                .replaceAll("[Đ]", "D");
        return text;
    }

    public String getPathFileByHocKy(String tenHocKy) {
        String path = covertFileName(tenHocKy) + "/";
        return path;
    }

    public String getPathFileActiveByApi(String tenHocKy) {
        String api = httpServletRequest.getRequestURI();
        String folder = "";
        String pathFile = "";
        String pathTemplate = getPropertiesRead(ConfigurationsConstant.PATH_FILE_TEMPLATE_LAB_REPORT_APP);
        if (api.startsWith("/admin")) {
            folder = getPathFileByHocKy(tenHocKy) + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_ADMIN);
            if (api.contains("/semester")) {
                pathFile = pathTemplate + getPropertiesRead(ConfigurationsConstant.NAME_FILE_QUAN_LY_HOC_KY);
            }
        }

        System.out.println("========= pathFile: " + pathFile);
        return pathFile;
    }
}
