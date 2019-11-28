# 什么是JAAS?
   Java Authentication and Authorization Service JDK2的作为可选包引入，jdk1.4正式集成到JDK，扩展java Security框架，增加对运行时安全的支持。
 
# 为什么要用JAAS

# JAAS 如何使用？
## 1. configure files
jaas的客户端和服务端需要配置jaas.conf文件
``` 
    Name {
            ModuleClass  Flag    ModuleOptions;
            ModuleClass  Flag    ModuleOptions;
            ModuleClass  Flag    ModuleOptions;
      };
      Name {
            ModuleClass  Flag    ModuleOptions;
            ModuleClass  Flag    ModuleOptions;
      };
      other {
            ModuleClass  Flag    ModuleOptions;
            ModuleClass  Flag    ModuleOptions;
      };
 
```

# JAAS 原理是什么样子的

# JAAS 如何扩展

# 参考文档？
1. [The Java Authentication and Authorization Service Munual](https://docs.oracle.com/javase/8/docs/technotes/guides/security/jaas/JAASRefGuide.html)
2. [JAAS是什么梗](https://blog.csdn.net/program_developer/article/details/78795922)
3. [SDK Config类](https://docs.oracle.com/javase/7/docs/api/javax/security/auth/login/Configuration.html)