package com.tinyerp.gateway.config.security;

import com.tinyerp.gateway.domain.CurrentUser;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * TODO:
 * For some reason it's not invoked! Needs to be fixed!
 * Once fixed then we can we can extract principal from Authentication like this:
 *
 * CurrentUser user = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 *
 * As per doc's it should be used if defined as a component!
 */
@Component
public class KeycloakUserExtractor implements PrincipalExtractor {

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        return CurrentUser.from(token);
    }

}
