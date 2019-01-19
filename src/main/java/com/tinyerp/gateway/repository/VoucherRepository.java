package com.tinyerp.gateway.repository;

import com.tinyerp.gateway.domain.Voucher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

}
