每天一个 Linux 命令（100）：strings 命令
-----------------------------

Linux爱好者

**Linux爱好者**

微信号 LinuxHub

功能介绍 点击获取《每天一个Linux命令》系列和精选Linux技术资源。「Linux爱好者」日常分享 Linux/Unix 相关内容，包括：工具资源、使用技巧、课程书籍等。

_今天_

收录于话题

  

（给Linux爱好者加星标，提升Linux技能）

> 转自：恋猫大鲤鱼  
> 
> https://dablelv.blog.csdn.net/article/details/89088572

1.命令简介
======

strings 命令是二进制工具集 GNU Binutils 的一员，用于打印文件中可打印字符串，文件可以是文本文件（test.c），但一般用于打印二进制目标文件、库或可执行文件中的可打印字符。字符串默认至少是 4 个或更多可打印字符的任意序列，可使用选项改变字符串最小长度。

2.命令格式
======

```
nm[-afovV][-min-len][-nmin-len][--bytes=min-len][-tradix][--radix=radix][-eencoding][--encoding=encoding][-][--all][--print-file-name][-Tbfdname][--target=bfdname][-w][--include-all-whitespace][--help][--version]file...
```

3.选项说明
======

注意，长选项的参数对于短选项也是必须的。

```
-a,--all,-扫描整个文件而不是只扫描目标文件初始化和装载段-d,--data仅打印文件中已初始化、加载的数据段中的字符串，这可能会减少输出中的垃圾量-e,--encoding=ENCODING选择字符编码与字节序。encoding可取值s=7bits的ASCII, S=8bits的Latin1, {b,l}=16bits宽字符大小端编码, {B,L}=32bits宽字符大小端编码。其中b，B代表bigendian，l，L代表littleendian-f,–-print-file-name在显示字符串前先显示文件名--help显示帮助信息-,-n,--bytes=MIN_LEN指定可打印字符序列的最小长度，而不是默认的4个字符-o类似--radix=o-t,--radix=RADIX输出字符串在文件中的偏移位置，RADIX可取值o（octal，八进制）、d（decimal，十进制）或者x（hexadecimal，十六进制）-T,--target=BFD_NAME指定二进制文件格式-v,-V,--version显示版本信息-w,--include-all-whitespace默认情况下，Tab 和空格字符包含在字符串中，但其他空白字符除外，比如换行符和回车符等字符不是。-w 使所有的空白字符被认为是字符串的一部分@FILE从指定的文件FILE中读取命令行选项
```

4.常用示例
======

（1）打印可执行文件中的所有可读字符串。

```
strings/bin/ls/lib64/ld-linux-x86-64.so.2libselinux.so.1_ITM_deregisterTMCloneTable__gmon_start___Jv_RegisterClasses_ITM_registerTMCloneTable_initfgetfileconfreeconlgetfilecon...
```

（2）查看某一个字符串属于哪个文件。

```
strings-f*|grep"xxx"
```

（3）查看glibc支持的版本。libc.so.6是c标准库，而这个标准库的制作者为了让库的使用者知道该库兼容哪些版本的标准库，就在这个库中定义了一些字符串常量，使用如下命令可以查看向下兼容的版本。

```
strings/lib64/libc.so.6|grepGLIBCGLIBC_2.2.5GLIBC_2.2.6GLIBC_2.3GLIBC_2.3.2GLIBC_2.3.3GLIBC_2.3.4GLIBC_2.4GLIBC_2.5GLIBC_2.6GLIBC_2.7GLIBC_2.8GLIBC_2.9GLIBC_2.10GLIBC_2.11GLIBC_2.12GLIBC_2.13GLIBC_2.14GLIBC_2.15GLIBC_2.16GLIBC_2.17GLIBC_PRIVATE
```

* * *

参考文献
====

\[1\] strings manual  
\[2\] GNU Binutils

  

> 给我们公号发送 命令 二字，获取“每天一个Linux命令”系列的完整目录。

  

\- EOF -

  

推荐阅读点击标题可跳转

1、[每天一个 Linux 命令（94）：man 命令](http://mp.weixin.qq.com/s?__biz=MzAxODI5ODMwOA==&mid=2666550875&idx=2&sn=fc3a41e08fa3aa551d555c492dde5ca8&chksm=80dc92f0b7ab1be6e84db9e4e4677d43f3d7c5307dff498a70965f138f8e41832b48fb871ec9&scene=21#wechat_redirect)

2、[每天一个 Linux 命令（92）：dirname 命令](http://mp.weixin.qq.com/s?__biz=MzAxODI5ODMwOA==&mid=2666550683&idx=2&sn=b6c4bea027fd22e0a3ea5b1e656ddee4&chksm=80dc9130b7ab1826529962f9ca7ae2ac044e39153210b50050f07a7dcafdc4c36f6a4b43eb38&scene=21#wechat_redirect)

3、[每天一个 Linux 命令（90）：help 命令](http://mp.weixin.qq.com/s?__biz=MzAxODI5ODMwOA==&mid=2666550621&idx=2&sn=d6f805ae07cae5f20a5089a123ed7d2e&chksm=80dc91f6b7ab18e0e8be6e03dc971279e38756f1e425fefe06e0dd35f68f790f2f7827cbf42b&scene=21#wechat_redirect)

  

看完本文有收获？请分享给更多人  

推

#### 发送到看一看

发送

**长按识别前往小程序**
