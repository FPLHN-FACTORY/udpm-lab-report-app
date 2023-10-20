package com.labreportapp.labreport.core.teacher.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeCountMeetingAndSheetRespone {

    private Sheet sheet;

    private Integer countMeeting;
}
