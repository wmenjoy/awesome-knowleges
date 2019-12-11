# 程序员的调试学

## 理论

### 为什么需要调试


### 调试的目标是什么

### 调试会有什么问题？

1. 没有源代码
2. 没有现场
3. 不能远程调试，不能影响用户使用

## 调试的技术有哪些

定位Bug，性能，能存泄露

## 代码local调试

## 远程调试

## 标准

### JDWP 协议

JDWP 是 Java Debug Wire Protocol 的缩写，它定义了调试器（debugger）和目标虚拟机（target vm）之间的通信协议。Target vm 中运行着我们要调试的 Java 程序，它与一般运行的 JVM 没有什么区别，只是在启动时加载了 JDWP Agent 从而具备了调试功能。而 debugger 就是我们本地的调试器，它向运行中的 target vm 发送指令来获取 target vm 运行时的状态和控制远程 Java 程序的执行。Debugger 和 target vm 分别在各自的进程中运行，他们之间通过 JDWP 通信协议进行通信。

### Java Instrument 机制


### 工具

1. [Btrace]()

2. [Alibaba](https://github.com/alibaba/arthas)
3. [Java 自带工具]()

## 参考
[图文并茂教你学会使用 IntelliJ IDEA 进行远程调试](https://juejin.im/post/5de68796518825124c50bb75?utm_source=gold_browser_extension)