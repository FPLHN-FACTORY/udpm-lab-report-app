package com.labreportapp.portalprojects.util;

import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.repository.MeHistoryProgressRepository;
import com.labreportapp.portalprojects.entity.Assign;
import com.labreportapp.portalprojects.entity.HistoryProgress;
import com.labreportapp.portalprojects.entity.Notification;
import com.labreportapp.portalprojects.entity.NotificationMember;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.entity.Todo;
import com.labreportapp.portalprojects.infrastructure.configemail.EmailSender;
import com.labreportapp.portalprojects.infrastructure.constant.StatusReminder;
import com.labreportapp.portalprojects.repository.AssignRepository;
import com.labreportapp.portalprojects.repository.NotificationMemberRepository;
import com.labreportapp.portalprojects.repository.NotificationRepository;
import com.labreportapp.portalprojects.repository.PeriodRepository;
import com.labreportapp.portalprojects.repository.ProjectRepository;
import com.labreportapp.portalprojects.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author thangncph26123
 */

@Component
public class ScheduleDaily {

    @Autowired
    @Qualifier(ProjectRepository.NAME)
    private ProjectRepository projectRepository;

    @Autowired
    @Qualifier(PeriodRepository.NAME)
    private PeriodRepository periodRepository;

    @Autowired
    @Qualifier(TodoRepository.NAME)
    private TodoRepository todoRepository;

    @Autowired
    private TodoHelper todoHelper;

    @Autowired
    @Qualifier(AssignRepository.NAME)
    private AssignRepository assignRepository;

    @Autowired
    @Qualifier(NotificationRepository.NAME)
    private NotificationRepository notificationRepository;

    @Autowired
    @Qualifier(NotificationMemberRepository.NAME)
    private NotificationMemberRepository notificationMemberRepository;

    @Autowired
    private MeHistoryProgressRepository meHistoryProgressRepository;

    @Autowired
    private EmailSender emailSender;

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ScheduleDaily(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void dailyChecking() {
        projectRepository.updateStatusProject();
        periodRepository.updateStatusPeriod();
        todoRepository.updateStatusTodoOverDueDate();

        List<Todo> todosFind = todoRepository.getAllTodoReminder();
        List<String> listStrMemberId = new ArrayList<>();
        for (Todo item : todosFind) {
            if (item.getReminderTime() < new Date().getTime()
                    && item.getStatusReminder() == StatusReminder.CHUA_GUI) {
                List<Assign> listAssign = assignRepository.getAllAssignByIdTodo(item.getId());
                String periodId = todoRepository.getPeriodByIdTodo(item.getId());
                String projectId = todoRepository.getProjectByIdTodo(item.getId());
                Notification notification = new Notification();
                notification.setTodoId(item.getId());
                notification.setMemberIdCreated(null);
                notification.setContent("Đầu việc " + item.getName() +
                        " mà bạn đang tham gia sẽ hết hạn vào lúc " +
                        DateConverter.convertDateToStringMail(item.getDeadline()));
                notification.setUrl("/detail-project/" + projectId
                        + "?idPeriod=" + periodId + "&idTodo=" + item.getId());
                Notification newNotification = notificationRepository.save(notification);

                List<NotificationMember> notificationMemberList = new ArrayList<>();

                for (Assign assign : listAssign) {
                    String memberId = assign.getMemberId();
                    NotificationMember notificationMember = new NotificationMember();
                    notificationMember.setMemberId(memberId);
                    notificationMember.setNotificationId(newNotification.getId());
                    notificationMember.setStatus(0);
                    notificationMemberList.add(notificationMember);
                    if (!listStrMemberId.contains(memberId)) {
                        listStrMemberId.add(memberId);
                    }
                }
                notificationMemberRepository.saveAll(notificationMemberList);

                String[] emailArray = listAssign.stream()
                        .map(Assign::getEmail)
                        .toArray(String[]::new);
                String emailContent =
                        """
                                <html><body> 
                                <p><span style=\"font-size: 20px;\">Xin chào!</span> Đây là một email 
                                nhắc nhở về đầu việc <span style=\"color: red;\"><b>
                                """
                                + item.getName() +
                                """
                                        </b></span> mà bạn đang tham gia.</p>
                                        <p>Chúng tôi xin nhắc nhở rằng ngày hạn của đầu việc 
                                        này sẽ sắp đến trong thời gian tới. 
                                        Vì vậy, chúng tôi đề nghị bạn hoàn thành và 
                                        gửi báo cáo/trạng thái của đầu việc trước ngày hạn.</p>
                                        <p>Thông tin chi tiết về đầu việc:</p>
                                        <ul>
                                        <li>Tên đầu việc: <span style=\"color: red;\"><b>
                                        """
                                + item.getName() + "</b></span></li>" +
                                "<li>Ngày hạn: <span style=\"color: red;\"><b>" +
                                DateConverter.convertDateToStringMail(item.getDeadline()) + "</b></span></li>" +
                                """
                                        </ul>
                                        <p>Vui lòng đảm bảo rằng bạn đã hoàn thành công việc và gửi báo cáo 
                                        đúng hạn để đảm bảo tiến độ và chất lượng của dự án.</p>
                                        <p>Nếu bạn có bất kỳ câu hỏi hoặc cần hỗ trợ bổ sung, xin vui lòng 
                                        liên hệ với chúng tôi.</p>
                                        <p>Xin cảm ơn sự cống hiến và đóng góp của bạn cho dự án!</p>
                                        <p>Trân trọng,</p>
                                        <p>- Portal Projects -</p>
                                        </body></html>
                                        """;
                Runnable emailTask = () -> {
                    emailSender.sendEmail(emailArray, "Portal Projects xin thông báo",
                            "Thông báo đầu việc sắp hết hạn", emailContent);
                };
                new Thread(emailTask).start();
                todoRepository.updateStatusReminder(item.getId());
            }
        };
        for (String str : listStrMemberId) {
            messagingTemplate.convertAndSend("/portal-projects/create-notification/" + str, new ResponseObject(true));
        }
    }

    @Scheduled(cron = "0 5 * * * *")
    public void dailyCheckingHistoryProgress() {
        Long dateNow = DateTimeUtil.getCurrentDateInMillis();
        List<Project> listProject = projectRepository.findAll();
        List<HistoryProgress> listHistoryProgress = new ArrayList<>();
        listProject.forEach(project -> {
            HistoryProgress historyProgressFind = meHistoryProgressRepository.findByProjectIdAndProgressDate(project.getId(), dateNow);
            if (historyProgressFind == null) {
                HistoryProgress historyProgress = new HistoryProgress();
                historyProgress.setProgressTotal(todoHelper.sumProgressAllTodoByProject(project.getId()));
                historyProgress.setProjectId(project.getId());
                historyProgress.setProgressDate(dateNow);
                historyProgress.setProgressChange(0F);
                listHistoryProgress.add(historyProgress);
            }
        });
        meHistoryProgressRepository.saveAll(listHistoryProgress);
    }
}
