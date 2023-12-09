//package com.labreportapp.portalprojects.core.admin.service.impl;
//
//import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateStakeholderRequest;
//import com.labreportapp.portalprojects.core.admin.model.response.AdProjectStkResponse;
//import com.labreportapp.portalprojects.core.admin.repository.AdStakeholderProjectRepository;
//import com.labreportapp.portalprojects.core.admin.service.AdStakeholderProjectService;
//import com.labreportapp.portalprojects.entity.StakeholderProject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.annotation.Validated;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author quynhncph26201
// */
//@Service
//@Validated
//public class AdStakeholderProjectServiceImpl implements AdStakeholderProjectService {
//    @Autowired
//    private AdStakeholderProjectRepository stakeHolderRepository;
//
//    @Override
//    public List<AdProjectStkResponse> getProjectsByStakeholderId(String stakeholderId) {
//        return stakeHolderRepository.findByStakeholderId(stakeholderId);
//    }
//
//    @Override
//    public List<AdProjectStkResponse> getAllProjects() {
//        return stakeHolderRepository.getAllProject();
//    }
//
//    @Override
//    public List<StakeholderProject> updateStakeHolder(AdUpdateStakeholderRequest comand) {
//        List<String> listProject = comand.getListProject();
//        List<StakeholderProject> listStakeProject = new ArrayList<>();
//        if (listProject.isEmpty()) {
//            return null;
//        } else {
//            List<StakeholderProject> newProject = new ArrayList<>();
//            listProject.forEach(item -> {
//                StakeholderProject stakeHolderProjectFind = stakeHolderRepository
//                        .findStakeProjet(comand.getId(), item);
//                if (stakeHolderProjectFind == null) {
//                    StakeholderProject newStackHolderProject = new StakeholderProject();
//                    newStackHolderProject.setStakeholderId(comand.getId());
//                    newStackHolderProject.setProjectId(item);
//                    listStakeProject.add(newStackHolderProject);
//
//                }
//            });
//        }
//        return stakeHolderRepository.saveAll(listStakeProject);
//    }
//
//}
