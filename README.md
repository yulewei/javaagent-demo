# javaagent-demo

Java Agent 学习笔记 <http://nullwy.me/2018/10/java-agent/>


---


通过运行 `gradle runDemo`，执行 `com.demo.App` 的 `main` 方法，示例：

```
$ gradle runDemo
hello world
hello world
hello world
```


通过运行 `gradle runPremainDemo`，在执行 `com.demo.App` 的 `main` 方法之前执行 `premain`。原本应该输出 `hello world`，被替换为 `hello agent`，示例：

```
$ gradle runPremainDemo
hello agent
hello agent
hello agent
```


依次运行 `gradle runDemo` 和 `gradle runAgentLoader`，`com.demo.App` 的 `main` 的输出结果 `hello world`，在加载 agent 后被替换为 `hello agent`，执行输出示例：

```
hello world
hello world
hello agent
hello agent
hello agent
```

---

**使用 Byte Buddy**

现在修改 `com.demo.App2` 的 `getGreeting` 方法如下：

``` java
public static String getGreeting() {
    try {
        Thread.sleep((long) (1000 * Math.random()));
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return "hello world";
}
```

使用 `com.demo.TimerAgent` 对 `getGreeting` 方法的运行时间计时，premain 方式，示例：

```
$ gradle runPremainBuddyDemo
public static java.lang.String com.demo.App2.getGreeting() took 694ms
hello world
public static java.lang.String com.demo.App2.getGreeting() took 507ms
hello world
```

agentmain 方式，依次运行 `gradle runDemo2` 和 `gradle runAgentLoaderBuddy`，执行输出示例：

```
hello world
hello world
hello world
public static java.lang.String com.demo.App2.getGreeting() took 905ms
hello world
public static java.lang.String com.demo.App2.getGreeting() took 882ms
```
