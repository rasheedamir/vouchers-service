package com.tinyerp.gateway.domain.voucher;

import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.domain.VoucherState;

import lombok.NonNull;

public final class ClaimedVoucher implements Voucher {

    private final VoucherId id;
    private final VoucherDescription description;

    ClaimedVoucher(@NonNull VoucherDescription description) {
        this.id = VoucherId.generate();
        this.description = description;
    }

    @Override
    public VoucherId getId() {
        return this.id;
    }

    @Override
    public VoucherDescription getDescription() {
        return this.description;
    }

    @Override
    public VoucherState getState() {
        return VoucherState.CLAIMED;
    }

}
