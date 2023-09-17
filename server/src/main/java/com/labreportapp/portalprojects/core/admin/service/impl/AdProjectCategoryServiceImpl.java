package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.portalprojects.core.admin.model.request.AdCreateProjectCategoryRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateProjectCategoryRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdProjectCategoryReponse;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectCategoryRepository;
import com.labreportapp.portalprojects.core.admin.service.AdProjectCategoryService;
import com.labreportapp.portalprojects.entity.ProjectCategory;
import com.labreportapp.portalprojects.util.FormUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author NguyenVinh
 */
@Service
public class AdProjectCategoryServiceImpl implements AdProjectCategoryService {

    @Autowired
    private AdProjectCategoryRepository adProjectCategoryRepository;

    private FormUtils formUtils = new FormUtils();


    @Override
    public ProjectCategory creatProjectCategory(@Valid AdCreateProjectCategoryRequest command) {
        ProjectCategory projectCategory = formUtils.convertToObject(ProjectCategory.class, command);
        return adProjectCategoryRepository.save(projectCategory);
    }

    @Override
    public List<ProjectCategory> addListProjectCategory(List<AdCreateProjectCategoryRequest> list) {
        List<ProjectCategory> projectCategoryList = new ArrayList<>();
        list.stream().forEach(a -> projectCategoryList.add(formUtils.convertToObject(ProjectCategory.class, a)));
        return adProjectCategoryRepository.saveAll(projectCategoryList);
    }

    @Override
    public List<ProjectCategory> updateListProjectCategory(String idProject, List<AdUpdateProjectCategoryRequest> list) {
        List<ProjectCategory> projectCategoryList = new ArrayList<>();
        List<AdProjectCategoryReponse> listCateProject = adProjectCategoryRepository.getAllByIdProject(idProject);
        if (!list.isEmpty()) {
            listCateProject.stream().forEach(a -> adProjectCategoryRepository.delete(adProjectCategoryRepository.getById(a.getId())));
        }
        list.stream().forEach(a -> projectCategoryList.add(formUtils.convertToObject(ProjectCategory.class, a)));
        return adProjectCategoryRepository.saveAll(projectCategoryList);
    }

    @Override
    public List<AdProjectCategoryReponse> getAllByidProject(String idProject) {
        return adProjectCategoryRepository.getAllByIdProject(idProject);
    }

    @Override
    public ProjectCategory upadteProjectCategory(@Valid AdUpdateProjectCategoryRequest command) {
        Optional<ProjectCategory> optional = adProjectCategoryRepository.findById(command.getId());
        ProjectCategory projectCategory = optional.get();
        projectCategory.setCategoryId(command.getCategoryId());
        projectCategory.setProjectId(command.getProjectId());
        return adProjectCategoryRepository.save(projectCategory);
    }
}
