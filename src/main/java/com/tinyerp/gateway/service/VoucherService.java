package com.tinyerp.gateway.service;

import com.tinyerp.gateway.json.ApiVoucher;
import com.tinyerp.gateway.repository.VoucherRepository;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class VoucherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherService.class);

    private final ProcessRuntime processRuntime;
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(ProcessRuntime processRuntime, VoucherRepository voucherRepository) {
        this.processRuntime = processRuntime;
        this.voucherRepository = voucherRepository;
    }

    public ApiVoucher save(@Valid ApiVoucher voucher) {

        // Just testing activiti
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("VoucherProcess")
                .withVariable("voucher", voucher)
                .build());

        // Just return some hard coded value for now
        return ApiVoucher.newBuilder().id(1L).description("First voucher").build();
    }
}
