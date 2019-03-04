package com.tinyerp.gateway.config.security.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.Collections;

@Configuration
public class ClientCredentialsConfiguration {

    private static final String CLIENT_CREDENTIALS = "client_credentials";

    private final String clientSecret;
    private final String clientId;
    private final String accessTokenUri;

    public ClientCredentialsConfiguration(
            @Value("${tinyerp.service.client.secret}") final String clientSecret,
            @Value("${tinyerp.service.client.clientId}") final String clientId,
            @Value("${tinyerp.service.client.accessTokenUri}") final String accessTokenUri) {
        this.clientSecret = clientSecret;
        this.clientId = clientId;
        this.accessTokenUri = accessTokenUri;
    }

    @Bean
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        final ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri(accessTokenUri);
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setScope(Collections.emptyList());
        resourceDetails.setGrantType(CLIENT_CREDENTIALS);

        return resourceDetails;
    }
}
