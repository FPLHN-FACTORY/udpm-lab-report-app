package com.labreportapp.portalprojects.core.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeCreateNotificationCommentRequest {

    private List<String> listMemberId;

//    private List<String> listEmail;

    @NotBlank
    private String todoId;

    @NotBlank
    private String url;
//
//    @NotNull
//    @NotBlank
//    @NotEmpty
//    private String idUser;
//
//    @NotNull
//    @NotBlank
//    @NotEmpty
//    private String username;
}
