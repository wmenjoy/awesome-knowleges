# linux中hash命令：显示、添加或清除哈希表
## 一、命令描述
```
NAME         top
       hash — remember or report utility locations
DESCRIPTION         top
       The hash utility shall affect the way the current shell environment
       remembers the locations of utilities found as described in Section
       2.9.1.1, Command Search and Execution.  Depending on the arguments
       specified, it shall add utility locations to its list of remembered
       locations or it shall purge the contents of the list. When no
       arguments are specified, it shall report on the contents of the list.

       Utilities provided as built-ins to the shell shall not be reported by
       hash.

```


linux系统下的hash指令
说明：
linux系统下会有一个hash表，当你刚开机时这个hash表为空，每当你执行过一条命令时，hash表会记录下这条命令的路径，就相当于缓存一样。第一次执行命令shell解释器默认的会从PATH路径下寻找该命令的路径，当你第二次使用该命令时，shell解释器首先会查看hash表，没有该命令才会去PATH路径下寻找。
hash表的作用：
大大提高命令的调用速率。
hash的参数
root@redhat ~]# hash　　//输入hash或hash -l 可以查看hash表的内容，我刚开机所以为空
hash: hash table empty
[root@redhat ~]# hash -l
hash: hash table empty
##当我执行过2条命令后再看：
[root@redhat ~]# hash　　//hash表会记录下执行该命令的次数，以及命令的绝对路径
hits command
1 /bin/cat
1 /bin/ls
[root@redhat ~]# hash -l　　//加参数-l既可以看到hash表命令的路径，也可以看到它的名字，说不定会有别名哦
builtin hash -p /bin/cat cat
builtin hash -p /bin/ls ls
[root@redhat ~]# hash -p /bin/ls bb　　//添加hash表，可以看到我把ls命令重新写了一遍，改名为bb
[root@redhat ~]# bb　　　　//当我执行bb时就是执行ls命令
anaconda-ks.cfg        icmp_echo_ignore_aly~  pub.key
dead.letter        icmp_echo_ignore_alz~  rpmbuild
icmp_echo_ignore_all~  install.log       RPM-GPG-KEY-useradd
icmp_echo_ignore_alw~  install.log.syslog     RPM-GPG-KEY-westos
icmp_echo_ignore_alx~  passwd
[root@redhat ~]# hash -t ls　　//-t参数可以查看hash表中命令的路径，要是hash表中没有怎么办？
/bin/ls
[root@redhat ~]# hash -t df　　//我没使用过df，执行hash，就会提示找不到该命令
-bash: hash: df: not found
[root@redhat ~]# hash -r　　//清楚hash表，清楚的是全部的
[root@redhat ~]# hash -l
hash: hash table empty
[root@redhat ~]# hash -l
builtin hash -p /bin/cat cat
builtin hash -p /bin/ls ls
[root@redhat ~]# hash -d cat 　　//清楚其中的某一条
[root@redhat ~]# hash -l
builtin hash -p /bin/ls ls
