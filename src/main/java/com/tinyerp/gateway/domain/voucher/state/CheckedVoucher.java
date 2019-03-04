package com.tinyerp.gateway.domain.voucher.state;

import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.domain.VoucherState;
import lombok.NonNull;

public final class CheckedVoucher implements Voucher {

    private final ClaimedVoucher claimedVoucher;

    CheckedVoucher(@NonNull ClaimedVoucher claimedVoucher) {
        this.claimedVoucher = claimedVoucher;
    }

    @Override
    public VoucherId getId() {
        return claimedVoucher.getId();
    }

    @Override
    public VoucherDescription getDescription() {
        return claimedVoucher.getDescription();
    }

    @Override
    public VoucherState getState() {
        return VoucherState.CHECKED;
    }
}
