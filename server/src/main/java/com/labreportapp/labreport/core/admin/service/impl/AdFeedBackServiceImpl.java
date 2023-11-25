package com.labreportapp.labreport.core.admin.service.impl;


import com.labreportapp.labreport.core.admin.model.response.AdFeedBackCustom;
import com.labreportapp.labreport.core.admin.model.response.AdFeedBackResponse;
import com.labreportapp.labreport.core.admin.model.response.AdGetFeedbackResponse;
import com.labreportapp.labreport.core.admin.model.response.AdObjFeedbackResponse;
import com.labreportapp.labreport.core.admin.repository.AdClassRepository;
import com.labreportapp.labreport.core.admin.repository.AdFeedBackRepository;
import com.labreportapp.labreport.core.admin.service.AdFeedBackService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.FeedBack;
import com.labreportapp.labreport.infrastructure.constant.StatusShowFeedback;
import com.labreportapp.labreport.util.CallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author quynhncph26201
 */
@Service
public class AdFeedBackServiceImpl implements AdFeedBackService {

    @Autowired
    private AdFeedBackRepository adFeedBackRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private AdClassRepository adClassRepository;

    @Override
    public List<AdFeedBackCustom> searchFeedBack(String idClass) {
        List<AdFeedBackResponse> list = adFeedBackRepository.getAllFeedBack(idClass);
        List<String> idListStudent = list.stream()
                .map(AdFeedBackResponse::getIdStudent)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = callApiIdentity.handleCallApiGetListUserByListId(idListStudent);
        List<AdFeedBackCustom> listFeedBackCustom = new ArrayList<>();
        list.forEach(feedback -> {
            AdFeedBackCustom adFeedBackCustom = new AdFeedBackCustom();
            adFeedBackCustom.setRateQuestion1(feedback.getRateQuestion1());
            adFeedBackCustom.setRateQuestion2(feedback.getRateQuestion2());
            adFeedBackCustom.setRateQuestion3(feedback.getRateQuestion3());
            adFeedBackCustom.setRateQuestion4(feedback.getRateQuestion4());
            adFeedBackCustom.setRateQuestion5(feedback.getRateQuestion5());
            adFeedBackCustom.setAverageRate(feedback.getAverageRate());
            adFeedBackCustom.setDescription(feedback.getDescription());
            adFeedBackCustom.setCreatedDate(feedback.getCreatedDate());
            adFeedBackCustom.setIdClass(feedback.getIdClass());
            adFeedBackCustom.setStt(feedback.getStt());
            adFeedBackCustom.setIdStudent(feedback.getIdStudent());
            if (feedback.getIdStudent() != null) {
                listResponse.forEach(response -> {
                    if (feedback.getIdStudent().equals(response.getId())) {
                        adFeedBackCustom.setEmailStudent(response.getEmail());
                        adFeedBackCustom.setNameStudent(response.getName());
                    }
                });
            }
            listFeedBackCustom.add(adFeedBackCustom);
        });
        return listFeedBackCustom;
    }

    @Override
    public AdObjFeedbackResponse getAllFeedbackByIdClass(String idClass) {
        AdObjFeedbackResponse objReturn = new AdObjFeedbackResponse();
        List<FeedBack> listFeedback = adFeedBackRepository.getAllFeedBackByIdClass(idClass);
        String codeClass = adClassRepository.findCodeByIdClass(idClass);
        objReturn.setCodeClass(codeClass != null ? codeClass : "");
        List<AdGetFeedbackResponse> listReturn = new ArrayList<>();
        if (listFeedback.size() == 0) {
            objReturn.setListFeedback(listReturn);
            return objReturn;
        }
        List<String> idStudentList = listFeedback.stream()
                .map(FeedBack::getStudentId)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = callApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        AtomicInteger stt = new AtomicInteger();
        listFeedback.forEach(i -> {
            AdGetFeedbackResponse obj = new AdGetFeedbackResponse();
            obj.setStt(stt.get() + 1);
            stt.getAndIncrement();
            obj.setId(i.getId());
            obj.setRateQuestion1(i.getRateQuestion1());
            obj.setRateQuestion2(i.getRateQuestion2());
            obj.setRateQuestion3(i.getRateQuestion3());
            obj.setRateQuestion4(i.getRateQuestion4());
            obj.setRateQuestion5(i.getRateQuestion5());
            obj.setAverageRate(i.getAverageRate());
            obj.setDescriptions(i.getDescriptions());
            if (i.getStatus() != null && i.getStatus().equals(StatusShowFeedback.YES)) {
                listResponse.forEach(call -> {
                    if (i.getStudentId().equals(call.getId())) {
                        obj.setStudentId(i.getStudentId());
                        obj.setStudentName(call.getName());
                        obj.setStudentUserName(call.getUserName());
                    }
                });
            }
            listReturn.add(obj);
        });
        objReturn.setListFeedback(listReturn);
        return objReturn;
    }

}
