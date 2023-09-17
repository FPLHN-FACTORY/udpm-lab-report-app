package com.labreportapp.portalprojects.util;

import com.labreportapp.portalprojects.core.member.repository.MeHistoryProgressRepository;
import com.labreportapp.portalprojects.entity.HistoryProgress;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import com.labreportapp.portalprojects.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author thangncph26123
 */
@Component
public class HistoryProgressHelper {

    @Autowired
    private MeHistoryProgressRepository meHistoryProgressRepository;

    @Autowired
    @Qualifier(ProjectRepository.NAME)
    private ProjectRepository projectRepository;

    @Autowired
    private TodoHelper todoHelper;

    public void updateHistoryProgress(String idProject) {
        Optional<Project> projectFind = projectRepository.findById(idProject);
        if(!projectFind.isPresent()){
            throw new MessageHandlingException(Message.PROJECT_NOT_EXISTS);
        }
        Long dateNow = DateTimeUtil.getCurrentDateInMillis();
        Long datePrevious = DateTimeUtil.getPreviousDayInMillis();

        HistoryProgress historyProgressFind = meHistoryProgressRepository.findByProjectIdAndProgressDate(idProject, dateNow);
        System.out.println(historyProgressFind == null);
        HistoryProgress historyProgressFindPrevious = meHistoryProgressRepository.findByProjectIdAndProgressDate(idProject, datePrevious);
        System.out.println(historyProgressFindPrevious == null);
        Float sumProgress = todoHelper.sumProgressAllTodoByProject(projectFind.get().getId());

        if (historyProgressFind == null) {
            HistoryProgress historyProgress = new HistoryProgress();
            historyProgress.setProgressTotal(sumProgress);
            historyProgress.setProjectId(idProject);
            if(historyProgressFindPrevious != null) {
                historyProgress.setProgressChange(sumProgress - historyProgressFindPrevious.getProgressTotal());
            }else {
                historyProgress.setProgressChange(0F);
            }
            historyProgress.setProgressDate(dateNow);
            meHistoryProgressRepository.save(historyProgress);
        } else {
            historyProgressFind.setProgressTotal(sumProgress);
            if(historyProgressFindPrevious != null) {
                historyProgressFind.setProgressChange(sumProgress - historyProgressFindPrevious.getProgressTotal());
            }else {
                historyProgressFind.setProgressChange(0F);
            }
            meHistoryProgressRepository.save(historyProgressFind);
        }
    }
}
