package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import org.jooq.DataType;
import org.jooq.impl.SQLDataType;

final class DataTypes {

    static final DataType<VoucherId> VOUCHER_ID = SQLDataType
            .CHAR(36)
            .asConvertedDataType(new VoucherIdConverter());
    static final DataType<VoucherDescription> VOUCHER_DESCRIPTION = SQLDataType
            .CHAR(250)
            .asConvertedDataType(new VoucherDescriptionConverter());

    private DataTypes() {
    }

}
