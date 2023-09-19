package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {Comment.class})
public interface MeCommentResponse extends IsIdentified {

    @Value("#{target.content}")
    String getContent();

    @Value("#{target.member_id}")
    String getMemberId();

    @Value("#{target.status_edit}")
    Integer getStatusEdit();

    @Value("#{target.todo_id}")
    String getTodoId();

    @Value("#{target.created_date}")
    Long getCreatedDate();
}
