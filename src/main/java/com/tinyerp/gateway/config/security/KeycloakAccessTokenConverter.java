package com.tinyerp.gateway.config.security;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KeycloakAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        KeycloakAuthentication keycloakAuthentication = new KeycloakAuthentication(authentication.getOAuth2Request(), authentication.getUserAuthentication(), claims);
        // all claims are stored; and later can be extracted as per needs
        keycloakAuthentication.setDetails(claims);
        return keycloakAuthentication;
    }

}
