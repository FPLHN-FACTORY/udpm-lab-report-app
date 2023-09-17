package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.portalprojects.core.member.model.request.MeCreateMemberProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeListMemberProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateMemberProjectRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeMemberProjectResponse;
import com.labreportapp.portalprojects.core.member.repository.MeMemberProjectRepository;
import com.labreportapp.portalprojects.core.member.repository.MeNotificationMemberRepository;
import com.labreportapp.portalprojects.core.member.repository.MeNotificationRepository;
import com.labreportapp.portalprojects.core.member.repository.MeProjectRepository;
import com.labreportapp.portalprojects.core.member.service.MeMemberProjectService;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.infrastructure.configemail.EmailSender;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.RoleMemberProject;
import com.labreportapp.portalprojects.infrastructure.constant.StatusWork;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeMemberProjectServiceImpl implements MeMemberProjectService {

    @Autowired
    private MeMemberProjectRepository meMemberProjectRepository;

    @Autowired
    private SuccessNotificationSender successNotificationSender;

    @Autowired
    private MeProjectRepository meProjectRepository;

    @Autowired
    private MeNotificationRepository meNotificationRepository;

    @Autowired
    private MeNotificationMemberRepository meNotificationMemberRepository;

    @Autowired
    private EmailSender emailSender;

    @Override
    @Cacheable(value = "membersByProject", key = "#idProject")
    public List<MeMemberProjectResponse> getAllMemberProject(String idProject) {
        return meMemberProjectRepository.getAllMemberProject(idProject);
    }

    @Override
    @Transactional
    @Synchronized
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public MemberProject update(@Valid MeUpdateMemberProjectRequest request, StompHeaderAccessor headerAccessor) {
        try {
            MemberProject memberProject = meMemberProjectRepository.findMemberProject(request.getIdProject(), request.getIdMember());
            if (memberProject == null) {
                throw new MessageHandlingException(Message.MEMBER_PROJECT_NOT_EXISTS);
            }
            memberProject.setRole(RoleMemberProject.values()[request.getRole()]);
            memberProject.setStatusWork(StatusWork.values()[request.getStatusWork()]);
            successNotificationSender.senderNotification(ConstantMessageSuccess.CAP_NHAT_THANH_CONG, headerAccessor);
            return meMemberProjectRepository.save(memberProject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Synchronized
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public List<MemberProject> create(@Valid MeListMemberProjectRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Project> projectFind = meProjectRepository.findById(request.getListMemberProject().get(0).getProjectId());
        if (!projectFind.isPresent()) {
            throw new MessageHandlingException(Message.PROJECT_NOT_EXISTS);
        }
        List<MemberProject> listMemberProject = new ArrayList<>();
        for (MeCreateMemberProjectRequest xx : request.getListMemberProject()) {
            MemberProject memberProject = new MemberProject();
            memberProject.setMemberId(xx.getMemberId());
            memberProject.setProjectId(xx.getProjectId());
            memberProject.setEmail(xx.getEmail());
            memberProject.setStatusWork(StatusWork.DANG_LAM);
            memberProject.setRole(RoleMemberProject.values()[xx.getRole()]);
            listMemberProject.add(memberProject);
        }
        meMemberProjectRepository.saveAll(listMemberProject);

//        int atIndex = request.getEmail().indexOf("@");
//        String username = "";
//        if (atIndex != -1) {
//            username = request.getEmail().substring(0, atIndex);
//        }
//
//        Notification notification = new Notification();
//        notification.setContent("Chúc mừng " + username + ", bạn đã được tham gia dự án " + projectFind.get().getName());
//        notification.setUrl("#/detail-project/" + request.getProjectId());
//        Notification newNotification = meNotificationRepository.save(notification);
//
//        NotificationMember notificationMember = new NotificationMember();
//        notificationMember.setMemberId(request.getMemberId());
//        notificationMember.setNotificationId(newNotification.getId());
//        notificationMember.setStatus(0);
//        meNotificationMemberRepository.save(notificationMember);

//        String[] arrayEmail = new String[1];
//        arrayEmail[0] = request.getEmail();
//        String contentEmail = """
//                <p>Xin chào""" + " " + username + """
//                ,</p>
//                <p>Đây là email thông báo rằng bạn đã được chọn để tham gia vào
//                một dự án của xưởng dự án của Bộ môn Phát Triển Phần Mềm.
//                Chúng tôi rất vui mừng và tin tưởng
//                rằng sự đóng góp của bạn sẽ góp phần làm nên thành công của dự án này.</p>
//                <p><strong>Dự án: </strong><span style=\"color: red;\"><b>
//                """ + projectFind.get().getName() + """
//                </b></span></p>
//                <p><strong>Thời gian bắt đầu: </strong><span style=\"color: red;\"><b>""" +
//                DateConverter.convertDateToStringMail(projectFind.get().getStartTime()) + """
//                </b></span></p>
//                <p><strong>Thời gian kết thúc dự kiến: </strong><span style=\"color: red;\"><b>""" +
//                DateConverter.convertDateToStringMail(projectFind.get().getEndTime()) + """
//                </b></span></p>
//                <p><strong>Vai trò của bạn trong dự án là: </strong></strong><span style=\"color: red;\"><b>""" +
//                ConvertRoleMemberProject.convert(newMemberProject.getRole()) + """
//                </b></span></p>
//                <p>Trân trọng,</p>
//                <p>- Portal Projects -</p>
//                """;
//        Thread emailThread = new Thread(() -> {
//            emailSender.sendEmail(arrayEmail, "Portal Projects xin thông báo",
//                    "Thông báo về việc tham gia dự án", contentEmail);
//        });
//        emailThread.start();
        successNotificationSender.senderNotification(ConstantMessageSuccess.THEM_THANH_CONG, headerAccessor);
        return listMemberProject;
    }
}
