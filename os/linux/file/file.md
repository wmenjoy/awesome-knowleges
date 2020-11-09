# 文件相关


## 使用操作
1. 获取打开文件描述符最多的程序

``` bash
# 查找文件句柄上线
ulimit –a
# 统计使用文件描述符最多的程序
lsof -n|awk '{print $2}'|sort|uniq -c|sort -nr

```

## 参考

1. [Linux中Too many open files 问题分析和解决_lkforce-CSDN博客](https://blog.csdn.net/lkforce/article/details/80710459)
