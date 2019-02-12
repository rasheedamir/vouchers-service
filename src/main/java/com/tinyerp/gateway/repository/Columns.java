package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.domain.VoucherState;
import org.jooq.Field;

import static org.jooq.impl.DSL.field;

final class Columns {

    // Voucher Table
    static final Field<VoucherId> VOUCHER_ID = field(ColumnNames.ID.name(), DataTypes.VOUCHER_ID);
    static final Field<VoucherDescription> VOUCHER_DESCRIPTION = field(ColumnNames.DESCRIPTION.name(), DataTypes.VOUCHER_DESCRIPTION);
    static final Field<VoucherState> VOUCHER_STATE = field(ColumnNames.STATE.name(), DataTypes.VOUCHER_STATE);

    static final Field[] ALL = {VOUCHER_ID, VOUCHER_DESCRIPTION, VOUCHER_STATE};

    private Columns() {
    }
}
