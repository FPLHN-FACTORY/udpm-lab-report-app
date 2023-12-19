package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.infrastructure.constant.RoleDefault;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.core.admin.model.request.AdCreateMemberProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdCreateProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdListMemberUpdateRoleRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateProjectRoleRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdDetailProjectCateMemberRespone;
import com.labreportapp.portalprojects.core.admin.model.response.AdMemberAndRoleProjectCustomResponse;
import com.labreportapp.portalprojects.core.admin.model.response.AdProjectReponse;
import com.labreportapp.portalprojects.core.admin.repository.AdCategoryRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdGroupProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdLabelProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdMemberProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdPotalsGroupProjectReposiory;
import com.labreportapp.portalprojects.core.admin.repository.AdPotalsRoleConfigRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdPotalsRoleMemberProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdPotalsRoleProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectCategoryRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectRepository;
import com.labreportapp.portalprojects.core.admin.service.AdMemberProjectService;
import com.labreportapp.portalprojects.core.admin.service.AdProjectService;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.entity.Category;
import com.labreportapp.portalprojects.entity.GroupProject;
import com.labreportapp.portalprojects.entity.Label;
import com.labreportapp.portalprojects.entity.LabelProject;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.entity.ProjectCategory;
import com.labreportapp.portalprojects.entity.RoleConfig;
import com.labreportapp.portalprojects.entity.RoleMemberProject;
import com.labreportapp.portalprojects.entity.RoleProject;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.StatusProject;
import com.labreportapp.portalprojects.infrastructure.constant.StatusWork;
import com.labreportapp.portalprojects.infrastructure.constant.TypeProject;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import com.labreportapp.portalprojects.repository.LabelRepository;
import com.labreportapp.portalprojects.repository.RoleProjectRepository;
import jakarta.validation.Valid;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
@Validated
public class AdProjectServiceImpl implements AdProjectService {

    @Autowired
    private AdProjectRepository adProjectRepository;

    @Autowired
    @Qualifier(LabelRepository.NAME)
    private LabelRepository labelRepository;

    @Autowired
    private AdLabelProjectRepository adLabelProjectRepository;

    @Autowired
    private AdProjectCategoryRepository adProjectCategoryRepository;

    @Autowired
    private AdMemberProjectRepository adMemberProjectRepository;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private AdPotalsRoleConfigRepository adPotalsRoleConfigRepository;

    @Autowired
    private AdPotalsRoleProjectRepository adPotalsRoleProjectRepository;

    @Autowired
    private AdPotalsRoleMemberProjectRepository adPotalsRoleMemberProjectRepository;

    @Autowired
    private AdMemberProjectService adMemberProjectService;

    @Autowired
    private AdPotalsGroupProjectReposiory adPotalsGroupProjectReposiory;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private AdGroupProjectRepository adGroupProjectRepository;

    @Autowired
    private AdCategoryRepository adCategoryRepository;

    @Autowired
    @Qualifier(RoleProjectRepository.NAME)
    private RoleProjectRepository roleProjectRepository;

    @Override
    public List<Project> findAllProject(Pageable pageable) {
        return adProjectRepository.findAllProject(pageable);
    }

    private List<AdProjectReponse> listProject;

