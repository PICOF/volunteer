package com.example.volunteer.Utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JWTUtil {
    private String key = "1145141919810_henghenghengaaaaaaaa";
    private SecretKey secretKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    public String getJWT(String openid,String nickname){
        return Jwts.builder()
                .setSubject("volunteer token")
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .setId(openid)
                .setAudience(nickname)
                .signWith(secretKey)
                .compact();
    }
    public String getId(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getId();
        } catch (JwtException e) {
            throw e;
        }
    }
    public String getNickname(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getAudience();
        } catch (JwtException e) {
            throw e;
        }
    }
}
