package com.demo;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import net.bytebuddy.agent.ByteBuddyAgent;

import java.io.File;

/**
 * @author yulewei on 2018/10/4
 */
public class AgentLoader2 {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java com.demo.AgentLoader <pid/name> <agent> [options]");
            System.exit(0);
        }

        String jvmPid = args[0];
        String agentJar = args[1];
        String options = args.length > 2 ? args[2] : null;
        for (VirtualMachineDescriptor jvm : VirtualMachine.list()) {
            if (jvm.displayName().contains(args[0])) {
                jvmPid = jvm.id();
                break;
            }
        }
        ByteBuddyAgent.attach(new File(agentJar), jvmPid, options);
    }
}
