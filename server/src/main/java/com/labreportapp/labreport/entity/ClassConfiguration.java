package com.labreportapp.labreport.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thangncph26123
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "class_configuration")
public class ClassConfiguration extends PrimaryEntity {

    @Column
    private Integer classSizeMax;

    @Column
    private Integer classSizeMin;

    @Column
    private Double pointMin;

    @Column
    private Double maximumNumberOfBreaks;

}
