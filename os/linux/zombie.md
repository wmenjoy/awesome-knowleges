# 僵尸进程

## 定义

In UNIX System terminology, a process that has terminated,but whose parent has not yet waited for it, is called a zombie.
在UNIX 系统中,一个进程结束了,但是他的父进程没有等待(调用wait / waitpid)他, 那么他将变成一个僵尸进程.  在fork()/execve()过程中，假设子进程结束时父进程仍存在，而父进程fork()之前既没安装SIGCHLD信号处理函数调用 waitpid()等待子进程结束，又没有显式忽略该信号，则子进程成为僵尸进程。

## 如何查看僵尸进程
1. 查找关键字defunct

```bash
ps -ef | grep defunct
```

2. 查找状态

```bash
ps -o pid,ppid, stat|grep Z
```

3. 使用top命令
```
Tasks:  95 total,   1 running,  94 sleeping,   0 stopped,   0 zombie
```

## 如何清理僵尸进程
一般僵尸进程很难直接kill掉，不过您可以kill僵尸的父进程。父进程死后，僵尸进程成为”孤儿进程”，过继给1号进程init，init始终会负责清理僵尸进程．它产生的所有僵尸进程也跟着消失。

可以通过如下：

```
ps -e -o ppid,stat | grep Z | cut -d" " -f1 | xargs kill -9

```

或者
```
kill -HUP `ps -A -ostat,ppid | grep -e '^[Zz]' | awk ’{print $2}’`

```

# 如何避免僵尸进程
处理SIGCHLD信号并不是必须的。但对于某些进程，特别是服务器进程往往在请求到来时生成子进程处理请求。如果父进程不等待子进程结束，子进程将成为僵尸进程（zombie）从而占用系统资源。如果父进程等待子进程结束，将增加父进程的负担，影响服务器进程的并发性能。在Linux下 可以简单地将 SIGCHLD信号的操作设为SIG_IGN。
signal(SIGCHLD,SIG_IGN);
这样，内核在子进程结束时不会产生僵尸进程。这一点与BSD4不同，BSD4下必须显式等待子进程结束才能释放僵尸进程

或者

用两次fork()，而且使紧跟的子进程直接退出，是的孙子进程成为孤儿进程，从而init进程将负责清除这个孤儿进程。



## 参考

1. [linux 如何清理僵尸进程 - 牛皮糖NewPtone - 博客园](https://www.cnblogs.com/yuxc/archive/2012/11/04/2753391.html)
