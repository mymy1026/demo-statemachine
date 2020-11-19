package com.yong;

import com.yong.machine.Events;
import com.yong.machine.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@SpringBootApplication
public class DemoStatemachineApplication implements CommandLineRunner {

    @Autowired
    StateMachineFactory<States, Events> factory;

    @Resource(name = "equipmentStateMachinePersist")
    private StateMachinePersister<States, Events, String> stateMachinePersist;

    public static void main(String[] args) {
        SpringApplication.run(DemoStatemachineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        StateMachine<States, Events> stateMachine = factory.getStateMachine();
        System.out.println("启动状态机");
        stateMachine.start();
        System.out.println("更新状态到待人");
        stateMachine.sendEvent(Events.STARVING_PERSON);
        System.out.println("存储stateMachine");
        stateMachinePersist.persist(stateMachine,"1");
        stateMachine = stateMachinePersist.restore(stateMachine,"1");
        System.out.println("获取stateMachine,当前状态为："+stateMachine.getState().getId());
        System.out.println("更新状态到LOT");
        stateMachine.sendEvent(Events.LOT);
        System.out.println("更新状态到Choice");
        stateMachine.sendEvent(Events.CHOICE);
        System.out.println("最终状态为："+stateMachine.getState().getId());
    }
}
