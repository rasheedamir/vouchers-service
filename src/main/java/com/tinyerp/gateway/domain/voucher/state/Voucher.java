package com.tinyerp.gateway.domain.voucher.state;

import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.domain.VoucherState;

public interface Voucher {

    VoucherId getId();
    VoucherDescription getDescription();
    VoucherState getState();

}
