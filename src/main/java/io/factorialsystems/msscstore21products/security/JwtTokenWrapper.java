package io.factorialsystems.msscstore21products.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public class JwtTokenWrapper {
    private static final String SYSTEM_NAME = "__system_user__";
    private static final String SYSTEM_EMAIL = "systemuser@factorialsystems.io";

    private static Map<String, Object> getClaims () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null) ? ((Jwt)authentication.getPrincipal()).getClaims() : null;
    }

    public static String getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null) ? ((Jwt)authentication.getPrincipal()).getTokenValue() : null;
    }

    public static String getUserName() {
        Map<String, Object> claims = getClaims();
        return claims != null ? (String) claims.get("name") : SYSTEM_NAME;
    }

    public static String getUserId() {
        Map<String, Object> claims = getClaims();
        return claims != null ? (String) claims.get("sub") : null;
    }

    public static String getEmail() {
        Map<String, Object> claims = getClaims();
        return claims != null ? (String) claims.get("email") : SYSTEM_EMAIL;
    }
}
