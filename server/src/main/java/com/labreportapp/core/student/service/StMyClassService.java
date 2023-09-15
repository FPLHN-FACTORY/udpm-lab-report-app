package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.request.StClassRequest;
import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.response.StMyClassResponse;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface StMyClassService {

    List<StMyClassResponse> getAllClass(final StFindClassRequest req);

    void leaveClass(final StClassRequest req);
}
