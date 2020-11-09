# 文件相关


## 使用操作
1. 获取打开文件描述符最多的程序

``` bash
lsof -n|awk '{print $2}'|sort|uniq -c|sort -nr

```
