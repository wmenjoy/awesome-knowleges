
## 功能
1. [Linux Mount 1](https://segmentfault.com/a/1190000006872609)
1. [Linux mount （第二部分 - Shared subtrees）](https://segmentfault.com/a/1190000006899213)
2. [Linux Init 系统](https://www.ibm.com/developerworks/cn/linux/1407_liuming_init1/)
3. [哪些永不消逝的进程](https://www.ibm.com/developerworks/cn/linux/1702_zhangym_demo/index.html)
3. [virtio](https://www.ibm.com/developerworks/cn/linux/1402_caobb_virtio/index.html)
4. [Linux Network](https://www.ibm.com/developerworks/cn/linux/1310_xiawc_networkdevice/index.html)

## 内存

1. [Linux 内存管理](https://segmentfault.com/a/1190000008125006)
2. [Linux 内存使用情况](https://segmentfault.com/a/1190000008125059)
3. [Linux 交换空间](https://segmentfault.com/a/1190000008125116)
4. [Linux虚拟文件系统](https://segmentfault.com/a/1190000008476809)
5. [Linux OOM Killer](https://segmentfault.com/a/1190000008268803)


## 进程

### tty
1. [Linux session和进程组概述](https://segmentfault.com/a/1190000006917884)


## 文件系统与磁盘管理
1. [Btrfs文件系统之subvolume与snapshot](https://segmentfault.com/a/1190000008605135)
2. [Linux文件系统之aufs](https://segmentfault.com/a/1190000008489207)
3. [Linux常见文件系统对比](https://segmentfault.com/a/1190000008481493)
4. [磁盘](disk.md)
## NameSpace

1. [Linux Namespace系列（01）：Namespace概述](https://segmentfault.com/a/1190000006908272)
1. [Linux Namespace系列（02）：UTS namespace (CLONE_NEWUTS)](https://segmentfault.com/a/1190000006908598)
1. [Linux Namespace系列（03）：IPC namespace (CLONE_NEWIPC)](https://segmentfault.com/a/1190000006908729)
1. [Linux Namespace系列（04）：mount namespaces (CLONE_NEWNS)](https://segmentfault.com/a/1190000006912742)
1. [Linux Namespace系列（05）：pid namespace (CLONE_NEWPID)](https://segmentfault.com/a/1190000006912878)
3. [Linux Namespace系列（06）：network namespace (CLONE_NEWNET)](https://segmentfault.com/a/1190000006912930)
4. [Linux Namespace系列（07）：user namespace (CLONE_NEWUSER) (第一部分)](https://segmentfault.com/a/1190000006913195)
5. [Linux Namespace系列（08）：user namespace (CLONE_NEWUSER) (第二部分)](https://segmentfault.com/a/1190000006913499)
6. [Linux Namespace系列（09）：利用Namespace创建一个简单可用的容器](https://segmentfault.com/a/1190000006913509)

## cgroup

1. [Linux Cgroup系列（01）：Cgroup概述](https://segmentfault.com/a/1190000006917884)
1. [Linux Cgroup系列（02）：创建并管理cgroup](https://segmentfault.com/a/1190000007241437)
1. [Linux Cgroup系列（03）：限制cgroup的进程数（subsystem之pids）](https://segmentfault.com/a/1190000007468509)
1. [Linux Cgroup系列（04）：限制cgroup的内存使用（subsystem之memory）](https://segmentfault.com/a/1190000008125359)
1. [Linux Cgroup系列（05）：限制cgroup的CPU使用（subsystem之cpu）](https://segmentfault.com/a/1190000008323952)

## 原理
1. [启动过程](https://segmentfault.com/a/1190000006872609)
2. [运行级别、引导目标、关闭和重新引导](https://www.ibm.com/developerworks/cn/linux/l-lpic1-101-3/index.html)
2. [EBPF](https://www.ibm.com/developerworks/cn/linux/l-lo-eBPF-history/index.html)

## 系统诊断工具
1. [Systemtap： a tool that allows developers and administrators to write and reuse simple scripts to deeply examine the activities of a live Linux system. Data may be extracted, filtered, and summarized quickly and safely, to enable diagnoses of complex performance or functional problems.](https://www.sourceware.org/systemtap/documentation.html)
2. centos7升级内核修改默认启动项[Change GRUB2 default boot target – /dev/blog](https://possiblelossofprecision.net/?p=1334)

## 代理

1. 参考[here](proxy.md)

##  相关资讯
1. [TuxURLs – A neat Linux news aggregator](https://tuxurls.com/)

## 其他
### Accessing Windows Share folder in Linux

```bash
mount.cifs //192.168.29.216/WindowsShare /root/Desktop/LinuxShare -o user=Ehsaan
```

### Generate 20 Characters long string

```bash
openssl rand -base64 20
```

### Delete all specific file types in all subdirectories

```bash
find . -name \*.srt -type f -delete
```

### Create a 2048 bit RSA key with certificate

```bash
openssl req --newkey rsa:2048 -nodes -keyout private.key -x509 -out certificate.crt
```
### Remove Duplicates

```bash
sort file.log | uniq -u
```

### Number Images in a folder

```bash
ls | cat -n | while read n f; do mv "$f" "file-$n.jpg"; done
```



## 参考
1. [2daygeek](https://www.2daygeek.com/category/monitoring-tools/)
2. [tty/PTT设备](https://segmentfault.com/a/1190000009082089)
3. [学习101](https://www.ibm.com/developerworks/cn/linux/l-lpic1-map/index.html)
1. [Linux Performance](http://www.brendangregg.com/linuxperf.html)
