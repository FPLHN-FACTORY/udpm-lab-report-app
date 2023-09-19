package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.ActivityTodo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {ActivityTodo.class})
public interface MeActivityResponse extends IsIdentified {

    @Value("#{target.member_created_id}")
    String getMemberCreatedId();

    @Value("#{target.member_id}")
    String getMemberId();

    @Value("#{target.project_id}")
    String getProjectId();

    @Value("#{target.todo_id}")
    String getTodoId();

    @Value("#{target.todo_list_id}")
    String getTodoListId();

    @Value("#{target.content_action}")
    String getContentAction();

    @Value("#{target.url_image}")
    String getUrlImage();

    @Value("#{target.created_date}")
    Long getCreatedDate();
}
