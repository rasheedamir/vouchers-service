package com.tinyerp.gateway.config.security;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak", ignoreUnknownFields = false)
public class KeycloakProperties {
    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
    private String userName;
    private String password;
    private String audience;
    private String jwtPublicKey;
}
