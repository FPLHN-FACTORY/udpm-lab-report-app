package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.service.AdTeacherService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.infrastructure.apiconstant.ActorConstants;
import com.labreportapp.labreport.util.CallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@Service
public class AdTeacherServiceImpl implements AdTeacherService {

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Override
    public List<SimpleResponse> getAllTeacher() {
        List<SimpleResponse> response = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_TEACHER);
        return response;
    }

    @Override
    public List<SimpleResponse> getAllTeacherDashBoard(String name) {
        List<SimpleResponse> response = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_TEACHER);
        if (name.isEmpty()) {
            return response;
        }
        List<SimpleResponse> results = response.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return results;
    }

}
