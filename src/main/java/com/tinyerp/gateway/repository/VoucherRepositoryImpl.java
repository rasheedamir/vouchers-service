package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.domain.VoucherState;
import com.tinyerp.gateway.exception.VoucherNotFoundException;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoucherRepositoryImpl implements VoucherRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherRepositoryImpl.class);

    private final DSLContext jooq;

    @Autowired
    public VoucherRepositoryImpl(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public Voucher add(Voucher voucherEntry) {
        LOGGER.info("Creating voucher : {}", voucherEntry);
        jooq.insertInto(Tables.VOUCHER)
                .set(Columns.VOUCHER_ID, voucherEntry.getId())
                .set(Columns.VOUCHER_DESCRIPTION, voucherEntry.getDescription())
                .set(Columns.VOUCHER_STATE, voucherEntry.getState())
                .execute();
        // Seems redundant! Can't we just return the one we received
        return findById(voucherEntry.getId());
    }

    @Override
    public Voucher delete(VoucherId id) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Voucher findById(VoucherId voucherId) {
        return jooq
                .select(Columns.ALL)
                .from(Tables.VOUCHER)
                .where(Columns.VOUCHER_ID.eq(voucherId))
                .fetchOptional(new VoucherRecordMapper())
                .orElseThrow(() -> new VoucherNotFoundException(voucherId));
    }

    @Override
    public Page<Voucher> findBySearchTerm(String searchTerm, Pageable pageable) {
        return null;
    }

    @Override
    public Voucher update(Voucher voucherEntry) {
        LOGGER.info("Updating voucher : {}", voucherEntry);
        jooq.update(Tables.VOUCHER)
                .set(Columns.VOUCHER_DESCRIPTION, voucherEntry.getDescription())
                .set(Columns.VOUCHER_STATE, voucherEntry.getState())
                .where(Columns.VOUCHER_ID.eq(voucherEntry.getId()))
                .execute();
        return findById(voucherEntry.getId());
    }

    @Override
    public Page<Voucher> findByCurrentState(VoucherState state, Pageable pageable) {
        return null;
    }
}
