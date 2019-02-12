package com.tinyerp.gateway.mapper;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.json.request.ApiClaimVoucher;
import com.tinyerp.gateway.json.response.ApiVoucherResponse;
import org.springframework.stereotype.Service;

@Service
public final class VoucherMapper {

    public Voucher.Builder from(ApiClaimVoucher apiClaimVoucher, VoucherId voucherId) {
        return Voucher.newBuilder()
                .id(voucherId)
                .description(apiClaimVoucher.getDescription());
    }

    public ApiVoucherResponse from(Voucher voucher) {
        return ApiVoucherResponse.newBuilder()
                .id(voucher.getId())
                .description(voucher.getDescription())
                .state(voucher.getState())
                .build();
    }
}
