package com.labreportapp.portalprojects.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdCreateGroupProjectRequest {

    @NotBlank
    private String name;

    private String descriptions;

    private MultipartFile file;
}
