package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.member.model.request.MeBasePeriodRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeFindPeriodRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdatePeriodRequest;
import com.labreportapp.portalprojects.core.member.model.response.MePeriodResponse;
import com.labreportapp.portalprojects.core.member.repository.MePeriodRepository;
import com.labreportapp.portalprojects.core.member.repository.MeProjectRepository;
import com.labreportapp.portalprojects.core.member.service.MePeriodService;
import com.labreportapp.portalprojects.entity.Period;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.StatusPeriod;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import com.labreportapp.portalprojects.infrastructure.projection.SimpleEntityProj;
import com.labreportapp.portalprojects.util.PeriodHelper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MePeriodServiceImpl implements MePeriodService {

    @Autowired
    private MePeriodRepository mePeriodRepository;

    @Autowired
    private MeProjectRepository meProjectRepository;

    @Autowired
    private PeriodHelper periodHelper;

    @Override
    public List<SimpleEntityProj> findAllSimpleEntity() {
        return mePeriodRepository.findAllSimpleEntity();
    }

    @Override
    public List<MePeriodResponse> getAllPeriodByIdProject(MeFindPeriodRequest req, String idProject) {
        return mePeriodRepository.getAllPeriodByIdProject(idProject);
    }

    @Override
    public PageableObject<MePeriodResponse> getAllPeriod(MeFindPeriodRequest request, String idProject) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<MePeriodResponse> res = mePeriodRepository.getAllPeriod(request, pageable, idProject);
        return new PageableObject(res);
    }

    @Override
    public Period findById(String id) {
        return mePeriodRepository.findById(id).get();
    }

    @Override
    @Synchronized
    public Period create(@Valid final MeBasePeriodRequest meCreatePeriodRequest) {
        MeBasePeriodRequest request = new MeBasePeriodRequest();
        request.setStartTime(meCreatePeriodRequest.getStartTime());
        request.setEndTime(meCreatePeriodRequest.getEndTime());
        request.setProjectId(meCreatePeriodRequest.getProjectId());
        Optional<Project> projectCheck = meProjectRepository.findById(meCreatePeriodRequest.getProjectId());
        if (!projectCheck.isPresent()) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        Period result = checkDatePeriod(request, null);
        Period period = new Period();
        period.setCode(periodHelper.genCodePeriod(meCreatePeriodRequest.getProjectId()));
        period.setName(meCreatePeriodRequest.getName());
        period.setDescriptions(meCreatePeriodRequest.getDescriptions());
        period.setTarget(meCreatePeriodRequest.getTarget());
        period.setProgress((float) 0);
        period.setStartTime(result.getStartTime());
        period.setEndTime(result.getEndTime());
        period.setProjectId(meCreatePeriodRequest.getProjectId());
        Long currentTime = new Date().getTime();
        if (currentTime < result.getStartTime()) {
            period.setStatusPeriod(StatusPeriod.CHUA_DIEN_RA);
        }
        if (result.getStartTime() <= currentTime && currentTime <= result.getEndTime()) {
            period.setStatusPeriod(StatusPeriod.DANG_DIEN_RA);
        }
        if (result.getEndTime() < currentTime) {
            period.setStatusPeriod(StatusPeriod.DA_DIEN_RA);
        }
        Period newPeriod = mePeriodRepository.save(period);

        updateProgressProject(meCreatePeriodRequest.getProjectId());
        return newPeriod;
    }

    public void updateProgressProject(String idProject) {
        List<Float> listProgressByIdProject = mePeriodRepository.getAllProgressByIdProject(idProject);

        float sumPro = 0;
        for (Float progress : listProgressByIdProject) {
            sumPro += progress;
        }
        float averagePro = sumPro / listProgressByIdProject.size();

        DecimalFormat decimalFormatPro = new DecimalFormat("#.##");
        decimalFormatPro.setRoundingMode(RoundingMode.HALF_UP);
        String roundedAveragePro = decimalFormatPro.format(averagePro);

        Optional<Project> projectFind = meProjectRepository.findById(idProject);
        if (!projectFind.isPresent()) {
            throw new MessageHandlingException(Message.PROJECT_NOT_EXISTS);
        }
        projectFind.get().setProgress(Float.parseFloat(roundedAveragePro));
        meProjectRepository.save(projectFind.get());
    }

    @Override
    @Synchronized
    @Transactional
    public Period update(@Valid final MeUpdatePeriodRequest meUpdatePeriodRequest) {
        Optional<Period> periodCheck = mePeriodRepository.findById(meUpdatePeriodRequest.getId());

        if (!periodCheck.isPresent()) {
            throw new RestApiException(Message.PERIOD_NOT_EXISTS);
        }
        MeUpdatePeriodRequest request = new MeUpdatePeriodRequest();
        request.setStartTime(meUpdatePeriodRequest.getStartTime());
        request.setEndTime(meUpdatePeriodRequest.getEndTime());
        request.setProjectId(meUpdatePeriodRequest.getProjectId());

        Period result = checkDatePeriod(request, meUpdatePeriodRequest.getId());
        Period period = periodCheck.get();
        period.setCode(periodCheck.get().getCode());
        period.setName(meUpdatePeriodRequest.getName());
        period.setDescriptions(meUpdatePeriodRequest.getDescriptions());
        period.setTarget(meUpdatePeriodRequest.getTarget());
        period.setProgress(periodCheck.get().getProgress());
        period.setStartTime(result.getStartTime());
        period.setEndTime(result.getEndTime());
        period.setProjectId(meUpdatePeriodRequest.getProjectId());
        return mePeriodRepository.save(period);
    }

    @Override
    public Period checkDatePeriod(MeBasePeriodRequest request, String id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = sdf.parse(request.getStartTime());
            endTime = sdf.parse(request.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RestApiException(Message.INVALID_DATE);
        }
        Long startTimeLong = startTime.getTime();
        Long endTimeLong = endTime.getTime();
        if (endTimeLong < startTimeLong) {
            throw new RestApiException((Message.INVALID_END_TIME));
        }
        List<Period> listPeriod = mePeriodRepository.getAllEntityPeriodByIdProject(request.getProjectId());
        for (Period period : listPeriod) {
            if (id != null) {
                if (period.getId().equals(id)) {
                    continue;
                }
            }
            if (startTimeLong < period.getEndTime()
                    && endTimeLong > period.getStartTime()) {
                throw new RestApiException(Message.PERIOD_OVERLAP);
            }
        }
        Optional<Project> projectFind = meProjectRepository.findById(request.getProjectId());
        if (startTimeLong < projectFind.get().getStartTime()) {
            throw new RestApiException(Message.START_TIME_OF_PERIOD_NO_SMALL_BETTER_START_TIME_OF_PROJECT);
        }
        if (endTimeLong > projectFind.get().getEndTime()) {
            throw new RestApiException(Message.END_TIME_OF_PERIOD_NO_BIG_BETTER_END_TIME_OF_PROJECT);
        }
        Period result = new Period();
        result.setStartTime(startTimeLong);
        result.setEndTime(endTimeLong);
        return result;
    }

    @Override
    @Synchronized
    @Transactional
    public String delete(String id, String projectId) {
        Short count = mePeriodRepository.checkPeriodTodo(id);
        if (count > 0) {
            throw new RestApiException(Message.CAN_NOT_DELETE_PERIOD_HAVE_TODO);
        }
        mePeriodRepository.deleteById(id);
        updateProgressProject(projectId);
        return id;
    }
}
