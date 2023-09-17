package com.labreportapp.portalprojects.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
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
@Table(name = "activity_todo")
@AllArgsConstructor
@NoArgsConstructor
public class ActivityTodo extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_ID)
    private String memberCreatedId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String memberId;

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "idx_todoId")
    private String todoId;

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "idx_todoListId")
    // đánh vào những thuộc tính xuất hiện nhiều trong mệnh đề JOIN của câu query,
    // chỉ đánh vào những trường khóa ngoại mà thực thể của khóa ngoại đấy có nhiều bản ghi
    // đánh index bừa sẽ gây lãng phí bộ nhớ
    // Ko được đánh index vào những trường có quá nhiều kí tự
    private String todoListId;

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "idx_projectId")
    private String projectId;

    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    private String contentAction;

    @Column(length = EntityProperties.LENGTH_NAME)
    private String urlImage;

    @Column(length = EntityProperties.LENGTH_ID)
    private String imageId;
}
