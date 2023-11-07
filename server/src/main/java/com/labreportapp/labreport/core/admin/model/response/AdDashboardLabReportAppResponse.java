package com.labreportapp.labreport.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author todo thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdDashboardLabReportAppResponse {

    private Integer tongSoLopHoc;

    private Integer tongSoGiangVien;

    private Integer tongSoSinhVien;

    private Integer tongSoLopChuaCoGiangVien;

    private Integer tongLopHocDuDieuKien;

    private Integer tongLopHocChuaDuDieuKien;

    private Integer tongLopGiangVienChinhSua;

    private Integer tongSoLevel;

    private List<AdDashboardTeacherResponse> listTeacher;
}
