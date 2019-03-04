package com.tinyerp.gateway.client.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VoucherClient {

    private final RestTemplate voucherRestTemplate;

    @Autowired
    public VoucherClient(final RestTemplate voucherRestTemplate) {
        this.voucherRestTemplate = voucherRestTemplate;
    }

}
