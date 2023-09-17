package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.portalprojects.core.member.model.request.MeCreateLabelProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteLabelProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateLabelProjectRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeLabelResponse;
import com.labreportapp.portalprojects.core.member.repository.MeLabelProjectRepository;
import com.labreportapp.portalprojects.core.member.repository.MeLabelRepository;
import com.labreportapp.portalprojects.core.member.service.MeLabelService;
import com.labreportapp.portalprojects.entity.LabelProject;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import com.labreportapp.portalprojects.infrastructure.successnotification.ConstantMessageSuccess;
import com.labreportapp.portalprojects.infrastructure.successnotification.SuccessNotificationSender;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeLabelServiceImpl implements MeLabelService {

    @Autowired
    private MeLabelRepository meLabelRepository;

    @Autowired
    private MeLabelProjectRepository meLabelProjectRepository;

    @Autowired
    private SuccessNotificationSender successNotificationSender;

    @Override
    public List<MeLabelResponse> getAllLabelByIdTodo(String idTodo) {
        return meLabelRepository.getAllLabelByIdTodo(idTodo);
    }

    @Override
    @Cacheable(value = "allLabelsByProject", key = "#idProject")
    public List<MeLabelResponse> getAll(String idProject) {
        return meLabelRepository.getAll(idProject);
    }

    @Override
    @Synchronized
    @CacheEvict(value = {"allLabelsByProject"}, allEntries = true)
    public LabelProject create(@Valid MeCreateLabelProjectRequest request, StompHeaderAccessor headerAccessor) {
        LabelProject labelProject = new LabelProject();
        labelProject.setCode(String.valueOf(new Date().getTime()));
        labelProject.setName(request.getName());
        labelProject.setColorLabel(request.getColor());
        labelProject.setProjectId(request.getProjectId());
        LabelProject newLabelProject = meLabelProjectRepository.save(labelProject);

        successNotificationSender.senderNotification(ConstantMessageSuccess.THEM_THANH_CONG, headerAccessor);
        return newLabelProject;
    }

    @Override
    @Transactional
    @Synchronized
    @CacheEvict(value = {"allLabelsByProject", "labelsByTodo"}, allEntries = true)
    public LabelProject update(@Valid MeUpdateLabelProjectRequest request, StompHeaderAccessor headerAccessor) {
        Optional<LabelProject> labelProjectFind = meLabelProjectRepository.findById(request.getId());
        if (!labelProjectFind.isPresent()) {
            throw new MessageHandlingException(Message.LABEL_NOT_EXISTS);
        }
        labelProjectFind.get().setName(request.getName());
        labelProjectFind.get().setColorLabel(request.getColor());
        LabelProject newLabelProject = meLabelProjectRepository.save(labelProjectFind.get());
        successNotificationSender.senderNotification(ConstantMessageSuccess.CAP_NHAT_THANH_CONG, headerAccessor);
        return newLabelProject;
    }

    @Override
    @Transactional
    @Synchronized
    @CacheEvict(value = {"allLabelsByProject", "labelsByTodo"}, allEntries = true)
    public String delete(@Valid MeDeleteLabelProjectRequest request, StompHeaderAccessor headerAccessor) {
        List<String> listIdTodo = meLabelProjectRepository.getAllTodoByIdProject(request.getProjectId());
        for (String xx : listIdTodo) {
            meLabelProjectRepository.deleteLabelProjectTodo(request.getId(), xx);
        }
        meLabelProjectRepository.deleteById(request.getId());
        successNotificationSender.senderNotification(ConstantMessageSuccess.XOA_THANH_CONG, headerAccessor);
        return request.getId();
    }

    @Override
    public LabelProject detail(String id) {
        return meLabelProjectRepository.findById(id).get();
    }

}
