//package com.labreportapp.labreport.core.admin.service;
//
//import com.labreportapp.labreport.core.admin.model.request.*;
//import com.labreportapp.labreport.core.admin.model.response.AdTypeProjectResponse;
//import com.labreportapp.labreport.core.common.base.PageableObject;
//import com.labreportapp.portalprojects.entity.TypeProject;
//import jakarta.validation.Valid;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
///**
// * @author quynhncph26201
// */
//public interface AdTypeProjectService {
//    List<TypeProject> findAllTypeProject(Pageable pageable);
//
//    TypeProject createTypeProject(@Valid final AdCreateTypeProjectRequest obj);
//
//    TypeProject updateTypeProject(final AdUpdateTypeProjectRequest obj);
//
//    PageableObject<AdTypeProjectResponse> searchTypeProject(final AdFindTypeProject rep);
//
//    Boolean deleteTypeProject(final String id);
//}
