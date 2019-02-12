package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.domain.VoucherState;
import org.jooq.DataType;
import org.jooq.impl.SQLDataType;

final class DataTypes {

    static final DataType<VoucherId> VOUCHER_ID = SQLDataType
            .CHAR(36)
            .asConvertedDataType(new VoucherIdConverter());
    static final DataType<VoucherDescription> VOUCHER_DESCRIPTION = SQLDataType
            .CHAR(250)
            .asConvertedDataType(new VoucherDescriptionConverter());
    static final DataType<VoucherState> VOUCHER_STATE = SQLDataType
            .CHAR(20)
            .asConvertedDataType(new VoucherStateConverter());

    private DataTypes() {
    }

}
