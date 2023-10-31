package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdFindProjectStatisticTopRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindProjectStatisticsRequest;
import com.labreportapp.labreport.core.admin.model.response.AdCountStatusProjectResponse;
import com.labreportapp.labreport.core.admin.model.response.AdObjListStatisticsResponse;
import com.labreportapp.labreport.core.admin.model.response.AdProjectProgressResponse;
import com.labreportapp.labreport.core.admin.model.response.AdProjectStatisticTopFiveResponse;
import com.labreportapp.labreport.core.admin.model.response.AdProjectStatisticsProgressTaskResponse;
import com.labreportapp.labreport.core.admin.model.response.AdProjectTaskResponse;
import com.labreportapp.labreport.core.admin.service.AdProjectStatisticsService;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author hieundph25894-duchieu212
 */
@Service
public class AdProjectStatisticsServiceImpl implements AdProjectStatisticsService {

    @Autowired
    private AdProjectRepository adProjectRepository;

    @Override
    public AdObjListStatisticsResponse findAllProjectStatisticTypeXuong(final AdFindProjectStatisticsRequest request) {
        request.setStartTimeLong(null);
        request.setEndTimeLong(null);
        if (!request.getStartTime().equals("")) {
            request.setStartTimeLong(DateUtils.truncate(new Date(Long.parseLong(request.getStartTime())), Calendar.DATE).getTime());
        }
        if (!request.getEndTime().equals("")) {
            request.setEndTimeLong(DateUtils.truncate(new Date(Long.parseLong(request.getEndTime())), Calendar.DATE).getTime());
        }
        List<AdProjectStatisticsProgressTaskResponse> list = adProjectRepository.findAllProjectStatistic(request);
        AdCountStatusProjectResponse count = adProjectRepository.countProjectStatistic(request);
        AdObjListStatisticsResponse obj = new AdObjListStatisticsResponse();
        List<AdProjectProgressResponse> listProgress = new ArrayList<>();
        List<AdProjectTaskResponse> listTasks = new ArrayList<>();
        list.forEach(item -> {
            AdProjectProgressResponse progress = new AdProjectProgressResponse();
            progress.setId(item.getId());
            progress.setCode(item.getCode());
            progress.setName(item.getName());
            progress.setProgress(item.getProgress());
            listProgress.add(progress);
            AdProjectTaskResponse task = new AdProjectTaskResponse();
            task.setId(item.getId());
            task.setCode(item.getCode());
            task.setName(item.getName());
            task.setTotalTasks(item.getTotalTasks());
            task.setCompletedTasks(item.getCompletedTasks());
            listTasks.add(task);
        });
        obj.setListProgress(listProgress);
        obj.setListTasks(listTasks);
        obj.setCountGroupProject(count.getCountGroupProject());
        obj.setCountProjectNotStart(count.getCountProjectNotStart());
        obj.setCountProjectStarting(count.getCountProjectStarting());
        obj.setCountProjectEnding(count.getCountProjectEnding());
        return obj;
    }

    @Override
    public List<AdProjectStatisticTopFiveResponse> getProjectFindTop(final AdFindProjectStatisticTopRequest request) {
        request.setStartTimeLong(null);
        request.setEndTimeLong(null);
        if (!request.getStartTime().equals("")) {
            request.setStartTimeLong(DateUtils.truncate(new Date(Long.parseLong(request.getStartTime())), Calendar.DATE).getTime());
        }
        if (!request.getEndTime().equals("")) {
            request.setEndTimeLong(DateUtils.truncate(new Date(Long.parseLong(request.getEndTime())), Calendar.DATE).getTime());
        }
        return adProjectRepository.getProjectFindTop(request);
    }
}
