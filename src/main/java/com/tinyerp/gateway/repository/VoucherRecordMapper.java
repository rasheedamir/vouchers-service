package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import org.jooq.Record;
import org.jooq.RecordMapper;

public final class VoucherRecordMapper implements RecordMapper<Record, Voucher> {

    @Override
    public Voucher map(Record record) {
        final VoucherId voucherId = record.getValue(Columns.VOUCHER_ID);
        final VoucherDescription voucherDescription = record.getValue(Columns.VOUCHER_DESCRIPTION);
        return Voucher.newBuilder().id(voucherId).description(voucherDescription).build();
    }
}
