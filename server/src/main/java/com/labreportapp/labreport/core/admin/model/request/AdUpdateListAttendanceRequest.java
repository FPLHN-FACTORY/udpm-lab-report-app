package com.labreportapp.labreport.core.admin.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdUpdateListAttendanceRequest {

    private List<AdUpdateAttendanceRequest> listAttendance;
}
