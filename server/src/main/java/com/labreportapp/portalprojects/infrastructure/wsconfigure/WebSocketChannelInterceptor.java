package com.labreportapp.portalprojects.infrastructure.wsconfigure;

import com.labreportapp.labreport.infrastructure.constant.SessionConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    @Value("${identity.secretKey}")
    private String secretKey;

    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try {
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
            System.out.println(accessor.getCommand());
            if (StompCommand.SEND.equals(accessor.getCommand())) {
                String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    String jwtToken = authorizationHeader.substring(7);
                    if (jwtToken != null) {
                        if (!validateToken(jwtToken, accessor.getSessionId())) {

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    public boolean validateToken(String token, String session) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);

            Date expirationDate = claims.getBody().getExpiration();
            if (expirationDate.before(new Date())) {
                return false;
            }

            String name = claims.getBody().get("name", String.class);
            String userName = claims.getBody().get("userName", String.class);
            String email = claims.getBody().get("email", String.class);
            String id = claims.getBody().get("id", String.class);

            webSocketSessionManager.storeSessionInfo(session, token, name, userName, email, id);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}

