package com.tinyerp.gateway.config.security.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Component
public final class RestTemplateFactory {

    private final ClientCredentialsResourceDetails clientCredentialsResourceDetails;

    @Autowired
    public RestTemplateFactory(final ClientCredentialsResourceDetails clientCredentialsResourceDetails) {
        this.clientCredentialsResourceDetails = clientCredentialsResourceDetails;
    }

    public RestTemplate create(final String baseUrl, final ExceptionFactory exceptionFactory) {

        final DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

        final RestTemplate restTemplate = new OAuth2RestTemplate(clientCredentialsResourceDetails, clientContext);
        restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));

        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl);
        final DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(builder);
        restTemplate.setUriTemplateHandler(factory);

        final ClientErrorHandler errorHandler = new ClientErrorHandler(exceptionFactory);
        restTemplate.setErrorHandler(errorHandler);

        return restTemplate;
    }
}
