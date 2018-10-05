package com.demo;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * @author yulewei on 2018/10/4
 */
public class AgentLoader {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java -cp .:$JAVA_HOME/lib/tools.jar"
                    + " com.demo.AgentLoader <pid/name> <agent> [options]");
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

        VirtualMachine jvm = VirtualMachine.attach(jvmPid);
        jvm.loadAgent(agentJar, options);
        jvm.detach();
    }
}
