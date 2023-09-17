package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.member.model.request.MeCreateNotificationCommentRequest;
import jakarta.validation.Valid;

/**
 * @author thangncph26123
 */
public interface MeNotificationService {

    void createNotification(@Valid MeCreateNotificationCommentRequest request);
}
