package com.tinyerp.gateway.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tinyerp.gateway.util.CollectionsUtil;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents an authenticated user returned from Keycloak
 * The principal present in the security context
 */
@Value
public final class CurrentUser {

    public static final String CLAIM_ID = "sub";
    public static final String CLAIM_USERNAME = "preferred_username";
    public static final String CLAIM_FULL_NAME = "name";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_AUTHORITIES = "authorities";
    public static final String CLAIM_CITY = "city";
    public static final String CLAIM_DEPARTMENTS = "departments";

    private final String id;
    private final String username;
    private final String fullName;
    private final String email;
    private final List<String> departments;
    private final String city;

    private CurrentUser(String accessToken) {
        DecodedJWT jwt = JWT.decode(accessToken);
        id = jwt.getClaim(CLAIM_ID).asString();
        username = jwt.getClaim(CLAIM_USERNAME).asString();
        fullName = jwt.getClaim(CLAIM_FULL_NAME).asString();
        email = jwt.getClaim(CLAIM_EMAIL).asString();
        departments = CollectionsUtil.asSafeList(jwt.getClaim(CLAIM_DEPARTMENTS).asList(String.class));
        city = jwt.getClaim(CLAIM_CITY).asString();
    }

    private CurrentUser(Map<String, ?> claims) {
        id = (String) claims.get(CLAIM_ID);
        username = (String) claims.get(CLAIM_USERNAME);
        fullName = (String) claims.get(CLAIM_FULL_NAME);
        email = (String) claims.get(CLAIM_EMAIL);
        departments = CollectionsUtil.asSafeList((ArrayList)claims.get(CLAIM_DEPARTMENTS));
        city = (String) claims.get(CLAIM_CITY);
    }

    public static CurrentUser from(String accessToken) {
        return new CurrentUser(accessToken);
    }

    public static CurrentUser from(Map<String, ?> claims) {
        return new CurrentUser(claims);
    }
}
