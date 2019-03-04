package com.tinyerp.gateway.domain.voucher.action;

import com.tinyerp.gateway.domain.VoucherEvent;
import com.tinyerp.gateway.domain.VoucherState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
public class ClaimToCheckTransitionAction implements Action<VoucherState, VoucherEvent> {

    @Override
    public void execute(StateContext<VoucherState, VoucherEvent> context) {
        log.debug("Claim to CHECK transition action ... ");

        // todo: send async email
        sendEmail();
    }

    public void sendEmail() {
        log.debug("Sending email async");
    }
}
