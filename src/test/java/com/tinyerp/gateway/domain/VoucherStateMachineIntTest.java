package com.tinyerp.gateway.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VoucherStateMachineIntTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private StateMachineFactory<VoucherState, VoucherEvent> stateMachineFactory;

    @Test
    public void test() {
        StateMachine<VoucherState, VoucherEvent> stateMachine = stateMachineFactory.getStateMachine();;
        stateMachine.start();

        stateMachine.sendEvent(VoucherEvent.check);
    }
}