    @Override
    public PageableObject<AdProjectReponse> searchProject(final AdFindProjectRequest rep) {
        rep.setStartTimeLong(null);
        rep.setEndTimeLong(null);
        if (!rep.getStartTime().equals("")) {
            rep.setStartTimeLong(DateUtils.truncate(new Date(Long.parseLong(rep.getStartTime())), Calendar.DATE).getTime());
        }
        if (!rep.getEndTime().equals("")) {
            rep.setEndTimeLong(DateUtils.truncate(new Date(Long.parseLong(rep.getEndTime())), Calendar.DATE).getTime());
        }
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<AdProjectReponse> reponses = adProjectRepository.findProjectPage(rep, pageable);
        listProject = reponses.stream().toList();
        return new PageableObject<>(reponses);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public AdProjectReponse createProject(@Valid AdCreateProjectRequest request) {
        String checkCode = adProjectRepository.getProjectByCode(request.getCode());
        if (checkCode != null) {
            throw new RestApiException(Message.CODE_PROJECT_ALREADY_EXISTS);
        }
        if (request.getStartTime() != null && request.getEndTime() != null) {
            if (request.getEndTime() - request.getStartTime() <= 0) {
                throw new RestApiException(Message.START_TIME_MORE_THAN_END_TIME);
            }
        } else if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new RestApiException(Message.TIME_NOT_NULL);
        }
        List<AdCreateMemberProjectRequest> listMember = request.getListMembers();
        Project project = new Project();
        project.setCode(request.getCode().trim());
        project.setName(request.getName().trim());
        project.setDescriptions(!request.getDescriptions().equals("") ? request.getDescriptions().trim() : "");
        project.setProgress(project.getProgress());
//        project.setStartTime(DateUtils.truncate(new Date(request.getStartTime()), Calendar.DATE).getTime());
//        project.setEndTime(DateUtils.truncate(new Date(request.getEndTime()), Calendar.DATE).getTime());
        project.setStartTime(request.getStartTime());
        project.setEndTime(request.getEndTime());
        project.setProgress(0F);
        project.setBackgroundColor("#59a1e3");
        project.setTypeProject(TypeProject.DU_AN_XUONG_DU_AN);
        project.setDescriptions(!request.getDescriptions().equals("") ? request.getDescriptions().trim() : "");
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

        GroupProject groupProjectOld = null;
        if (request.getGroupProjectId() != null) {
            Optional<GroupProject> groupProject = adPotalsGroupProjectReposiory.findById(request.getGroupProjectId());
            if (groupProject.isPresent()) {
                groupProject.ifPresent(value -> project.setGroupProjectId(value.getId()));
                groupProjectOld = groupProject.get();
            }

        } else {
            project.setGroupProjectId(request.getGroupProjectId());
        }
        Project newProject = adProjectRepository.save(project);

        StringBuilder message = new StringBuilder();
        message.append("Đã tạo dự án xưởng mới: ").append("  Mã dự án: ").append(newProject.getCode()).append(", ").append("Tên dự án: ").append(project.getName()).append(" , Thời gian bắt đầu/kết thúc: ").append(convertLongToStringDate(newProject.getStartTime())).append("/").append(convertLongToStringDate(newProject.getEndTime()));
        if (!project.getDescriptions().equals("")) {
            message.append(", Mô tả: ").append(newProject.getDescriptions());
        }
        message.append(", Background mặc định. ");
        StringBuilder messageGroupProject = new StringBuilder();
        if (groupProjectOld != null) {
            messageGroupProject.append(" Nhóm dự án: ").append(groupProjectOld.getName()).append(".");
        }

        List<String> listCaregoryIds = request.getListCategorysId();
        List<ProjectCategory> newCategoryProject = new ArrayList<>();
        listCaregoryIds.forEach(item -> {
            ProjectCategory categoryProject = new ProjectCategory();
            categoryProject.setCategoryId(item);
            categoryProject.setProjectId(newProject.getId());
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
        if (messageCategory.length() > 0 && messageCategory.charAt(messageCategory.length() - 1) == ',') {
            messageCategory.insert(0, " Thể loại: ");
            messageCategory.setCharAt(messageCategory.length() - 1, '.');
        }
        List<Label> listLabel = labelRepository.findAll();
        List<LabelProject> newLabelProject = new ArrayList<>();
        listLabel.forEach(item -> {
            LabelProject labelProject = new LabelProject();
            labelProject.setProjectId(newProject.getId());
            labelProject.setColorLabel(item.getColorLabel());
            labelProject.setName(item.getName());
            newLabelProject.add(labelProject);
        });
        adLabelProjectRepository.saveAll(newLabelProject);

        List<MemberProject> newMemberProject = new ArrayList<>();
        listMember.forEach(item -> {
            MemberProject memberProject = new MemberProject();
            memberProject.setProjectId(newProject.getId());
            memberProject.setMemberId(item.getMemberId());
            memberProject.setEmail(item.getEmail());
            memberProject.setStatusWork(StatusWork.DANG_LAM);
            newMemberProject.add(memberProject);
        });

        List<RoleConfig> listRoleConfig = adPotalsRoleConfigRepository.findAll();
        List<RoleProject> newRoleProject = new ArrayList<>();
        listRoleConfig.forEach(item -> {
            RoleProject roleProject = new RoleProject();
            roleProject.setProjectId(newProject.getId());
            roleProject.setName(item.getName());
            roleProject.setDescription(item.getDescription());
            roleProject.setRoleDefault(item.getRoleDefault());
            newRoleProject.add(roleProject);
        });
        List<RoleProject> listRoleProject = adPotalsRoleProjectRepository.saveAll(newRoleProject);

        List<RoleMemberProject> newRoleMemberProject = new ArrayList<>();
        Optional<AdCreateMemberProjectRequest> memberDefault = listMember.stream()
                .filter(i -> i.getMemberId().equals(labReportAppSession.getUserId()))
                .findFirst();
        if (!memberDefault.isPresent()) {
            MemberProject memberCreateProject = new MemberProject();
            memberCreateProject.setMemberId(labReportAppSession.getUserId());
            memberCreateProject.setProjectId(newProject.getId());
            memberCreateProject.setEmail(labReportAppSession.getEmail());
            memberCreateProject.setStatusWork(StatusWork.DANG_LAM);
            memberCreateProject.setId(adMemberProjectRepository.save(memberCreateProject).getId());

            RoleMemberProject roleMemberProjectDefault = new RoleMemberProject();
            roleMemberProjectDefault.setMemberProjectId(memberCreateProject.getId());
            Optional<RoleProject> roleProSetDefault = listRoleProject.stream()
                    .filter(i -> i.getRoleDefault().equals(RoleDefault.DEFAULT))
                    .findFirst();

            if (roleProSetDefault.isPresent()) {
                roleMemberProjectDefault.setRoleProjectId(roleProSetDefault.get().getId());
            } else {
                if (listRoleProject.size() > 0) {
                    RoleProject roleProjectAp = listRoleProject.get(0);
                    roleMemberProjectDefault.setRoleProjectId(roleProjectAp.getId());
                }
            }
            newRoleMemberProject.add(roleMemberProjectDefault);
        }
        List<MemberProject> listMemberProject = adMemberProjectRepository.saveAll(newMemberProject);
        listMember.forEach(member -> {
            member.getListRole().forEach(role -> {
                listRoleProject.forEach(roleProject -> {
                    if (roleProject.getName().equals(role)) {
                        RoleMemberProject roleMemberProject = new RoleMemberProject();
                        roleMemberProject.setRoleProjectId(roleProject.getId());
                        listMemberProject.forEach(memberProject -> {
                            if (member.getMemberId().equals(memberProject.getMemberId())) {
                                roleMemberProject.setMemberProjectId(memberProject.getId());
                            }
                        });
                        newRoleMemberProject.add(roleMemberProject);
                    }
                });
            });
        });
        List<RoleMemberProject> listMemberRoleSave = adPotalsRoleMemberProjectRepository.saveAll(newRoleMemberProject);

        StringBuilder messageStudentRole = new StringBuilder();
        List<String> idStudentList = listMemberProject.stream()
                .map(MemberProject::getMemberId)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = callApiIdentity.handleCallApiGetListUserByListId(idStudentList);

        listMemberProject.forEach(member -> {
            listRespone.forEach(infor -> {
                if (member.getMemberId().equals(infor.getId())) {
                    messageStudentRole.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName());
//                        listRoleProject.forEach(role -> {
//                            if (role.getId().equals(i.getRoleProjectId())) {
//                                messageStudentRole.append(" ").append(role.getName());
//                            }
//                        });
                    messageStudentRole.append(",");
                }

            });
        });
        if (messageStudentRole.length() > 0 && messageStudentRole.charAt(messageStudentRole.length() - 1) == ',') {
            messageStudentRole.insert(0, " Thành viên: ");
            messageStudentRole.setCharAt(messageStudentRole.length() - 1, '.');
        }
        loggerUtil.sendLogScreen(message.toString() + messageCategory.toString() + messageGroupProject.toString() + messageStudentRole.toString(), "");
        Optional<AdProjectReponse> projectView = adProjectRepository.findOneProjectById(newProject.getId());
        return projectView.get();
    }

    public String convertLongToStringDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public AdProjectReponse updateProject(@Valid AdUpdateProjectRoleRequest request, String idProject) {
        Optional<Project> projectCheck = adProjectRepository.findById(idProject);
        if (projectCheck.isEmpty()) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        String checkCodeProject = adProjectRepository.findByIdCode(request.getCode(), idProject);
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
            project.setGroupProjectId(null);
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
        List<AdListMemberUpdateRoleRequest> listMember = request.getListMember();

        Project newProject = adProjectRepository.save(project);
        adProjectCategoryRepository.deleteAllProjectCategoryByIdProject(idProject);
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

        List<MemberProject> newMemberProject = new ArrayList<>();
        List<AdMemberAndRoleProjectCustomResponse> listMemberJoinRole = adMemberProjectService
                .findAllMemberJoinProject(idProject);

        listMember.forEach(item -> {
            Optional<AdMemberAndRoleProjectCustomResponse> objFind = findMemberRoleByMemberId(
                    listMemberJoinRole, item.getMemberId());
            if (objFind.isEmpty()) {
                MemberProject memberProject = new MemberProject();
                memberProject.setProjectId(newProject.getId());
                memberProject.setMemberId(item.getMemberId());
                memberProject.setEmail(item.getEmail());
                memberProject.setStatusWork(StatusWork.DANG_LAM);
                newMemberProject.add(memberProject);
            }
        });

        List<RoleMemberProject> newRoleMemberProject = new ArrayList<>();
        List<MemberProject> listMemberProjectSave = adMemberProjectRepository.saveAll(newMemberProject);
        List<MemberProject> listMemberProject = adMemberProjectRepository.getAllMemberProjectByIdProject(idProject);

        StringBuilder messageStudentRole = new StringBuilder();
        ConcurrentHashMap<String, SimpleResponse> mapInforStudent = new ConcurrentHashMap<>();
        addDataMapsInforStudent(mapInforStudent, listMemberProject);
        adPotalsRoleMemberProjectRepository.deleteAllRoleMemberProjectByIdProject(idProject);
        listMember.forEach(member -> {
            member.getListRole().forEach(role -> {
                listMemberProject.forEach(memberProject -> {
                    if (memberProject.getMemberId().equals(member.getMemberId())) {
                        RoleMemberProject roleMemberProject = new RoleMemberProject();
                        roleMemberProject.setMemberProjectId(memberProject.getId());
                        roleMemberProject.setRoleProjectId(role.getIdRole());
                        newRoleMemberProject.add(roleMemberProject);

                    }
                });
            });
        });
        listMemberProjectSave.forEach(member -> {
            SimpleResponse infor = mapInforStudent.get(member.getMemberId());
            if (infor != null) {
                messageStudentRole.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName());
            }
            messageStudentRole.append(",");
        });
        if (messageStudentRole.length() > 0 && messageStudentRole.charAt(messageStudentRole.length() - 1) == ',') {
            messageStudentRole.insert(0, " Thêm thành viên: ");
            messageStudentRole.setCharAt(messageStudentRole.length() - 1, '.');
        }

        adPotalsRoleMemberProjectRepository.saveAll(newRoleMemberProject);
        StringBuilder messageStudentRoleDelete = new StringBuilder();
        if (listMemberProject.size() > listMember.size()) {
            List<MemberProject> listMemberProjectDeleteAll = listMemberProject.stream()
                    .filter(member1 -> listMember.stream().noneMatch(member2 -> member1.getMemberId().equals(member2.getMemberId())))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            listMemberProjectDeleteAll.forEach(member -> {
                SimpleResponse studentFind = mapInforStudent.get(member.getMemberId());
                if (studentFind != null) {
                    messageStudentRoleDelete.append(" ").append(studentFind.getName()).append(" - ").append(studentFind.getUserName());
                }
                messageStudentRoleDelete.append(",");
            });
            adMemberProjectRepository.deleteAll(listMemberProjectDeleteAll);
        }
        if (messageStudentRoleDelete.length() > 0 && messageStudentRoleDelete.charAt(messageStudentRoleDelete.length() - 1) == ',') {
            messageStudentRoleDelete.insert(0, " Xóa thành viên: ");
            messageStudentRoleDelete.setCharAt(messageStudentRoleDelete.length() - 1, '.');
        }
        loggerUtil.sendLogScreen(message.toString() + messageCategory.toString() + messageGroupProject.toString() + messageStudentRole.toString() + messageStudentRoleDelete.toString(), "");
        Optional<AdProjectReponse> projectView = adProjectRepository.findOneProjectById(newProject.getId());
        return projectView.get();
    }

