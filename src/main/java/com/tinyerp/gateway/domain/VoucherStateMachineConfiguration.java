package com.tinyerp.gateway.domain;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@Slf4j
@EnableStateMachineFactory(contextEvents=false)
public class VoucherStateMachineConfiguration extends EnumStateMachineConfigurerAdapter<VoucherState, VoucherEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<VoucherState, VoucherEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(false)
                .listener(loggingListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<VoucherState, VoucherEvent> states)
            throws Exception {
        states
                .withStates()
                .initial(VoucherState.CLAIMED, context -> setUnpaid(context.getExtendedState()))
                .end(VoucherState.PAID)
                .states(EnumSet.allOf(VoucherState.class));
    }

    public StateMachineListener<VoucherState, VoucherEvent> loggingListener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<VoucherState, VoucherEvent> from, State<VoucherState, VoucherEvent> to) {
                log.info("State changed to {}", to.getId());
            }
            @Override
            public void eventNotAccepted(Message<VoucherEvent> event) {
                log.error("Event not accepted: {}", event.getPayload());
            }
        };
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<VoucherState, VoucherEvent> transitions)
            throws Exception {
        // Transitions between all states must be built and marked by triggering events.
        // In short, the state machine describes the various states in which your system can be, and under which conditions it can go from state to state.
        transitions
                .withExternal()
                // (1)
                .source(VoucherState.CLAIMED)
                .target(VoucherState.CHECKED)
                .event(VoucherEvent.check)
                .action(checkVoucher())
/**
                .and()
                // (2)
                .withExternal()
                .source(OrderState.ReadyForDelivery)
                .target(OrderState.Completed)
                .guard(isPaid())
                .event(OrderEvent.Deliver)
                .secured("ROLE_ANONYMOUS", ComparisonType.ANY)
                .secured("hasTarget('S1')");
                .and()
                // (3)
                .withExternal()
                .source(OrderState.ReadyForDelivery)
                .target(OrderState.Canceled)
                .guard(isPaid())
                .event(OrderEvent.Refund)
                .action(refundPayment())
                .and()
                // (4)
                .withExternal()
                .source(OrderState.Completed)
                .target(OrderState.Canceled)
                .event(OrderEvent.Refund)
                .action(refundPayment())
                .and()
                // (5)
                .withExternal()
                .source(OrderState.Canceled)
                .target(OrderState.Open)
                .event(OrderEvent.Reopen)
                .and()
                // (6)
                .withExternal()
                .source(OrderState.Open)
                .target(OrderState.Canceled)
                .event(OrderEvent.Cancel)
                .and()
                // (7)
                .withExternal()
                .source(OrderState.Open)
                .target(OrderState.ReadyForDelivery)
                .event(OrderEvent.UnlockDelivery)
                .and()
                // (8)
                .withExternal()
                .source(OrderState.ReadyForDelivery)
                .target(OrderState.Canceled)
                .guard(not(isPaid()))
                .event(OrderEvent.Cancel)
                .and()
                // (9)
                .withExternal()
                .source(OrderState.ReadyForDelivery)
                .target(OrderState.AwaitingPayment)
                .guard(not(isPaid()))
                .event(OrderEvent.Deliver)
                .and()
                // (10)
                .withExternal()
                .source(OrderState.AwaitingPayment)
                .target(OrderState.Completed)
                .event(OrderEvent.ReceivePayment)
                .action(receivePayment())
                .and()
                // (11)
                .withInternal()
                .source(OrderState.ReadyForDelivery)
                .event(OrderEvent.ReceivePayment)
                .action(receivePayment())
                .and()
                // (12)
                .withInternal()
                .source(OrderState.Canceled)
                .event(OrderEvent.ReceivePayment)
                .action(receivePayment())
 **/
                .and();
    }

    void setUnpaid(ExtendedState extendedState) {
        log.info("Unsetting paid");
        extendedState.getVariables().put("paid", Boolean.FALSE);
    }

    void setPaid(ExtendedState extendedState) {
        log.info("Setting paid");
        extendedState.getVariables().put("paid", Boolean.TRUE);
    }

    public Action<VoucherState, VoucherEvent> checkVoucher() {
        return context -> setPaid(context.getExtendedState());
    }
}
