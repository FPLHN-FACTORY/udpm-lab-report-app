package com.labreportapp.labreport.util;

import com.labreportapp.labreport.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @author thangncph26123
 */
@Component
public class SemesterHelper {

    @Autowired
    @Qualifier(SemesterRepository.NAME)
    private SemesterRepository semesterRepository;

    public String getSemesterCurrent() {
        return semesterRepository.getSemesterCurrent(Calendar.getInstance().getTimeInMillis());
    }
}
