package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.domain.VoucherState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoucherRepository {

    /**
     * Adds a new voucher entry.
     * @param voucherEntry  The information of the added voucher entry.
     * @return  The added voucher entry.
     */
    Voucher add(Voucher voucherEntry);

    /**
     * Deletes a voucher entry.
     * @param id    The id of the deleted voucher entry.
     * @return  The deleted voucher entry.
     * @throws com.tinyerp.gateway.exception.VoucherNotFoundException If the deleted voucher entry is not found.
     */
    Voucher delete(VoucherId id);

    /**
     * Finds all voucher entries.
     * @return  Found voucher entries.
     */
    List<Voucher> findAll();

    /**
     * Finds a voucher entry.
     * @param id    The id of the requested voucher entry.
     * @return  The found voucher entry.
     * @throws com.tinyerp.gateway.exception.VoucherNotFoundException If voucher entry is not found.
     */
    Voucher findById(VoucherId id);

    Page<Voucher> findByCurrentState(VoucherState state, Pageable pageable);

    /**
     * Finds voucher
     * @param searchTerm
     * @param pageable
     * @return
     */
    Page<Voucher> findBySearchTerm(String searchTerm, Pageable pageable);

    /**
     * Updates the information of a voucher entry.
     * @param voucherEntry   The new information of a voucher entry.
     * @return  The updated voucher entry.
     * @throws com.tinyerp.gateway.exception.VoucherNotFoundException If the updated voucher entry is not found.
     */
    Voucher update(Voucher voucherEntry);
}
