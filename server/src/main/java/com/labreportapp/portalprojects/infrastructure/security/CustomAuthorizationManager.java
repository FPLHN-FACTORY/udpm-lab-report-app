package com.labreportapp.portalprojects.infrastructure.security;//package com.labreportapp.portalprojects.infrastructure.security;
//
//import com.labreportapp.portalprojects.entity.MemberProject;
//import com.labreportapp.portalprojects.infrastructure.constant.Constants;
//import com.labreportapp.portalprojects.infrastructure.constant.RoleMemberProject;
//import com.labreportapp.portalprojects.repository.MemberProjectRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//import java.util.function.Supplier;
//
///**
// * @author thangncph26123
// */
//
//@Component
//public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
//
//    @Autowired
//    @Qualifier(MemberProjectRepository.NAME)
//    private MemberProjectRepository memberProjectRepository;
//
//    private boolean hasPermission(RequestAuthorizationContext context) {
//        HttpServletRequest request = context.getRequest();
//        String idProject = extractIdProjectFromUrl(request.getRequestURI());
//
//        MemberProject memberProjectFind = memberProjectRepository.findMemberByMemberIdAndProjectId(Constants.ID_USER, idProject);
//        if (memberProjectFind.getRole() == RoleMemberProject.MANAGER ||
//                memberProjectFind.getRole() == RoleMemberProject.LEADER) {
//            return true;
//        }
//        return false;
//    }
//
//    private String extractIdProjectFromUrl(String url) {
//        String[] parts = url.split("/");
//        if (parts.length >= 4) {
//            String lastPart = parts[parts.length - 1];
//            try {
//                UUID uuid = UUID.fromString(lastPart);
//                return uuid.toString();
//            } catch (IllegalArgumentException e) {
//                return null;
//            }
//        } else {
//            return null;
//        }
//    }
//
//
//    @Override
//    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
//        return new AuthorizationDecision(hasPermission(object));
//    }
//}
//
//
