package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.core.admin.repository.AdCategoryRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdPotalsGroupProjectReposiory;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectCategoryRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectRepository;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.member.model.request.MeFindProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateBackgroundProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateFiledProjectRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeDetailProjectCateResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeDetailUpdateProjectResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeProjectResponse;
import com.labreportapp.portalprojects.core.member.repository.MeProjectRepository;
import com.labreportapp.portalprojects.core.member.service.MeProjectService;
import com.labreportapp.portalprojects.entity.Category;
import com.labreportapp.portalprojects.entity.GroupProject;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.entity.ProjectCategory;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.StatusProject;
import com.labreportapp.portalprojects.infrastructure.constant.TypeProject;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeProjectServiceImpl implements MeProjectService {

    @Autowired
    private MeProjectRepository meProjectRepository;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private AdPotalsGroupProjectReposiory adPotalsGroupProjectReposiory;

    @Autowired
    private AdProjectRepository adProjectRepository;

    @Autowired
    private AdCategoryRepository adCategoryRepository;

    @Autowired
    private AdProjectCategoryRepository adProjectCategoryRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public PageableObject<MeProjectResponse> getAllProjectByIdUser(final MeFindProjectRequest request) {
        request.setIdUser(labReportAppSession.getUserId());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<MeProjectResponse> res = meProjectRepository.getAllProjectById(pageable, request);
        return new PageableObject(res);
    }

    @Override
    public Project findById(String id) {
        Optional<Project> project = meProjectRepository.findById(id);
        if (Objects.isNull(project)) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        return project.get();
    }

    @Override
    @Synchronized
    @Transactional
    public Project updateBackground(@Valid MeUpdateBackgroundProjectRequest request) {
        Optional<Project> projectFind = meProjectRepository.findById(request.getProjectId());
        if (!projectFind.isPresent()) {
            throw new MessageHandlingException(Message.PROJECT_NOT_EXISTS);
        }
        if (request.getType().equals("0")) {
            projectFind.get().setBackgroundImage(request.getName());
            projectFind.get().setBackgroundColor(null);
        } else {
            projectFind.get().setBackgroundImage(null);
            projectFind.get().setBackgroundColor(request.getName());
        }
        return meProjectRepository.save(projectFind.get());
    }

    @Override
    public MeDetailUpdateProjectResponse detailPeriodToProject(String id) {
        Optional<Project> project = meProjectRepository.findById(id);
        if (Objects.isNull(project)) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        Optional<MeDetailProjectCateResponse> projectCustom = meProjectRepository.findOneProjectCategoryById(id);
        MeDetailUpdateProjectResponse objReturn = new MeDetailUpdateProjectResponse();
        objReturn.setProject(project.get());
        objReturn.setProjectCustom(projectCustom.get());
        return objReturn;
    }

    public String convertLongToStringDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    @Override
    @Transactional
    public MeDetailUpdateProjectResponse updateFiledProject(MeUpdateFiledProjectRequest request) {
        Optional<Project> projectCheck = meProjectRepository.findById(request.getId());
        if (projectCheck.isEmpty()) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        String checkCodeProject = adProjectRepository.findByIdCode(request.getCode(), request.getId());
        Project project = projectCheck.get();
        if (checkCodeProject != null && !project.getCode().equals(request.getCode())) {
            throw new RestApiException(Message.CODE_PROJECT_ALREADY_EXISTS);
        }
        StringBuilder message = new StringBuilder();
        message.append("Đã cập nhật dự án xưởng có mã-tên \"").append(projectCheck.get().getCode()).append(" - ").append(projectCheck.get().getName()).append("\": ");

        if (!project.getCode().equals(request.getCode())) {
            message.append(" Mã của  dự án từ: ").append(project.getCode()).append(" thành ").append(request.getCode()).append(",");
        }
        if (!project.getName().equals(request.getName())) {
            message.append(" Tên của dự án từ: ").append(project.getName()).append(" thành ").append(request.getName()).append(",");
        }
        if (!project.getStartTime().equals(request.getStartTime()) || !project.getEndTime().equals(request.getEndTime())) {
            message.append(" Thời gian của  dự án án từ: ").append(convertLongToStringDate(project.getStartTime()))
                    .append("/").append(convertLongToStringDate(project.getEndTime()))
                    .append(" thành ").append(convertLongToStringDate(request.getStartTime()))
                    .append("/").append(convertLongToStringDate(request.getEndTime())).append(",");
        }
        if (!project.getDescriptions().equals(request.getDescriptions())) {
            message.append(" Mô tả của dự án từ: ").append(project.getDescriptions()).append(" thành ").append(request.getDescriptions()).append(",");
        }
        project.setCode(request.getCode().trim());
        project.setDescriptions(!request.getDescriptions().equals("") ? request.getDescriptions().trim() : "");
        project.setName(request.getName().trim());
        project.setProgress(project.getProgress());
        project.setStartTime(request.getStartTime());
        project.setEndTime(request.getEndTime());
        StringBuilder messageGroupProject = new StringBuilder();
        if (request.getGroupProjectId() != null) {
            Optional<GroupProject> groupProjectNew = adPotalsGroupProjectReposiory.findById(request.getGroupProjectId());
            groupProjectNew.ifPresent(value -> project.setGroupProjectId(value.getId()));
            if (projectCheck.get().getGroupProjectId() == null) {
                messageGroupProject.append(" Thêm nhóm dự án ").append(" là ").append(groupProjectNew.get().getName()).append(".");
            } else {
                Optional<GroupProject> groupProjectOld = adPotalsGroupProjectReposiory.findById(projectCheck.get().getGroupProjectId());
                if (groupProjectOld.isPresent() && groupProjectNew.isPresent()) {
                    if (!groupProjectOld.get().getId().equals(groupProjectNew.get().getId())) {
                        messageGroupProject.append(" Nhóm dự án từ ").append(groupProjectOld.get().getName())
                                .append(" thành ").append(groupProjectNew.get().getName()).append(".");
                    }
                }
            }
        } else {
            project.setGroupProjectId(request.getGroupProjectId());
        }


        adProjectCategoryRepository.deleteAllProjectCategoryByIdProject(project.getId());
        List<String> listCaregoryIds = request.getListCategorysId();
        List<ProjectCategory> newCategoryProject = new ArrayList<>();
        listCaregoryIds.forEach(item -> {
            ProjectCategory categoryProject = new ProjectCategory();
            categoryProject.setCategoryId(item);
            categoryProject.setProjectId(project.getId());
            newCategoryProject.add(categoryProject);
        });
        adProjectCategoryRepository.saveAll(newCategoryProject);

        StringBuilder messageCategory = new StringBuilder();
        List<Category> listCategoryDb = adCategoryRepository.findAll();
        newCategoryProject.forEach(cateNew -> {
            listCategoryDb.forEach(cate -> {
                if (cateNew.getCategoryId().equals(cate.getId())) {
                    messageCategory.append(" ").append(cate.getName()).append(",");
                }
            });
        });
        if (messageCategory.length() > 0 && (messageCategory.charAt(messageCategory.length() - 1) == ',')) {
            messageCategory.insert(0, " Thể loại: ");
            messageCategory.setCharAt(messageCategory.length() - 1, '.');
        }

        project.setTypeProject(TypeProject.DU_AN_XUONG_DU_AN);
        Long currentTime = new Date().getTime();
        if (currentTime < request.getStartTime()) {
            project.setStatusProject(StatusProject.CHUA_DIEN_RA);
        }
        if (currentTime >= request.getStartTime() && currentTime <= request.getEndTime()) {
            project.setStatusProject(StatusProject.DANG_DIEN_RA);
        }
        if (request.getEndTime() < currentTime) {
            project.setStatusProject(StatusProject.DA_DIEN_RA);
        }
        //  loggerUtil.sendLogScreen(message.toString() + messageCategory.toString() + messageGroupProject.toString(), "");
        Project projectUp = adProjectRepository.save(project);
        Optional<MeDetailProjectCateResponse> projectCustom = meProjectRepository.findOneProjectCategoryById(projectUp.getId());
        MeDetailUpdateProjectResponse objReturn = new MeDetailUpdateProjectResponse();
        objReturn.setProject(projectUp);
        objReturn.setProjectCustom(projectCustom.get());
        return objReturn;
    }

}
