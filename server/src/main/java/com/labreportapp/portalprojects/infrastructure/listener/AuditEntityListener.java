package com.labreportapp.portalprojects.infrastructure.listener;

import com.labreportapp.portalprojects.entity.base.AuditEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Calendar;

/**
 * @author thangncph26123
 */

public class AuditEntityListener {

    @PrePersist
    private void onCreate(AuditEntity entity) {
        entity.setCreatedDate(getCurrentTime());
        entity.setLastModifiedDate(getCurrentTime());
    }

    @PreUpdate
    private void onUpdate(AuditEntity entity) {
        entity.setLastModifiedDate(getCurrentTime());
    }

    private long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
