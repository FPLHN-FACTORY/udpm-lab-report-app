package com.labreportapp.portalprojects.core.admin.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdUpdateStakeholderRequest {
    private String id;
    private List<String> listProject;
}
