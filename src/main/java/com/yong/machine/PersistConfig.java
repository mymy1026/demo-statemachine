package com.yong.machine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

@Configuration
public class PersistConfig {


    @Autowired
    private com.yong.machine.StateMachinePersist stateMachinePersist;


    /**
     * 注入StateMachinePersister对象
     *
     * @return
     */
    @Bean(name = "equipmentStateMachinePersist")
    public StateMachinePersister<States, Events, String> getPersister() {
        return new DefaultStateMachinePersister<>(stateMachinePersist);
    }


}
