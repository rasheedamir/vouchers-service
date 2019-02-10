package com.tinyerp.gateway.service;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.json.request.ApiVoucherRequest;
import com.tinyerp.gateway.json.response.ApiVoucherResponse;
import com.tinyerp.gateway.mapper.VoucherMapper;
import com.tinyerp.gateway.repository.VoucherRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class VoucherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository, VoucherMapper voucherMapper) {
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
    }

    public ApiVoucherResponse save(@Valid ApiVoucherRequest apiVoucherRequest) {
        LOGGER.debug("persisting a voucher");

        // TODO: to be generated automatically
        VoucherId voucherId = VoucherId.generate();
        Voucher voucher = voucherRepository.add(voucherMapper.from(apiVoucherRequest, voucherId));

        // Just return some hard coded value for now
        return voucherMapper.from(voucher);
    }
}
