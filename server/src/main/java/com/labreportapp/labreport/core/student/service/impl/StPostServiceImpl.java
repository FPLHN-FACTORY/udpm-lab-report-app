package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StFindPostRequest;
import com.labreportapp.labreport.core.student.model.response.StPostResponse;
import com.labreportapp.labreport.core.student.model.response.StPostTeacherResponse;
import com.labreportapp.labreport.core.student.repository.StPostRepository;
import com.labreportapp.labreport.core.student.service.StPostService;
import com.labreportapp.labreport.util.CallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author quynhncph26201
 */
@Service
public class StPostServiceImpl implements StPostService {

    @Autowired
    private StPostRepository repository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Override
    public PageableObject<StPostTeacherResponse> searchPagePost(StFindPostRequest repquest) {
        Pageable pageable = PageRequest.of(repquest.getPage() - 1, repquest.getSize());
        Page<StPostResponse> listPage = repository.searchPostByIdClass(repquest, pageable);
        List<String> idUsers = listPage.getContent().stream()
                .map(StPostResponse::getIdTeacher)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<SimpleResponse> listTeacher = callApiIdentity.handleCallApiGetListUserByListId(idUsers);
        Page<StPostTeacherResponse> pageNew = listPage.map(item -> {
            StPostTeacherResponse objNew = new StPostTeacherResponse();
            objNew.setId(item.getId());
            objNew.setDescriptions(item.getDescriptions());
            objNew.setCreatedDate(item.getCreatedDate());
            objNew.setTeacherId(item.getIdTeacher());
            objNew.setTeacherName(null);
            objNew.setTeacherUsername(null);
            if (item.getIdTeacher() != null && listTeacher.size() != 0) {
                listTeacher.forEach(user -> {
                    if (user.getId().equals(item.getIdTeacher())) {
                        objNew.setTeacherName(user.getName());
                        objNew.setTeacherUsername(user.getUserName());
                    }
                });
            }
            return objNew;
        });
        return new PageableObject<>(pageNew);
    }
}
