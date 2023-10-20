package com.labreportapp.labreport.infrastructure.security;

import com.labreportapp.labreport.infrastructure.constant.SessionConstant;
import com.labreportapp.labreport.util.AreRolesEqual;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.CustomException;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
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
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

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

        String storedId = (String) httpSession.getAttribute(SessionConstant.USER_CURRENT_ID);
        String storedEmail = (String) httpSession.getAttribute(SessionConstant.USER_CURRENT_EMAIL);
        String storedUserName = (String) httpSession.getAttribute(SessionConstant.USER_CURRENT_USERNAME);
        String storedName = (String) httpSession.getAttribute(SessionConstant.USER_CURRENT_NAME);

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
        if (id.equals(storedId)
                && email.equals(storedEmail)
                && userName.equals(storedUserName)
                && name.equals(storedName)) {
            return new UsernamePasswordAuthenticationToken(null, token, authorities);
        } else {
            httpSession.setAttribute(SessionConstant.USER_CURRENT_ID, id);
            httpSession.setAttribute(SessionConstant.USER_CURRENT_EMAIL, email);
            httpSession.setAttribute(SessionConstant.USER_CURRENT_USERNAME, userName);
            httpSession.setAttribute(SessionConstant.USER_CURRENT_NAME, name);

            return new UsernamePasswordAuthenticationToken(id, token, authorities);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);

            Date expirationDate = claims.getBody().getExpiration();
            if (expirationDate.before(new Date())) {
                throw new RestApiException(Message.INVALID_TOKEN);
            }
            Object roleClaim = claims.getBody().get("role");
            String id = claims.getBody().get("id", String.class);
            Object response = convertRequestCallApiIdentity.handleCallApiGetRoleUserByIdUserAndModuleCode(id);
            if (!AreRolesEqual.compareObjects(roleClaim, response)) {
                throw new CustomException(Message.ROLE_USER_CHANGE);
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new RestApiException(Message.INVALID_TOKEN);
        }
    }
}
