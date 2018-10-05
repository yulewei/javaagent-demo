package com.demo;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class ByteBuddyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .type(named("com.demo.App"))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                            TypeDescription typeDescription,
                                                            ClassLoader classLoader,
                                                            JavaModule module) {
                        return builder.method(named("getGreeting"))
                                .intercept(FixedValue.value(agentArgs));
                    }
                }).installOn(inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .disableClassFormatChanges()
                .type(named("com.demo.App"))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                            TypeDescription typeDescription,
                                                            ClassLoader classLoader,
                                                            JavaModule module) {
                        return builder.method(named("getGreeting"))
                                .intercept(FixedValue.value(agentArgs));
                    }
                }).installOn(inst);
    }

}
