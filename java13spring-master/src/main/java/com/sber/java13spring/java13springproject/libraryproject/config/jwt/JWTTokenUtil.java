package com.sber.java13spring.java13springproject.libraryproject.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JWTTokenUtil
      implements Serializable {
    @Serial
    private static final long serialVersionUID = -2550185165626007488L;
    private static final ObjectMapper objectMapper = getDefaultObjectMapper();
    
    private static ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper();
    }
    
    //7 * 24 * 60 * 60 * 1000 = 1 неделя в миллисекундах
    public static final long JWT_TOKEN_VALIDITY = 604800000;
    
    //секрет для формирования подписи токена
    private final String secret = "zdtlD3JK56m6wTTgsNFhqzjqP";
    
    /*
    payload = { "username": "andy_gavrilov",
                "role": "USER",
                "password": "asdaey1u2g3k2hedvbqwkegrv1i2uyv"}
     */
    public String getUsernameFromToken(String token) {
        String subject = getClaimsFromToken(token, Claims::getSubject);
        JsonNode subjectJSON = null;
        try {
            subjectJSON = objectMapper.readTree(subject);
        }
        catch (JsonProcessingException e) {
            //https://www.baeldung.com/slf4j-log-exceptions
            log.error("JWTTokenUtil#getUsernameFromToken(): {}", e.getMessage());
        }
        
        if (subjectJSON != null) {
            return subjectJSON.get("username").asText();
        }
        else {return null;}
    }
    
    //Достаем роль пользователя из токена
    public String getRoleFromToken(String token) {
        String subject = getClaimsFromToken(token, Claims::getSubject);
        JsonNode subjectJSON = null;
        try {
            subjectJSON = objectMapper.readTree(subject);
        }
        catch (JsonProcessingException e) {
            //https://www.baeldung.com/slf4j-log-exceptions
            log.error("JWTTokenUtil#getRoleFromToken(): {}", e.getMessage());
        }
        
        if (subjectJSON != null) {
            return subjectJSON.get("user_role").asText();
        }
        else {return null;}
    }
    
    //получение фиксированной информации из токена
    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }
    
    //для получения любой информации из токена, необходим секретный ключ
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    //Создаем токен и кладем в него информацию о пользователе в виде .toString нашего CustomUserDetails
    public String generateToken(UserDetails userDetails) {
        return doGenerateToken(userDetails.toString());
    }
    
    //Настраиваем токен
    private String doGenerateToken(String subject) {
        return Jwts.builder()
              .setSubject(subject)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
              .signWith(SignatureAlgorithm.HS512, secret)
              .compact();
    }
    
    //Подтверждение токена
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    //Проверка, истекло ли время действия токена
    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    private Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }
}
