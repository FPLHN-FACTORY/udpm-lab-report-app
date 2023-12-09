//package com.labreportapp.portalprojects.core.member.service.impl;
//
//import com.labreportapp.portalprojects.core.member.model.request.FindHistoryProgressBarRequest;
//import com.labreportapp.portalprojects.core.member.model.response.MeHistoryProgressResponse;
//import com.labreportapp.portalprojects.core.member.service.MeHistoryProgressService;
//import com.labreportapp.portalprojects.repository.HistoryProgressRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * @author thangncph26123
// */
//@Service
//public class MeHistoryProgressServiceImpl implements MeHistoryProgressService {
//
//    @Autowired
//    @Qualifier(HistoryProgressRepository.NAME)
//    private HistoryProgressRepository historyProgressRepository;
//
//    @Override
//    public List<MeHistoryProgressResponse> getAllHistoryProgressByIdProject(final FindHistoryProgressBarRequest request) {
//        return historyProgressRepository.getHistoryProgressByStartTimeAndEndTime(request);
//    }
//}
