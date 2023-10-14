package com.labreportapp.labreport.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdExportExcelClassCustom {

    Integer stt;

    String code;

    Long startTime;

    String classPeriod;

    Integer startHour;

    Integer startMinute;

    Integer endHour;

    Integer endMinute;

    Integer classSize;

    String teacherId;

    String userNameTeacher;

    String nameLevel;

    String nameActivity;
}
