package com.tinyerp.gateway.rest;

import com.tinyerp.gateway.domain.CurrentUser;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    CurrentUser getUser();
}
