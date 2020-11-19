package com.yong.machine;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class StateMachinePersist implements org.springframework.statemachine.StateMachinePersist<States,Events,String> {
    private final HashMap<String, StateMachineContext<States, Events>> contexts = new HashMap<>();
    @Override
    public void write(StateMachineContext<States, Events> stateMachineContext, String s) throws Exception {
        contexts.put(s, stateMachineContext);
    }

    @Override
    public StateMachineContext<States, Events> read(String s) throws Exception {
        new DefaultStateMachineContext(s,)
        return contexts.get(s);
    }
}
