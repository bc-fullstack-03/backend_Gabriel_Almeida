package com.gabriel.socialMedia.Service.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTServiceImpl implements JwtService {

    private final long EXPIRATION_TIME = 7200000;
    private final String KEY = "34743777217A25432A462D4A614E645267556B58703273357638782F413F4428";

    public String generateToken(UUID userId) {
        return Jwts
                .builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(genSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidToken(String token, String userId) {
        var claims = Jwts
                .parserBuilder()
                .setSigningKey(genSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        var sub = claims.getSubject();
        var tExpiration = claims.getExpiration();

        return (sub.equals(userId) && !tExpiration.before(new Date()));
    }

    private Key genSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
    }
}
