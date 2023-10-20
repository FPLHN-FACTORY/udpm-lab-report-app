package com.labreportapp.labreport.infrastructure.apiconstant;

/**
 * @author thangncph26123
 */
public class ApiConstants {

    public static final String API_GET_USER_BY_LIST_ID = DomainIdentityConstants.DOMAIN + "/api/UserSigns/GetDetailUsers";

    public static final String API_GET_USER_BY_ID =  DomainIdentityConstants.DOMAIN +  "/api/UserSigns/FindById";

    public static final String API_GET_ALL_USER_BY_ROLE_AND_MODULE =  DomainIdentityConstants.DOMAIN +  "/api/UserSigns/GetUserFromRoleModuleAsync";

    public static final String API_GET_ROLES_USER_BY_ID_USER_AND_MODULE_CODE =  DomainIdentityConstants.DOMAIN +  "/api/UserSigns/GetRoleFromUserModule";

    public static final String API_GET_USER_BY_EMAIL =  DomainIdentityConstants.DOMAIN +  "/api/UserSigns/FindByEmail";

}
