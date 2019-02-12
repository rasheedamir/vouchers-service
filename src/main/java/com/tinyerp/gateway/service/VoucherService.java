package com.tinyerp.gateway.service;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.domain.VoucherEvent;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.domain.VoucherState;
import com.tinyerp.gateway.json.request.ApiClaimVoucher;
import com.tinyerp.gateway.json.response.ApiVoucherResponse;
import com.tinyerp.gateway.mapper.VoucherMapper;
import com.tinyerp.gateway.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Transactional
public class VoucherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;
    private final StateMachineFactory<VoucherState, VoucherEvent> stateMachineFactory;

    private static final String VOUCHER_ID_HEADER = "voucherId";

    @Autowired
    public VoucherService(VoucherRepository voucherRepository, VoucherMapper voucherMapper,
                          @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") StateMachineFactory<VoucherState, VoucherEvent> stateMachineFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
        this.stateMachineFactory = stateMachineFactory;
    }

    public ApiVoucherResponse claim(@Valid ApiClaimVoucher apiClaimVoucher) {
        LOGGER.debug("Claim a voucher");

        VoucherId voucherId = VoucherId.generate();
        Voucher voucher = voucherMapper.from(apiClaimVoucher, voucherId).state(VoucherState.CLAIMED).build();
        Voucher persistedVoucher = voucherRepository.add(voucher);

        return voucherMapper.from(persistedVoucher);
    }

    public ApiVoucherResponse onEvent(VoucherId id, VoucherEvent event) {
        StateMachine<VoucherState, VoucherEvent> sm = this.build(id);

        Message<VoucherEvent> message =  MessageBuilder.withPayload(event)
                .setHeader(VOUCHER_ID_HEADER, id.toString())
                .build();

        sm.sendEvent(message);

        return voucherMapper.from(voucherRepository.findById(id));
    }

    private StateMachine<VoucherState, VoucherEvent> build(VoucherId id) {
        Voucher voucher = this.voucherRepository.findById(id);
        String voucherIdKey = voucher.getId().toString();

        StateMachine<VoucherState, VoucherEvent> sm = this.stateMachineFactory.getStateMachine(voucherIdKey);
        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<>() {
                        @Override
                        public void preStateChange(State<VoucherState, VoucherEvent> state, Message<VoucherEvent> message, Transition<VoucherState, VoucherEvent> transition, StateMachine<VoucherState, VoucherEvent> stateMachine) {
                            Optional.ofNullable(message).ifPresent(msg -> {
                                Optional.ofNullable(String.class.cast(msg.getHeaders().get(VOUCHER_ID_HEADER))).ifPresent(voucherId1 -> {
                                            Voucher existingVoucher = voucherRepository.findById(new VoucherId(voucherId1));
                                            // TODO: Replace with copy builder
                                            Voucher updatedVoucher = Voucher
                                                    .newBuilder()
                                                    .id(existingVoucher.getId())
                                                    .description(existingVoucher.getDescription())
                                                    .state(state.getId())
                                                    .build();
                                            voucherRepository.update(updatedVoucher);
                                        });
                            });

                        }
                    });
                    sma.resetStateMachine(new DefaultStateMachineContext<>(voucher.getState(), null, null, null));
                });

        sm.start();

        return sm;
    }
}
