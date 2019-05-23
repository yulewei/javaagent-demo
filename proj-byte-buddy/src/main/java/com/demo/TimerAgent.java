package com.demo;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class TimerAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .type(ElementMatchers.any())
                .transform((builder, type, classLoader, module) ->
                        builder.method(ElementMatchers.nameMatches(agentArgs))
                                .intercept(MethodDelegation.to(TimingInterceptor.class)))
                .installOn(inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
//                .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
                .type(ElementMatchers.any())
                .transform((builder, type, classLoader, module) ->
                        builder.visit(Advice.to(TimingAdvice.class)
                                .on(ElementMatchers.nameMatches(agentArgs))))
                .installOn(inst);
    }

}