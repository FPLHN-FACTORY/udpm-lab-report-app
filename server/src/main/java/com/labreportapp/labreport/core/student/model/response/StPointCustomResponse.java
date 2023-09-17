package com.labreportapp.labreport.core.student.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StPointCustomResponse {

    private Integer stt;

    private String tenDauDiem;

    private Integer trongSo;

    private Double diem;

    private String ghiChu;
}
