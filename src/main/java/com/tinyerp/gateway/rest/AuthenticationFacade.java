package com.tinyerp.gateway.rest;

import com.tinyerp.gateway.domain.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * To fully leverage the Spring dependency injection and be able to retrieve the authentication everywhere, not just
 * in @Controller beans, we need to hide the static access behind a simple facade:
 *
 * The facade exposes the Authentication object while hiding the static state and keeping the code decoupled and
 * fully testable:
 */
@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public CurrentUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        return CurrentUser.from(token);
    }
}
