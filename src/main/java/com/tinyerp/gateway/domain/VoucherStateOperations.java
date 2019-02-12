package com.tinyerp.gateway.domain;

public interface VoucherStateOperations {

    VoucherState claim(Voucher voucher);

    VoucherState check(Voucher voucher);
}
