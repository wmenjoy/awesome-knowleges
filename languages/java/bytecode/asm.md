# Introduction
## ASM 是什么？
官网介绍
``` text
ASM is an all purpose Java bytecode manipulation and analysis framework. It can
be used to modify existing classes or to dynamically generate classes, directly in binary form.
ASM provides some common bytecode transformations and analysis algorithms from which custom complex 
transformations and code analysis tools can be built. ASM offers similar functionality as other Java 
bytecode frameworks, but is focused on performance. Because it was designed and implemented to be as 
small and as fast as possible, it is well suited for use in dynamic systems (but can of course be used 
in a static way too, e.g. in compilers).
```
简而言之
    ASM就是追求性能，还能灵巧的字节码操作工具。 直接操作class文件或者数据流。然后去修改java 类行为
## APT， aspectJ, Javaassist
```
APT:APT(Annotation Processing Tool)即注解处理器，是一种处理注解的工具，确切的说它是javac的一个工具，它用来在编译时扫描和处理注解。注解处理器以Java代码(或者编译过的字节码)作为输入，生成.java文件作为输出。简单来说就是在编译期，通过注解生成.java文件
```

# 参考
1. [官网](https://asm.ow2.io/)