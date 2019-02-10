package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.VoucherId;
import org.jooq.Converter;

public class VoucherIdConverter implements Converter<String, VoucherId> {

    @Override
    public VoucherId from(final String value) {
        return new VoucherId(value);
    }

    @Override
    public String to(final VoucherId voucherId) {
        return voucherId.toString();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<VoucherId> toType() {
        return VoucherId.class;
    }
}
