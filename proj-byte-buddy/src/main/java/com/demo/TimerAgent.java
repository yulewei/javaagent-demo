package com.demo;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class TimerAgent {

    public static void premain(String arguments, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.named("com.demo.App2"))
                .transform((builder, type, classLoader, module) ->
                        builder.method(ElementMatchers.any())
                                .intercept(MethodDelegation.to(TimingInterceptor.class)))
                .installOn(instrumentation);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
//                .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
                .type(ElementMatchers.named("com.demo.App2"))
                .transform((builder, type, classLoader, module) ->
                        builder.visit(Advice.to(TimingAdvice.class)
                                .on(ElementMatchers.named("getGreeting"))))
                .installOn(inst);
    }

}