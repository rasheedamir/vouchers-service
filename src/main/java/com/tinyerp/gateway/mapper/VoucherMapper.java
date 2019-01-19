package com.tinyerp.gateway.mapper;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.json.ApiVoucher;

public final class VoucherMapper {

    public static ApiVoucher from(Voucher voucher) {
        return ApiVoucher.newBuilder()
                .id(voucher.getId())
                .description(voucher.getDescription())
                .build();
    }
}
