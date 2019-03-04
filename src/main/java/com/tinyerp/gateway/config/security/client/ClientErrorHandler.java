package com.tinyerp.gateway.config.security.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class ClientErrorHandler extends DefaultResponseErrorHandler {

    private final ExceptionFactory exceptionFactory;

    public ClientErrorHandler(final ExceptionFactory exceptionFactory) {
        this.exceptionFactory = exceptionFactory;
    }

    @Override
    protected void handleError(
            final ClientHttpResponse response,
            final HttpStatus statusCode)  {
        throw exceptionFactory.create(statusCode);
    }
}
