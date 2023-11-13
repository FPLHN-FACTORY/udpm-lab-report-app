package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.labreport.core.student.model.request.StUpdateHomeWorkAndNotebyLeadTeamRequest;
import com.labreportapp.labreport.core.student.model.response.StHomeWordAndNoteResponse;
import com.labreportapp.labreport.core.student.model.response.StMeetingResponse;
import com.labreportapp.labreport.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.labreport.core.student.repository.StHomeWorkRepository;
import com.labreportapp.labreport.core.student.repository.StLeadTeamRepository;
import com.labreportapp.labreport.core.student.repository.StMeetingRepository;
import com.labreportapp.labreport.core.student.repository.StNoteRepository;
import com.labreportapp.labreport.core.student.repository.StReportRepository;
import com.labreportapp.labreport.core.student.repository.StTeamRepository;
import com.labreportapp.labreport.core.student.service.StMeetingService;
import com.labreportapp.labreport.entity.HomeWork;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.entity.Note;
import com.labreportapp.labreport.entity.Report;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.entity.Team;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.CustomException;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author quynhncph26201
 */
@Service
public class StMeetingServiceImpl implements StMeetingService {

    @Autowired
    private StMeetingRepository stMeetingrepository;

    @Autowired
    private StReportRepository stReportRepository;

    @Autowired
    private StHomeWorkRepository stHomeWorkRepository;

    @Autowired
    private StNoteRepository stNoteRepository;

    @Autowired
    private StLeadTeamRepository stLeadTeamRepository;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private StTeamRepository stTeamRepository;

    @Override
    public List<StMeetingResponse> searchMeetingByIdClass(StFindMeetingRequest request) {
        return stMeetingrepository.findMeetingByIdClass(request);
    }

    @Override
    public Integer countMeetingByClassId(String idClass) {
        return stMeetingrepository.countMeetingByClassId(idClass);
    }

    @Override
    public StMeetingResponse searchMeetingByIdMeeting(StFindMeetingRequest request) {
        Optional<StMeetingResponse> meeting = stMeetingrepository.searchMeetingByIdMeeting(request);
        if (!meeting.isPresent()) {
            throw new CustomException(Message.MEETING_NOT_EXISTS);
        }
        if (meeting.get().getMeetingDate() > new Date().getTime()) {
            throw new RestApiException(Message.CHUA_DEN_THOI_GIAN_CUA_BUOI_HOC);
        }
        return meeting.get();
    }

    @Override
    public StHomeWordAndNoteResponse searchDetailMeetingTeamById(StFindMeetingRequest request) {
        Optional<StHomeWordAndNoteResponse> object = stMeetingrepository.searchDetailMeetingTeamByIdMeIdTeam(request);
        if (!object.isPresent()) {
            return null;
        }
        return object.get();
    }

    @Override
    public List<StMyTeamInClassResponse> getAllTeams(StFindMeetingRequest stFindStudentClasses) {
        return stMeetingrepository.getTeamInClass(stFindStudentClasses);
    }


