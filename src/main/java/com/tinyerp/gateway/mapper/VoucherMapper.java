package com.tinyerp.gateway.mapper;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.json.request.ApiVoucherRequest;
import com.tinyerp.gateway.json.response.ApiVoucherResponse;
import org.springframework.stereotype.Service;

@Service
public final class VoucherMapper {

    public Voucher from(ApiVoucherRequest apiVoucherRequest, Long voucherId) {
        return Voucher.newBuilder()
                .id(voucherId)
                .description(apiVoucherRequest.getDescription())
                .build();
    }

    public ApiVoucherResponse from(Voucher voucher) {
        return ApiVoucherResponse.newBuilder()
                .id(voucher.getId())
                .description(voucher.getDescription())
                .build();
    }
}
