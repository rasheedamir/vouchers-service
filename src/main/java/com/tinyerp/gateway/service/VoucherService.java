package com.tinyerp.gateway.service;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.json.request.ApiVoucherRequest;
import com.tinyerp.gateway.json.response.ApiVoucherResponse;
import com.tinyerp.gateway.mapper.VoucherMapper;
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
    private static final String VOUCHER_PROCESS_KEY = "VoucherProcess";

    private final ProcessRuntime processRuntime;
    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;

    @Autowired
    public VoucherService(ProcessRuntime processRuntime, VoucherRepository voucherRepository, VoucherMapper voucherMapper) {
        this.processRuntime = processRuntime;
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
    }

    public ApiVoucherResponse save(@Valid ApiVoucherRequest apiVoucherRequest) {

        // TODO: to be generated automatically
        Long voucherId = 1L;
        Voucher voucher = voucherRepository.save(voucherMapper.from(apiVoucherRequest, voucherId));

        // TODO: The data from the ApiVoucherRequest will be passed onto the process
        // TODO: Q: To understand if it makes sense to pass whole object as JSON or pass piece by piece?
        // TODO: Q: The data from the API call will be used as payload of the start event?
        // TODO: Q: What will be the start event?
        // Just testing activiti
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey(VOUCHER_PROCESS_KEY)
                .withBusinessKey("voucher-" + voucherId)
                .withVariable("voucher", apiVoucherRequest)
                .build());

        // TODO: Q: To understand that does it make sense to pass all data to activiti process and then separately persist as entity as well? Isn't it duplication?

        // Just return some hard coded value for now
        return voucherMapper.from(voucher, processInstance);
    }
}
