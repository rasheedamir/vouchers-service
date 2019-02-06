package com.tinyerp.gateway.config.security;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component // Creates a Spring bean of configuration stereotype.
@ConfigurationProperties(prefix = "gateway.keycloak", ignoreUnknownFields = false) // Used to bind a class with an externalized property file. Very powerful and must be used to separate out bean classes with configuration entity class.
@EnableConfigurationProperties // Creates a binding between a configuration entity class and Spring configuration stereotype so that after injection within a service properties can be retrieved easily.
public class GatewayKeycloakProperties {
    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
    private String jwtPublicKey;
}
