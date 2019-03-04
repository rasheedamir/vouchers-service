package com.tinyerp.gateway.config.security.client;

import org.springframework.http.HttpStatus;

public interface ExceptionFactory {

    RuntimeException create(final HttpStatus httpStatus);

}
