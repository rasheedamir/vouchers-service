package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import org.jooq.Field;

import static org.jooq.impl.DSL.field;

final class Columns {

    // Voucher Table
    static final Field<VoucherId> VOUCHER_ID = field(ColumnNames.ID.name(), DataTypes.VOUCHER_ID);
    static final Field<VoucherDescription> VOUCHER_DESCRIPTION = field(ColumnNames.DESCRIPTION.name(), DataTypes.VOUCHER_DESCRIPTION);

    static final Field[] ALL = {VOUCHER_ID, VOUCHER_DESCRIPTION};

    private Columns() {
    }
}
