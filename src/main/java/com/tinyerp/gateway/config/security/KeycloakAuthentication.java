package com.tinyerp.gateway.config.security;

import com.tinyerp.gateway.domain.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.Map;

public class KeycloakAuthentication extends OAuth2Authentication {

    private final CurrentUser currentUser;

    /**
     * Construct an OAuth 2 authentication. Since some grant types don't require user authentication, the user
     * authentication may be null.
     *
     * @param storedRequest      The authorization request (must not be null).
     * @param userAuthentication The user authentication (possibly null).
     */
    public KeycloakAuthentication(OAuth2Request storedRequest, Authentication userAuthentication, Map<String, ?> claims) {
        super(storedRequest, userAuthentication);
        this.currentUser = CurrentUser.from(claims);
    }

    /**
     * Had to override it so, that in resource methods we can do;
     *
     * @AuthenticationPrincipal CurrentUser currentUser
     *
     * Because you have @AuthenticationPrincipal Authentication authentication than ServletRequestMethodArgumentResolver
     * resolves it instead of AuthenticationPrincipalArgumentResolver. NOTE: @AuthenticationPrincipal is meant to resolve
     * Authentication.getPrincipal() - see javadoc. Given that you're trying to resolve Authentication than
     * ServletRequestMethodArgumentResolver will attempt to resolve it. Try changing the method argument to
     * @AuthenticationPrincipal Object principal and put a breakpoint in AuthenticationPrincipalArgumentResolver.resolveArgument()
     * and you will see that it calls OAuth2Authentication.getPrincipal() which returns null.
     *
     * Try this…in CustomAccessTokenConverter.extractAuthentication() modify the method to return a new instance of
     * OAuth2Authentication starting from a copy of super.extractAuthentication(claims) but modify the attributes you
     * want, e.g. principal -> CurrentUser. Do this and @AuthenticationPrincipal CurrentUser user should work
     *
     * You can extend OAuth2Authentication and override getPrincipal
     *
     * @return
     */
    @Override
    public Object getPrincipal() {
        return this.currentUser;
    }

}
