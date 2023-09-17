package com.labreportapp.portalprojects.util;

import com.labreportapp.portalprojects.infrastructure.constant.RoleMemberProject;

/**
 * @author thangncph26123
 */
public class ConvertRoleMemberProject {

    public static String convert(RoleMemberProject roleMemberProject) {
        String result = "";
        switch (roleMemberProject) {
            case MANAGER:
                result = "Quản lý";
                break;
            case LEADER:
                result = "Trưởng nhóm";
                break;
            case DEV:
                result = "Developer";
                break;
            case TESTER:
                result = "Tester";
                break;
            default:
                result = "Không xác định";
                break;
        }
        return result;
    }
}
