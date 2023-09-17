package com.labreportapp.portalprojects.entity.base;

import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
import com.labreportapp.portalprojects.infrastructure.listener.CreatePrimaryEntityListener;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * @author thangncph26123
 */

@Getter
@Setter
@MappedSuperclass
@EntityListeners(CreatePrimaryEntityListener.class)
public abstract class PrimaryEntity extends AuditEntity
    implements IsIdentified {

    @Id
    @Column(length = EntityProperties.LENGTH_ID, updatable = false)
    private String id;

}
