package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.portalprojects.core.admin.model.request.AdCreatLabelRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindLabelRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateLabelRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdLabelReponse;
import com.labreportapp.portalprojects.core.admin.repository.AdlabelReopsitory;
import com.labreportapp.portalprojects.core.admin.service.AdLabelService;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.entity.Label;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import com.labreportapp.portalprojects.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author NguyenVinh
 */
@Service
public class AdLabelServiceImpl implements AdLabelService {

    @Autowired
    private AdlabelReopsitory adlabelReopsitory ;

    private FormUtils formUtils = new FormUtils();

    private List<AdLabelReponse> listLabel;

    @Override
    public PageableObject<AdLabelReponse> searchLabel( final AdFindLabelRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage(), rep.getSize());
        Page<AdLabelReponse> reponses = adlabelReopsitory.findByNameLabel(rep, pageable);
        listLabel = reponses.stream().toList();
        return new PageableObject<>(reponses);
    }

    @Override
    public Label creatLabel(AdCreatLabelRequest command) {
        String code = adlabelReopsitory.getMalabel(command.getCode());
        if(code !=null){
            throw new RestApiException(Message.CODE_LABEL_ALREADY_EXISTS);
        }
        Label label = formUtils.convertToObject(Label.class,command);
        return adlabelReopsitory.save(label);
    }

    @Override
    public Label upadteLabel(AdUpdateLabelRequest command) {
        System.out.println(command.getId());
        Optional<Label> optional = adlabelReopsitory.findById(command.getId());
        if(!optional.isPresent()){
            throw new RestApiException(Message.LABEL_NOT_EXISTS);
        }
//        String checkCodeLabel = adlabelReopsitory.findByCodeLabel(command.getCode(),command.getId());
//        if(checkCodeLabel != null){
//            throw new RestApiException(Message.CODE_LABEL_ALREADY_EXISTS);
//        }
        Label label = optional.get();
        label.setCode(command.getCode());
        label.setName(command.getName());
        label.setColorLabel(command.getColorLabel());
        return adlabelReopsitory.save(label);
    }

    @Override
    public boolean deleteLabel(String id) {
        Optional<Label> optional = adlabelReopsitory.findById(id);
        if(!optional.isPresent()){
            throw new RestApiException(Message.LABEL_NOT_EXISTS);
        }
        adlabelReopsitory.delete(optional.get());
        return true;
    }

    @Override
    public Label getOneByIdLable(String id) {
        Optional<Label> optional = adlabelReopsitory.findById(id);
        if(!optional.isPresent()){
            throw new RestApiException(Message.LABEL_NOT_EXISTS);
        }
        return optional.get();
    }

    @Override
    public List<String> getAllIdByStatus(String status) {
        List<String> list = adlabelReopsitory.getAllIdByStatus(status);
        return list;
    }
}
