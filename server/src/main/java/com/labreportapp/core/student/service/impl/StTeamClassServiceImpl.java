package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.core.student.model.response.StMyStudentTeamResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.core.student.repository.StMyClassRepository;
import com.labreportapp.core.student.service.StTeamClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Service
public class StTeamClassServiceImpl implements StTeamClassService {
    @Autowired
    private StMyClassRepository repository;

    @Override
    public List<StMyTeamInClassResponse> getTeamInClassByIdStudent(FindTeamClassRequest req) {
        return repository.getTeamByIdClassAndIdStudent(req);
    }

    @Override
    public List<StMyStudentTeamResponse> getMyStudentTeam(FindTeamClassRequest req) {
        return repository.getTeamByIdAll(req);
    }

    @Override
    public List<StMyTeamInClassResponse> getTeamStNotJoin(FindTeamClassRequest req) {
        return repository.getTeamByStNotJoin(req);
    }
}
