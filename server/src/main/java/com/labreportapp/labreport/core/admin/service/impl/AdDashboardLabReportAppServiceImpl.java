package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdDashboardLabReportAppRequest;
import com.labreportapp.labreport.core.admin.model.response.AdDashboardClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdDashboardLabReportAppResponse;
import com.labreportapp.labreport.core.admin.model.response.AdDashboardTeacherResponse;
import com.labreportapp.labreport.core.admin.repository.AdDashboardLabReportAppRepository;
import com.labreportapp.labreport.core.admin.service.AdDashboardLabReportAppService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.labreport.repository.ClassConfigurationRepository;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.SemesterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author todo thangncph26123
 */
@Service
@Validated
public class AdDashboardLabReportAppServiceImpl implements AdDashboardLabReportAppService {

    @Autowired
    private AdDashboardLabReportAppRepository adDashboardLabReportAppRepository;

    @Autowired
    @Qualifier(ClassConfigurationRepository.NAME)
    private ClassConfigurationRepository classConfigurationRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private SemesterHelper semesterHelper;

    @Override
    public AdDashboardLabReportAppResponse dashboard(final AdDashboardLabReportAppRequest request) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (request.getIdSemester() == null) {
            if (idSemesterCurrent != null) {
                request.setIdSemester(idSemesterCurrent);
                request.setIdActivity("");
            } else {
                request.setIdSemester("");
            }
        } else if (request.getIdSemester().equalsIgnoreCase("")) {
            if (idSemesterCurrent != null) {
                request.setIdSemester(idSemesterCurrent);
                request.setIdActivity("");
            } else {
                request.setIdSemester("");
            }
        }
        List<ClassConfiguration> classConfigurationList = classConfigurationRepository.findAll();
        ClassConfiguration classConfiguration = null;
        if (classConfigurationList != null && classConfigurationList.size() > 0) {
            classConfiguration = classConfigurationRepository.findAll().get(0);
        }
        AdDashboardLabReportAppResponse response = new AdDashboardLabReportAppResponse();

        Integer tongSoLopHoc = adDashboardLabReportAppRepository.getTongSoLopHoc(request);
        response.setTongSoLopHoc(tongSoLopHoc);

        Integer tongSoGiangVien = adDashboardLabReportAppRepository.getTongSoGiangVien(request);
        response.setTongSoGiangVien(tongSoGiangVien);

        Integer tongSoSinhVien = adDashboardLabReportAppRepository.getTongSoSinhVien(request);
        response.setTongSoSinhVien(tongSoSinhVien);

        Integer tongSoLopChuaCoGiangVien = adDashboardLabReportAppRepository.getTongSoLopChuaCoGiangVien(request);
        response.setTongSoLopChuaCoGiangVien(tongSoLopChuaCoGiangVien);

        Integer tongLopHocDuDieuKien = 0;
        if (classConfiguration != null) {
            tongLopHocDuDieuKien = adDashboardLabReportAppRepository.getTongSoLopDuDieuKien(request, classConfiguration.getClassSizeMin());
            response.setTongLopHocDuDieuKien(tongLopHocDuDieuKien);
        } else {
            response.setTongLopHocDuDieuKien(tongLopHocDuDieuKien);
        }

        Integer tongLopHocChuaDuDieuKien = tongSoLopHoc - tongLopHocDuDieuKien;
        response.setTongLopHocChuaDuDieuKien(tongLopHocChuaDuDieuKien);

        Integer tongLopGiangVienChinhSua = adDashboardLabReportAppRepository.getTongLopGiangVienChinhSua(request);
        response.setTongLopGiangVienChinhSua(tongLopGiangVienChinhSua);

        Integer tongSoLevel = adDashboardLabReportAppRepository.getTongSoLevel();
        response.setTongSoLevel(tongSoLevel);

        List<String> teacherIds = adDashboardLabReportAppRepository.getListGiangVien(request);
        List<SimpleResponse> listSimple = callApiIdentity.handleCallApiGetListUserByListId(teacherIds);
        List<AdDashboardTeacherResponse> listTeacher = new ArrayList<>();
        listSimple.forEach(simple -> {
            AdDashboardTeacherResponse adDashboardTeacherResponse = new AdDashboardTeacherResponse();
            adDashboardTeacherResponse.setId(simple.getId());
            adDashboardTeacherResponse.setEmail(simple.getEmail());
            adDashboardTeacherResponse.setName(simple.getName());
            adDashboardTeacherResponse.setUserName(simple.getUserName());
            adDashboardTeacherResponse.setPicture(simple.getPicture());
            List<AdDashboardClassResponse> listClass = adDashboardLabReportAppRepository.getListClass(simple.getId(),
                    request.getIdSemester(), request.getIdActivity(), new Date().getTime());
            adDashboardTeacherResponse.setListClass(listClass);
            listTeacher.add(adDashboardTeacherResponse);
        });
        response.setListTeacher(listTeacher);
        return response;
    }
}
