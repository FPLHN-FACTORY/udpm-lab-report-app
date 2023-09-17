package com.labreportapp.labreport.infrastructure.listener;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import jakarta.persistence.PrePersist;

import java.util.UUID;

/**
 * @author thangncph26123
 */

public class CreatePrimaryEntityListener {

    @PrePersist
    private void onCreate(PrimaryEntity entity) {
        entity.setId(UUID.randomUUID().toString());
    }
}
