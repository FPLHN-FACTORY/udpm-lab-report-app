package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.response.StMyClassResponse;
import com.labreportapp.core.student.repository.StMyClassRepository;
import com.labreportapp.core.student.service.StMyClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
public class StMyClassServiceImpl implements StMyClassService {

    @Autowired
    private StMyClassRepository stMyClassRepository;

    @Override
    public List<StMyClassResponse> getAllClass(final StFindClassRequest req) {
        return stMyClassRepository.getAllClass(req);
    }
}
