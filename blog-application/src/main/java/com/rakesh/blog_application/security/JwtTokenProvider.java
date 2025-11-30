package com.rakesh.blog_application.security;

import com.rakesh.blog_application.exception.BlogApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt.expiration}")
    private long jwtExpirationInMs;

    // generate jwt token
    public String generateToken(Authentication authentication)
    {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from jwt token
    public String getUsernameFromJWT(String token)
    {
        return Jwts.parser()
                .verifyWith((SecretKey)key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // validate jwt token
    public boolean validateToken(String token)
    {
        try{
            Jwts.parser()
                    .verifyWith((SecretKey)key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException malformedJwtException){
            throw new BlogApiException("Invalid JWT token");
        }catch(ExpiredJwtException expiredJwtException){
            throw new BlogApiException("JWT token expired");
        }catch(UnsupportedJwtException unsupportedJwtException){
            throw new BlogApiException("Unsupported JWT token");
        }catch(IllegalArgumentException illegalArgumentException){
            throw new BlogApiException("JWT claims string is null or empty");
        }
    }
}
