package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.VoucherDescription;
import org.jooq.Converter;

public class VoucherDescriptionConverter implements Converter<String, VoucherDescription> {

    @Override
    public VoucherDescription from(String value) {
        return new VoucherDescription(value);
    }

    @Override
    public String to(VoucherDescription voucherDescription) {
        return voucherDescription.toString();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<VoucherDescription> toType() {
        return VoucherDescription.class;
    }
}
