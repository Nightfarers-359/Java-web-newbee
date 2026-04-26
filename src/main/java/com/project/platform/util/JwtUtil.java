package com.project.platform.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.platform.DTO.JWTpayload;
import com.project.platform.config.JwtProperties;

@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    private final String SECRET = jwtProperties.getSecret();
    private final long EXPIRE_TIME = jwtProperties.getExpireTime();
    private final String ISSUER = jwtProperties.getIssuer();

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    
    /**
     * 签发JWT
     * @param payload
     * @return JWT token
    */
    public String createToken(JWTpayload payload) {
        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put("typ", "jwt");
            headers.put("alg", "hs256");
            return JWT.create()
                    .withHeader(headers)
                    .withIssuer(ISSUER)
                    .withClaim("id", payload.getId())
                    .withClaim("isAdmin", payload.isAdmin())
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw e;
        }
    }

    /**
     * 解析JWT
     * @param token
     * @return payload
     * @throws Exception
    */
    public JWTpayload verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            JWTpayload payload = new JWTpayload();
            payload.setId(decodedJWT.getClaim("id").asInt());
            payload.setAdmin(decodedJWT.getClaim("isAdmin").asBoolean());
            return payload;

        } catch (Exception e) {
            throw e;
        }
    }

    
}
