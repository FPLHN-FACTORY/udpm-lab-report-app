package com.labreportapp.labreport.infrastructure.security;

import com.labreportapp.labreport.infrastructure.constant.SessionConstant;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * @author thangncph26123
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        try {
            String jwtToken = extractJwtToken(request);
            HttpSession session = request.getSession();
            session.setAttribute(SessionConstant.TOKEN, jwtToken);
            response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            if (jwtToken != null) {
                String message = jwtTokenProvider.validateToken(jwtToken);
                if (message == null) {
                    throw new AccessDeniedException("UNAUTHORIZED");
                }
                if (message.isEmpty()) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                if (message.equals(Message.SESSION_EXPIRED.getMessage()) || message.equals(Message.ROLE_HAS_CHANGE.getMessage())) {
                    throw new RuntimeException(message);
                }
            } else {
                throw new AccessDeniedException("UNAUTHORIZED");
            }
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().write(e.getMessage());
        }
    }

    private String extractJwtToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}