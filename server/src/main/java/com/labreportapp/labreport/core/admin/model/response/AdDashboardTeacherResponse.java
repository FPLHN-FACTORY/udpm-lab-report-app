package com.labreportapp.labreport.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author todo thangncph26123
 */
@Getter
@Setter
@ToString
public class AdDashboardTeacherResponse {

    private String id;

    private String name;

    private String userName;

    private String email;

    private String picture;

    private List<AdDashboardClassResponse> listClass;
}
