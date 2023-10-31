package com.labreportapp.labreport.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author hieundph25894 - duchieu212
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdObjListStatisticsResponse {

    private Integer countGroupProject;

    private Integer countProjectNotStart;

    private Integer countProjectStarting;

    private Integer countProjectEnding;

    List<AdProjectProgressResponse> listProgress;

    List<AdProjectTaskResponse> listTasks;

}
