# 优雅的停止程序
linux 程序的退出标注是通过信号量来完成，当然我们可以通过暴露socket端口来完成，不过这里只是讨论信号量，尽量利用系统提供的设施，而不用做特殊的额外的开发

## 单进程的退出  
对于单个进程而言，当接受到一个信号量的时候，我们程序，应该能够接受这个信号，并根据这个信号量在程序退出前，处理程序应该做的事情。

Linux的[trap](http://man7.org/linux/man-pages/man1/trap.1p.html)命令，正是为了完成这个命令而存在的，我们可以针对信号量，监理对应的处理方法。

比如我们将要用的进程例子, 我们想在程序接收到终端，kill命令的时候能够，执行term_exit方法。
```bash
status=true
term_exit()
{
   echo "sub: $1 terminated"
   status=false
}
trap "term_exit TERM" TERM
trap "term_exit INT" INT
while [ "$status" = "true" ]; do
  echo -n "." >> 1.log
  sleep 1
done
#清理信号量
trap - TERM INT
echo "sub: DONE"
exit 0

```
**trap term_exit TERM** 表示整个程序会特殊处理TERM 信号量，接收到信号处理后，会执行**term_exit TERM**, 下一个trap 类似
** trap - TERM INT** 清理信号量
程序执行的结果如下如下
```
[root@fs03-192-168-126-18 temp]# sh sub.sh
^Csub: INT terminated
sub: DONE
[root@fs03-192-168-126-18 temp]# sh sub.sh &
[1] 6577
[root@fs03-192-168-126-18 temp]# kill 6577
[root@fs03-192-168-126-18 temp]# sub: TERM terminated
sub: DONE
```


## 多进程的退出
这种情况下，我们一般有个管理进程。 我们需要发给管理进程信号量，由管理进程，根据信号来分别处理worker进程的退出善后事宜。
这里就不仅需要能够管理进程去通知worker进程来执行退出任务，还要有机制来查询子进程的执行状态。 信号量，socket通信都是好的方法。

在管理进程和worker进程的关系是父子进程， 利用linux系统本身在子进程退出的时候会给父进程发消息的机制，能做的更高效。shell的buildin命令[wait](http://man7.org/linux/man-pages/man1/wait.1p.html)，就是专门来完成这件事情的
结合单进程的退出，我们在增加一个简单的管理服务， 代码如下:
```
#!/bin/sh

pidfile=./proc.pid

term_exit()
{
   pid=`cat $pidfile`
   echo "pid is $pid"
   echo "main: terminated"
   kill -s TERM  $pid
}
trap term_exit TERM INT

#Run sub.sh and wait it.
sh ./sub.sh &
sub_pid=$!
echo $sub_pid > $pidfile
wait $sub_pid || exit  true
echo "main: DONE"
trap - TERM INT
rm -rf $pidfile
exit 0

```

其中
**wait $sub_pid || exit  true** 用来等待子进程的退出， 如果有异常忽略
程序执行的结果如下如下
```bash
[root@fs03-192-168-126-18 temp]# sh main.sh
^Cpid is 13184
main: terminated
main: DONE
[root@fs03-192-168-126-18 temp]# sub: TERM terminated
sub: DONE
```
这里注意子进程最好是通过后台进程来做，因为trap是在执行完一条命令结束后才会响应对应的信号量，而这个可能会导致一些小问题。

## Docker程序的优雅退出
docker 本身要求服务必须有一个前台进程。如果前台进程正常退出就表示这个程序已经执行完成。 而我们的传统服务程序，大多数都是后台程序，那么如何去适配呢?

方案有二：
1. 修改程序的启动方式为前台启动
2. docker本身执行的程序和我们程序本身分离。docker执行的程序就相当于我们的管理程序，我们的程序继续以后台程序执行

两个方案比较：
方案一：
优点： 1.虽然修改代码，相对简单易懂，没有额外的进程开销
      2. 可以充分利用docker或者生态默认提供的健康检查机制，日志机制
缺点： 1. docker服务本身的重启，只能通过docker本身来实现，如果接入k8s，ip甚至实现漂移，测试、运维比较困难
      2. 服务本身需要适配docker的这种机制走修改

方案二：
优点： 1. 服务本身不需要修改代码 
      2. 在运维的时候，可以实现直接在docker里面重启程序，或者用systemctl 这种管理程序的方式
      3. 也能够提供一个在docker内部的动态扩展点
缺点： 1.需要开发单独的管理程序，理解起来比较费劲
      2. 多一个管理进程，开销更大
      
      
  


