package com.RL.security.jwt;

import java.util.Date;

import com.RL.security.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component//baska yere enjekte edebilmek icin
public class JwtUtils {

    private static Logger logger=LoggerFactory.getLogger(JwtUtils.class); //exception;ari lolog a basmak icin

    @Value("${RL.app.jwtSecret}")//yaml da belirttik
    private String jwtSecret;

    @Value("${RL.app.jwtExpirationMs}")//yaml da belirttik
    private long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();//cuurent login olan user i aldik

        return Jwts.builder()//heryerde ayni olan yapi dependensy bunu istiyor
                .setSubject("" + (userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();//token olusturma kodu

    }

    public Long getIdFromJwtToken(String token) {//gelen token dan id yi extract ettik
        String strId = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return Long.parseLong(strId);
    }

    public boolean validateJwtToken(String token) {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token is expired {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT Token is unsupported {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("JWT Token is malformed {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error(" Invalid JWT signature {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT Token illegal args {}", e.getMessage());
        }

        return false;

    }


}