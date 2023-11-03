package com.labreportapp.labreport.core.admin.service.impl;


import com.labreportapp.labreport.core.admin.model.response.AdFeedBackCustom;
import com.labreportapp.labreport.core.admin.model.response.AdFeedBackResponse;
import com.labreportapp.labreport.core.admin.repository.AdFeedBackRepository;
import com.labreportapp.labreport.core.admin.service.AdFeedBackService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author quynhncph26201
 */
@Service
public class AdFeedBackServiceImpl implements AdFeedBackService {

    @Autowired
    private AdFeedBackRepository adFeedBackRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<AdFeedBackCustom> searchFeedBack(String idClass) {
        List<AdFeedBackResponse> list = adFeedBackRepository.getAllFeedBack(idClass);
        List<String> idListStudent = list.stream()
                .map(AdFeedBackResponse::getIdStudent)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idListStudent);
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

}
