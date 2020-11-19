package com.yong.machine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.WAIT_MATERIAL)
                .choice(States.RESTORE)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal().source(States.WAIT_MATERIAL).target(States.WAIT_PERSON).event(Events.STARVING_PERSON)
                .and().withExternal().source(States.WAIT_MATERIAL).target(States.PRODUCTION).event(Events.LOT)
                .and().withExternal().source(States.WAIT_MATERIAL).target(States.LOT_CHANGE).event(Events.LOT_CHANGE)
                .and().withExternal().source(States.WAIT_PERSON).target(States.WAIT_MATERIAL).event(Events.STARVING_MATERIAL)
                .and().withExternal().source(States.WAIT_PERSON).target(States.PRODUCTION).event(Events.LOT)
                .and().withExternal().source(States.WAIT_PERSON).target(States.LOT_CHANGE).event(Events.LOT_CHANGE)
                .and().withExternal().source(States.PRODUCTION).target(States.WAIT_MATERIAL).event(Events.STARVING_MATERIAL)
                .and().withExternal().source(States.PRODUCTION).target(States.WAIT_PERSON).event(Events.STARVING_PERSON)
                .and().withExternal().source(States.PRODUCTION).target(States.LOT_CHANGE).event(Events.LOT_CHANGE)
                .and().withExternal().source(States.PRODUCTION).target(States.RESTORE).event(Events.CHOICE)
                .and().withChoice().source(States.RESTORE)
                .first(States.PRODUCTION,productionGuard())
                .then(States.WAIT_MATERIAL,waiteMaterialGuaid())
                .then(States.WAIT_PERSON,waitePersonGuard())
                .last(States.LOT_CHANGE)
                .and().withExternal().source(States.LOT_CHANGE).target(States.PRODUCTION).event(Events.LOT);

    }

    private Guard<States, Events> waitePersonGuard() {
        return new Guard<States, Events>() {
            @Override
            public boolean evaluate(StateContext<States, Events> context) {
                //todo 数据库查询并处理
                return false;
            }
        };
    }

    private Guard<States, Events> waiteMaterialGuaid() {
        return new Guard<States, Events>() {
            @Override
            public boolean evaluate(StateContext<States, Events> context) {
                //todo 数据库查询并处理
                return false;
            }
        };
    }

    @Bean
    public Guard<States, Events> productionGuard() {
        return new Guard<States, Events>() {
            @Override
            public boolean evaluate(StateContext<States, Events> context) {
                //todo 数据库查询并处理
                return false;
            }
        };
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }
}
