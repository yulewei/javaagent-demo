package com.demo;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

public class TimingAdvice {

    @Advice.OnMethodEnter
    public static long enter() {
        return System.currentTimeMillis();
    }

    @Advice.OnMethodExit
    public static void exit(@Advice.Origin Method method, @Advice.Enter long start) {
        System.out.println(method + " took " + (System.currentTimeMillis() - start) + "ms");
    }
}