package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.VoucherState;
import org.jooq.Converter;

public class VoucherStateConverter implements Converter<String, VoucherState> {

    @Override
    public VoucherState from(String s) {
        return VoucherState.valueOf(s);
    }

    @Override
    public String to(VoucherState voucherState) {
        return voucherState.name();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<VoucherState> toType() {
        return VoucherState.class;
    }
}
