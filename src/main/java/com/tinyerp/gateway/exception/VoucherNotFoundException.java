package com.tinyerp.gateway.exception;

import com.tinyerp.gateway.domain.VoucherId;

public class VoucherNotFoundException extends RuntimeException {

    public VoucherNotFoundException(VoucherId id) {
        super(id.toString());
    }
}