    @Override
    @Transactional
    @Synchronized
    public StHomeWordAndNoteResponse updateDetailMeetingTeamByLeadTeam(StUpdateHomeWorkAndNotebyLeadTeamRequest request) {
        Optional<Meeting> meeting = stMeetingrepository.findById(request.getIdMeeting());
        if (!meeting.isPresent()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        Optional<StudentClasses> optionalStudentClasses = stLeadTeamRepository.findStudentClassesByStudentIdAndClassId(labReportAppSession.getUserId(), meeting.get().getClassId());
        Optional<Team> team = stTeamRepository.findById(request.getIdTeam());
        String codeClass = loggerUtil.getCodeClassByIdClass(meeting.get().getClassId());
        String nameSemester = loggerUtil.getNameSemesterByIdClass(meeting.get().getClassId());
        String nameMeeting = meeting.isPresent() ? meeting.get().getName() : "";
        String nameTeam = team.isPresent() ? team.get().getName() : "";
        StringBuilder stringHw = new StringBuilder();
        StringBuilder stringNote = new StringBuilder();
        StringBuilder stringReport = new StringBuilder();
        if (optionalStudentClasses.isPresent()) {
            StudentClasses studentClasses = optionalStudentClasses.get();
            if (studentClasses.getRole().equals(RoleTeam.LEADER)) {
                HomeWork homeWorkNew = new HomeWork();
                homeWorkNew.setMeetingId(request.getIdMeeting());
                homeWorkNew.setTeamId(request.getIdTeam());
                homeWorkNew.setDescriptions(request.getDescriptionsHomeWork());
                if (request.getIdHomeWork() != null) {
                    Optional<HomeWork> objectHW = stHomeWorkRepository.findById(request.getIdHomeWork());
                    if (objectHW.isPresent()) {
                        homeWorkNew.setId(objectHW.get().getId());
                        String homeW = "";
                        if (objectHW.get().getDescriptions() != null) {
                            homeW = objectHW.get().getDescriptions();
                        }
                        if (!request.getDescriptionsHomeWork().equals("") && homeW.equals("")) {
                            stringHw.append("Đã thêm bài tập về nhà (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsHomeWork()).append("\". ");
                        } else if (!request.getDescriptionsHomeWork().equals("") && !request.getDescriptionsHomeWork().equals(homeW)) {
                            stringHw.append("Đã cập nhật bài tập về nhà (").append(nameMeeting).append(" - ").append(nameTeam).append(") từ \"").append(homeW).append("\" thành \"").append(request.getDescriptionsHomeWork()).append("\". ");
                        } else if (request.getDescriptionsHomeWork().equals("") && !homeW.equals("")) {
                            stringHw.append("Đã xóa bài tập về nhà (").append(nameMeeting).append(" - ").append(nameTeam).append("). ");
                        }
                    }
                } else {
                    if (!request.getDescriptionsHomeWork().equals("")) {
                        stringHw.append("Đã thêm bài tập về nhà (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsHomeWork()).append("\". ");
                    }
                }
                stHomeWorkRepository.save(homeWorkNew);
                Note noteNew = new Note();
                noteNew.setMeetingId(request.getIdMeeting());
                noteNew.setTeamId(request.getIdTeam());
                noteNew.setDescriptions(request.getDescriptionsNote());
                if (request.getIdNote() != null) {
                    Optional<Note> objectNote = stNoteRepository.findById(request.getIdNote());
                    if (objectNote.isPresent()) {
                        noteNew.setId(objectNote.get().getId());
                        String note = "";
                        if (objectNote.get().getDescriptions() != null) {
                            note = objectNote.get().getDescriptions();
                        }
                        if (!request.getDescriptionsNote().equals("") && note.equals("")) {
                            stringNote.append("Đã thêm nhận xét (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsNote()).append("\". ");
                        } else if (!request.getDescriptionsNote().equals("") && !request.getDescriptionsNote().equals(note)) {
                            stringNote.append("Đã cập nhật nhận xét (").append(nameMeeting).append(" - ").append(nameTeam).append(") từ \"").append(objectNote.get().getDescriptions()).append("\" thành \"").append(request.getDescriptionsNote()).append("\". ");
                        } else if (request.getDescriptionsNote().equals("") && !note.equals("")) {
                            stringNote.append("Đã xóa nhận xét (").append(nameMeeting).append(" - ").append(nameTeam).append("). ");
                        }
                    }
                } else {
                    if (!request.getDescriptionsNote().equals("")) {
                        stringNote.append("Đã thêm nhận xét (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsNote()).append("\". ");
                    }
                }
                stNoteRepository.save(noteNew);
                Report reportNew = new Report();
                reportNew.setMeetingId(request.getIdMeeting());
                reportNew.setTeamId(request.getIdTeam());
                reportNew.setDescriptions(request.getDescriptionsReport());
                if (request.getIdReport() != null) {
                    Optional<Report> objectReport = stReportRepository.findById(request.getIdReport());
                    if (objectReport.isPresent()) {
                        reportNew.setId(objectReport.get().getId());
                        String report = "";
                        if (objectReport.get().getDescriptions() != null) {
                            report = objectReport.get().getDescriptions();
                        }
                        if (!request.getDescriptionsReport().equals("") && report.equals("")) {
                            stringReport.append("Đã thêm báo cáo (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsReport()).append("\". ");
                        } else if (!request.getDescriptionsReport().equals("") && !request.getDescriptionsReport().equals(report)) {
                            stringReport.append("Đã cập nhật báo cáo (").append(nameMeeting).append(" - ").append(nameTeam).append(") từ \"").append(objectReport.get().getDescriptions()).append("\" thành \"").append(request.getDescriptionsReport()).append("\". ");
                        } else if (request.getDescriptionsReport().equals("") && !report.equals("")) {
                            stringReport.append("Đã xóa báo cáo (").append(nameMeeting).append(" - ").append(nameTeam).append("). ");
                        }
                    }
                } else {
                    if (!request.getDescriptionsReport().equals("")) {
                        stringReport.append("Đã thêm báo báo (").append(nameMeeting).append(" - ").append(nameTeam).append(") là \"").append(request.getDescriptionsReport()).append("\". ");
                    }
                }
                stReportRepository.save(reportNew);
                loggerUtil.sendLogStreamClass(stringNote.toString() + stringHw.toString() + stringReport.toString(),
                        codeClass, nameSemester);
                StFindMeetingRequest teFind = new StFindMeetingRequest();
                teFind.setIdTeam(request.getIdTeam());
                teFind.setIdMeeting(request.getIdMeeting());
            } else {
                throw new RestApiException(Message.YOU_MUST_LEADER);
            }
        }
        StFindMeetingRequest stFind = new StFindMeetingRequest();
        stFind.setIdTeam(request.getIdTeam());
        stFind.setIdMeeting(request.getIdMeeting());
        Optional<StHomeWordAndNoteResponse> objectFind = stMeetingrepository.searchDetailMeetingTeamByIdMeIdTeam(stFind);
        if (!objectFind.isPresent()) {
            return null;
        }
        return objectFind.get();
    }

    @Override
    public Integer getRoleByIdStudent(final StFindMeetingRequest request) {
        return stMeetingrepository.getRoleByIdStudent(request);
    }

}
