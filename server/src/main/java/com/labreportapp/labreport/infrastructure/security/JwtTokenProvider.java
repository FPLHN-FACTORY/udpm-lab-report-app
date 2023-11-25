package com.labreportapp.labreport.infrastructure.security;

import com.labreportapp.labreport.infrastructure.constant.SessionConstant;
import com.labreportapp.labreport.util.AreRolesEqual;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author thangncph26123
 */
@Component
public class JwtTokenProvider {

    @Value("${identity.secretKey}")
    private String secretKey;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private CallApiIdentity callApiIdentity;

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        String name = claims.get("name", String.class);
        String userName = claims.get("userName", String.class);
        String email = claims.get("email", String.class);
        String id = claims.get("id", String.class);
        httpSession.setAttribute(SessionConstant.USER_CURRENT_ID, id);
        httpSession.setAttribute(SessionConstant.USER_CURRENT_EMAIL, email);
        httpSession.setAttribute(SessionConstant.USER_CURRENT_USERNAME, userName);
        httpSession.setAttribute(SessionConstant.USER_CURRENT_NAME, name);
        Object roleClaim = claims.get("role");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (roleClaim instanceof String) {
            authorities.add(new SimpleGrantedAuthority((String) roleClaim));
        } else if (roleClaim instanceof List<?>) {
            List<String> roleList = (List<String>) roleClaim;
            for (String role : roleList) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return new UsernamePasswordAuthenticationToken(id, token, authorities);
    }

    public String validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);

            Date expirationDate = claims.getBody().getExpiration();
            if (expirationDate.before(new Date())) {
                return Message.SESSION_EXPIRED.getMessage();
            }
            Object roleClaim = claims.getBody().get("role");
            String id = claims.getBody().get("id", String.class);
            Object response = callApiIdentity.handleCallApiGetRoleUserByIdUserAndModuleCode(id);
            if (Objects.isNull(response)) {
                return null;
            }
            if (!AreRolesEqual.compareObjects(roleClaim, response)) {
                return Message.ROLE_HAS_CHANGE.getMessage();
            }
            return "";
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}
