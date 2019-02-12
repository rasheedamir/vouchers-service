package com.tinyerp.gateway.domain;

import org.springframework.messaging.Message;
import org.springframework.statemachine.state.State;

public interface VoucherStateChangeListener {
    void onStateChange(State<VoucherState, VoucherEvent> state, Message<VoucherEvent> message);
}
