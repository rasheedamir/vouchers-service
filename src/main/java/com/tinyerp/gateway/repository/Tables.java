package com.tinyerp.gateway.repository;

import static org.jooq.impl.DSL.table;

import org.jooq.Record;
import org.jooq.Table;

final class Tables {

    static final Table<Record> VOUCHER = table(TableNames.VOUCHER.name());

    private Tables() {
    }
}
