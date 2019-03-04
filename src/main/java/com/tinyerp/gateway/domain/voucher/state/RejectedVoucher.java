package com.tinyerp.gateway.domain.voucher.state;

import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.domain.VoucherState;

public final class RejectedVoucher implements Voucher {

    @Override
    public VoucherId getId() {
        return null;
    }

    @Override
    public VoucherDescription getDescription() {
        return null;
    }

    @Override
    public VoucherState getState() {
        return null;
    }
}
