package com.tinyerp.gateway.client.voucher;

import com.tinyerp.gateway.config.security.client.RestTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class VoucherRestTemplateConfig {

    private final RestTemplateFactory restTemplateFactory;

    @Autowired
    public VoucherRestTemplateConfig(final RestTemplateFactory restTemplateFactory) {
        this.restTemplateFactory = restTemplateFactory;
    }

    @Bean
    RestTemplate voucherRestTemplate(
            @Value("${tinyerp.voucher.endPoint}") final String baseUrl) {
        return restTemplateFactory.create(baseUrl, VoucherClientException::new);
    }
}
