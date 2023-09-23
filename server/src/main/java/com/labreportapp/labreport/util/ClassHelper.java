package com.labreportapp.labreport.util;

import com.labreportapp.labreport.repository.ActivityRepository;
import com.labreportapp.labreport.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author thangncph26123
 */
@Component
public class ClassHelper {

    @Autowired
    @Qualifier(ClassRepository.NAME)
    private ClassRepository classRepository;

    @Autowired
    @Qualifier(ActivityRepository.NAME)
    private ActivityRepository activityRepository;

    public String genMaLopTheoHoatDong(String idActivity) {
        String maLopMax = classRepository.getMaNhomMaxByIdActivity(idActivity);
        String text = activityRepository.getCodeActivity(idActivity);
        Integer count = 0;
        try {
            if (maLopMax != null) {
                String[] splitStr = maLopMax.split("_");
                count = Integer.valueOf(splitStr[splitStr.length - 1]) + 1;
            } else {
                count = 1;
            }
        } catch (Exception e) {
            return null;
        }
        String maLopMoi = text + "_" + count;
        return maLopMoi;
    }
}
