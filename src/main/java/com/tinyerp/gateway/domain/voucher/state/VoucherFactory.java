package com.tinyerp.gateway.domain.voucher.state;

import com.tinyerp.gateway.domain.VoucherDescription;

public final class VoucherFactory {

    public static ClaimedVoucher newVoucher(VoucherDescription voucherDescription) {
        return new ClaimedVoucher(voucherDescription);
    }

}
