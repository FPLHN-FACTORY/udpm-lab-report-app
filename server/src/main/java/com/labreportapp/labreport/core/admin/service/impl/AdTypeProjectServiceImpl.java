//package com.labreportapp.labreport.core.admin.service.impl;
//
//import com.labreportapp.labreport.core.admin.model.request.AdCreateTypeProjectRequest;
//import com.labreportapp.labreport.core.admin.model.request.AdFindTypeProject;
//import com.labreportapp.labreport.core.admin.model.request.AdUpdateTypeProjectRequest;
//import com.labreportapp.labreport.core.admin.model.response.AdTypeProjectResponse;
//import com.labreportapp.labreport.core.admin.repository.AdTypeProjectRepository;
//import com.labreportapp.labreport.core.admin.service.AdTypeProjectService;
//import com.labreportapp.labreport.core.common.base.PageableObject;
//import com.labreportapp.labreport.util.FormUtils;
//import com.labreportapp.portalprojects.entity.TypeProject;
//import com.labreportapp.portalprojects.infrastructure.constant.Message;
//import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.annotation.Validated;
//
//import java.util.List;
//import java.util.Optional;
//
///**
// * @author quynhncph26201
// */
//@Service
//@Validated
//public class AdTypeProjectServiceImpl implements AdTypeProjectService {
//
//    @Autowired
//    private AdTypeProjectRepository adTypeProjectRepository;
//
//    private FormUtils formUtils = new FormUtils();
//
//    private List<AdTypeProjectResponse> adTypeProjectResponseList;
//
//    @Override
//    public List<TypeProject> findAllTypeProject(Pageable pageable) {
//        return adTypeProjectRepository.getAllTypeProject(pageable);
//    }
//
//    @Override
//    public TypeProject createTypeProject(AdCreateTypeProjectRequest obj) {
//        TypeProject typeProject = formUtils.convertToObject(TypeProject.class, obj);
//        return adTypeProjectRepository.save(typeProject);
//    }
//
//    @Override
//    public TypeProject updateTypeProject(AdUpdateTypeProjectRequest obj) {
//        Optional<TypeProject> findById = adTypeProjectRepository.findById(obj.getId());
//        if (!findById.isPresent()) {
//            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
//        }
//        TypeProject typeProject = findById.get();
//        typeProject.setName(obj.getName());
//        typeProject.setDescription(obj.getDescription());
//
//        return adTypeProjectRepository.save(typeProject);
//    }
//
//    @Override
//    public PageableObject<AdTypeProjectResponse> searchTypeProject(AdFindTypeProject rep) {
//        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
//        Page<AdTypeProjectResponse> adTypeProjectResponses = adTypeProjectRepository.searchTypeProject(rep, pageable);
//        adTypeProjectResponseList = adTypeProjectResponses.stream().toList();
//        return new PageableObject<>(adTypeProjectResponses);
//    }
//
//    @Override
//    public Boolean deleteTypeProject(String id) {
//        Optional<TypeProject> findTypeProjectById = adTypeProjectRepository.findById(id);
//
//        if (!findTypeProjectById.isPresent()) {
//            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
//        }
//
//        adTypeProjectRepository.delete(findTypeProjectById.get());
//        return true;
//    }
//}
