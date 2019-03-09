package io.lypsis.commons.authorization;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class TokenHelper {

    public static final Logger log = LoggerFactory.getLogger(TokenHelper.class);

    public static final String TOKEN_HEADER_PREFIX = "Bearer ";
    public static final String CLAIM_KEY_UNIQUE_ID = "id";
    public static final String CLAIM_KEY_ROLE = "role";
    public static final String CLAIM_KEY_NAME = "name";
    public static final String CLAIM_KEY_CUSTOM_CODE = "customerCode";
    public static final String CLAIM_KEY_TYPE = "typ";
    public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_CREATED = "iat";

    private TokenHelper() {}

    private static JwtBuilder initJwtClaimsToken(String subject, Date expiration, Clock defaultClock) {
        final Date issuedAt = Date.from(defaultClock.instant());
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration);
    }

    public static String buildJwtClaimsToken(String subject,
                                             Map<String, Object> claims,
                                             Date expiration,
                                             byte[] signSecret,
                                             Clock clock) {

        return initJwtClaimsToken(subject, expiration, clock)
                .setHeaderParam(TokenHelper.CLAIM_KEY_TYPE, "JWT")
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signSecret)
                .compact();

    }

    public static Optional<Claims> getClaimsFromToken(String token, byte[] signSecret) throws JwtException {

        Claims claims = null;

        try {

            claims = Jwts.parser()
                    .setSigningKey(signSecret)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (SignatureException ex) {
            log.error("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.", ex);
        }

        return Optional.ofNullable(claims);

    }

}