    public void addDataMapsInforStudent(ConcurrentHashMap<String, SimpleResponse> mapAll, List<MemberProject> listMemberProject) {
        List<String> idStudentList = listMemberProject.stream()
                .map(MemberProject::getMemberId)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = callApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        getAllPutMapInfor(mapAll, listRespone);
    }

    public void getAllPutMapInfor
            (ConcurrentHashMap<String, SimpleResponse> mapSimple, List<SimpleResponse> listStudent) {
        for (SimpleResponse student : listStudent) {
            mapSimple.put(student.getId(), student);
        }
        listStudent.clear();
    }

    private Optional<AdMemberAndRoleProjectCustomResponse> findMemberRoleByMemberId(
            List<AdMemberAndRoleProjectCustomResponse> listMemberRole, String memberId) {
        Optional<AdMemberAndRoleProjectCustomResponse> obj = listMemberRole.stream()
                .filter(i -> i.getMemberId().equals(memberId) && !memberId.equals(""))
                .findFirst();
        return obj;
    }

    @Override
    public AdDetailProjectCateMemberRespone detailUpdate(String idProject) {
        Optional<AdProjectReponse> projectFind = adProjectRepository.findOneProjectById(idProject);
        if (!projectFind.isPresent()) {
            return null;
        }
        List<AdMemberAndRoleProjectCustomResponse> listMemberRole = adMemberProjectService
                .findAllMemberJoinProject(idProject);
        List<ProjectCategory> listProjectCategory = adProjectCategoryRepository.findAllByProjectId(idProject);
        AdProjectReponse project = projectFind.get();
        AdDetailProjectCateMemberRespone objReturn = new AdDetailProjectCateMemberRespone();
        objReturn.setCode(project.getCode());
        objReturn.setName(project.getName());
        objReturn.setBackGroundImage(project.getBackGroundImage());
        objReturn.setBackGroundColor(project.getBackGroundColor());
        objReturn.setStartTime(project.getStartTime());
        objReturn.setEndTime(project.getEndTime());
        objReturn.setProgress(project.getProgress());
        objReturn.setStatusProject(project.getStatusProject());
        objReturn.setDescriptions(project.getDescriptions());
        objReturn.setListMemberRole(listMemberRole);
        objReturn.setListCategory(listProjectCategory);
        objReturn.setGroupProjectId(project.getGroupProjectId());
        return objReturn;
    }

    @Override
    public Project findProjectById(String id) {
        Optional<Project> projectCheck = adProjectRepository.findById(id);
        if (!projectCheck.isPresent()) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        return projectCheck.get();
    }

    @Override
    public Boolean removeProject(String id) {
        Optional<Project> projectCheck = adProjectRepository.findById(id);
        if (!projectCheck.isPresent()) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        adProjectRepository.delete(projectCheck.get());
        return true;
    }

    @Override
    public Boolean checkRole(String memberId, String projectId) {
        MemberProject memberProjectFind = adMemberProjectRepository.findMemberProject(memberId, projectId);
        if (memberProjectFind == null) {
            throw new RestApiException(Message.MEMBER_PROJECT_NOT_EXISTS);
        }
        List<String> listIds = adProjectRepository.getAllRoleProject(memberProjectFind.getId());
        List<RoleProject> listRoleProject = roleProjectRepository.findAllById(listIds);
        if (listRoleProject != null) {
            for (RoleProject xx : listRoleProject) {
                if (xx.getRoleDefault() == RoleDefault.DEFAULT) {
                    return true;
                }
            }
        }
        return false;
    }

}
