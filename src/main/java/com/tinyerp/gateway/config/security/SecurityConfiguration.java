package com.tinyerp.gateway.config.security;

import com.tinyerp.gateway.util.RestPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
// EnableWebSecurity will provide configuration via HttpSecurity providing the configuration you could find with <http></http> tag in xml configuration, it's allow you to configure your access based on urls patterns, the authentication endpoints, handlers etc...
@EnableWebSecurity(debug = true)
@EnableResourceServer
// EnableGlobalMethodSecurity provides AOP security on methods, some of annotation it will enable are PreAuthorize, PostAuthorize also it has support for JSR-250.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${gateway.keycloak.jwtPublicKey}")
    private String jwtPublicKey;

    @Value("${gateway.keycloak.clientId}")
    private String clientId;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final KeycloakAccessTokenConverter keycloakAccessTokenConverter;

    public SecurityConfiguration(KeycloakAccessTokenConverter keycloakAccessTokenConverter) {
        this.keycloakAccessTokenConverter = keycloakAccessTokenConverter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .headers().frameOptions().sameOrigin()
                .and()
            .headers()
                .frameOptions()
                .disable()
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
                .antMatchers(RestPaths.ACTUATOR_HEALTH, RestPaths.ACTUATOR_INFO).permitAll()
                .antMatchers(RestPaths.SWAGGER_UI).denyAll()
                .anyRequest().fullyAuthenticated(); // this line is mandatory!
    }

    /**
     * Overridden to inject overridden dependency
     *
     * @param config
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        LOGGER.debug("Running with OAuth2 Security!");
        config.tokenServices(tokenServices());
        /**
         * If you don't set this then you will get this error
         * "error_description": "Invalid token does not contain resource id (oauth2-resource)"
         *
         * In case of keycloak; the resourceId should be set to the clientId.
         */
        config.resourceId(clientId);
        // This is where you inject your custom error management
        config
            .accessDeniedHandler(accessDeniedHandler())
            .authenticationEntryPoint(authenticationEntryPoint());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(jwtPublicKey);
        converter.setAccessTokenConverter(keycloakAccessTokenConverter);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    /**
     * Declared this bean to exclude UserDetailsServiceAutoConfiguration which prints a default password on console;
     * (remove this and look in the console output)
     *
     * @param defaultTokenServices
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(final DefaultTokenServices defaultTokenServices) {
        final OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        authenticationManager.setTokenServices(defaultTokenServices);
        return authenticationManager;
    }

    /**
     * Inject your custom exception translator into the OAuth2 {@link AuthenticationEntryPoint}.
     *
     * @return AuthenticationEntryPoint
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        final OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
        entryPoint.setExceptionTranslator(exceptionTranslator());
        return entryPoint;
    }

    /**
     * Classic Spring Security stuff, defining how to handle {@link AccessDeniedException}s.
     * Inject your custom exception translator into the OAuth2AccessDeniedHandler.
     * (if you don't add this access denied exceptions may use a different format)
     *
     * @return AccessDeniedHandler
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        final OAuth2AccessDeniedHandler handler = new OAuth2AccessDeniedHandler();
        handler.setExceptionTranslator(exceptionTranslator());
        return handler;
    }

    /** Define your custom exception translator bean here */
    @Bean
    public WebResponseExceptionTranslator<?> exceptionTranslator() {
        return new ApiErrorWebResponseExceptionTranslator();
    }
}
