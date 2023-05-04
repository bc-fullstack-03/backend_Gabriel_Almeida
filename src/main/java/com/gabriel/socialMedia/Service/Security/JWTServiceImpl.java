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
public class JWTServiceImpl implements  JwtService{

    private final long  EXPIRATION_TIME = 7200000;
    private final String KEY = "%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQe\n";

    public String generateToken(UUID userId){
        return Jwts
                .builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(genSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key genSignKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
    }
}
