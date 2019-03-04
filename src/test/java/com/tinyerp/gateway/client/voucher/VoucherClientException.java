package com.tinyerp.gateway.client.voucher;

import org.springframework.http.HttpStatus;

public final class VoucherClientException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Error when communicating with voucher API, status is: %s";

    public VoucherClientException(final HttpStatus statusCode) {
        super(String.format(MESSAGE_TEMPLATE, statusCode.value()));
    }
}
