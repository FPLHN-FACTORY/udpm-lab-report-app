package com.labreportapp.portalprojects.entity.base;

import com.labreportapp.portalprojects.infrastructure.listener.AuditEntityListener;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

/**
 * @author thangncph26123
 */

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public abstract class AuditEntity {

    @Column(updatable = false)
    private Long createdDate;

    @Column
    private Long lastModifiedDate;

}
