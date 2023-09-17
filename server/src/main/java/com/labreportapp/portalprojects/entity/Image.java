package com.labreportapp.portalprojects.entity;

import com.labreportapp.portalprojects.entity.base.PrimaryEntity;
import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
import com.labreportapp.portalprojects.infrastructure.constant.StatusImage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Index;

/**
 * @author thangncph26123
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
public class Image extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_NAME)
    private String nameFile;

    @Column(length = EntityProperties.LENGTH_NAME)
    private String nameImage;

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "idx_todoId")
    private String todoId;

    @Column(nullable = false)
    private StatusImage statusImage;
}
