package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeFeedbackResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeObjFeedbackResponse;
import com.labreportapp.labreport.core.teacher.repository.TeActivityRepository;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeFeedbackRepository;
import com.labreportapp.labreport.core.teacher.service.TeFeedBackService;
import com.labreportapp.labreport.entity.FeedBack;
import com.labreportapp.labreport.infrastructure.constant.StatusShowFeedback;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.labreport.util.SemesterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author hieundph25894 - duchieu212
 */
@Service
public class TeFeedBackServiceImpl implements TeFeedBackService {

    @Autowired
    private TeFeedbackRepository teFeedbackRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private TeActivityRepository teActivityRepository;

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Override
    public TeObjFeedbackResponse getAllFeedbackByIdClass(String idClass) {
        TeObjFeedbackResponse objReturn = new TeObjFeedbackResponse();
        List<FeedBack> listFeedback = teFeedbackRepository.getAllFeedBackByIdClass(idClass);
        String codeClass = teClassRepository.findCodeByIdClass(idClass);
        objReturn.setCodeClass(codeClass != null ? codeClass : "");
        List<TeFeedbackResponse> listReturn = new ArrayList<>();
        if (listFeedback.size() == 0) {
            objReturn.setListFeedback(listReturn);
            return objReturn;
        }
        List<String> idStudentList = listFeedback.stream()
                .map(FeedBack::getStudentId)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        AtomicInteger stt = new AtomicInteger();
        stt.set(1);
        listFeedback.forEach(i -> {
            TeFeedbackResponse obj = new TeFeedbackResponse();
            obj.setStt(stt.getAndIncrement());
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
