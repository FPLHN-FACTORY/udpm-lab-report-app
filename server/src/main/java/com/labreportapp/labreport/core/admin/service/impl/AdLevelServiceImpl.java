package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateLevelRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindLevelRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateLevelRequest;
import com.labreportapp.labreport.core.admin.model.response.AdLevelResponse;
import com.labreportapp.labreport.core.admin.repository.AdLevelRepository;
import com.labreportapp.labreport.core.admin.service.AdLevelService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.Level;
import com.labreportapp.labreport.util.FormUtils;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class AdLevelServiceImpl implements AdLevelService {

    @Autowired
    private AdLevelRepository adLevelRepository;

    private FormUtils formUtils = new FormUtils();

    private List<AdLevelResponse> adLevelResponsesList;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public List<Level> findAllLevel(Pageable pageable) {
        return adLevelRepository.getAllLevel(pageable);
    }

    @Override
    public Level createLevel(@Valid AdCreateLevelRequest obj) {
        Level level = formUtils.convertToObject(Level.class, obj);
        loggerUtil.sendLogScreen("Đã thêm Level mới \"" + level.getName() + "\"", "");
        return adLevelRepository.save(level);
    }

    @Override
    public Level updateLevel(AdUpdateLevelRequest obj) {
        Optional<Level> findById = adLevelRepository.findById(obj.getId());
        if (!findById.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        Level level = findById.get();
        StringBuilder message = new StringBuilder();
        if (!level.getName().equals(obj.getName())) {
            message.append("Đã cập nhật tên level từ \"").append(level.getName()).append("\" thành ").append("\"").append(obj.getName()).append(".");
        }
        level.setName(obj.getName());
        loggerUtil.sendLogScreen(message.toString(), "");
        return adLevelRepository.save(level);
    }

    @Override
    public PageableObject<AdLevelResponse> searchLevel(AdFindLevelRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<AdLevelResponse> adLevelResponses = adLevelRepository.searchLevel(rep, pageable);
        adLevelResponsesList = adLevelResponses.stream().toList();
        return new PageableObject<>(adLevelResponses);
    }

    @Override
    public Boolean deleteLevel(String id) {
        Optional<Level> findLevelById = adLevelRepository.findById(id);
        Integer countActivities = adLevelRepository.countActivitiesByLevelId(id);

        if (!findLevelById.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        if (countActivities != null && countActivities > 0) {
            throw new RestApiException(Message.LEVEL_ACTIVITY_ALREADY_EXISTS);
        }
        adLevelRepository.delete(findLevelById.get());
        loggerUtil.sendLogScreen("Đã xóa level \"" + findLevelById.get().getName() + "\".", "");
        return true;
    }
}
