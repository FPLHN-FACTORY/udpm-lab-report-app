package com.labreportapp.labreport.infrastructure.security;

import com.labreportapp.labreport.infrastructure.constant.SessionConstant;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
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

import java.util.Collections;
import java.util.Date;

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
    private LabReportAppSession labReportAppSession;

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
//        String role = claims.get("role", String.class);
        String name = claims.get("name", String.class);
        String userName = claims.get("userName", String.class);
        String email = claims.get("email", String.class);
        String id = claims.get("id", String.class);

        String storedId = (String) httpSession.getAttribute(SessionConstant.USER_CURRENT_ID);
        String storedEmail = (String) httpSession.getAttribute(SessionConstant.USER_CURRENT_EMAIL);
        String storedUserName = (String) httpSession.getAttribute(SessionConstant.USER_CURRENT_USERNAME);
        String storedName = (String) httpSession.getAttribute(SessionConstant.USER_CURRENT_NAME);
        System.out.println(labReportAppSession.getUserId() + " ddddddd");
        if (id.equals(storedId)
                && email.equals(storedEmail)
                && userName.equals(storedUserName)
                && name.equals(storedName)) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("role");
            return new UsernamePasswordAuthenticationToken(null, token, Collections.singletonList(authority));
        } else {
            httpSession.setAttribute(SessionConstant.USER_CURRENT_ID, id);
            httpSession.setAttribute(SessionConstant.USER_CURRENT_EMAIL, email);
            httpSession.setAttribute(SessionConstant.USER_CURRENT_USERNAME, userName);
            httpSession.setAttribute(SessionConstant.USER_CURRENT_NAME, name);

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("role");
            return new UsernamePasswordAuthenticationToken(null, token, Collections.singletonList(authority));
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
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
