# 数据源更新


## Debain
### 使用说明
一般情况下，修改/etc/apt/sources.list文件，将Debian的默认源地址改成新的地址即可，比如将http://deb.debian.org改成https://mirrors.xxx.com，可使用以下命令：
```bash
sed -i "s@http://deb.debian.org@https://mirrors.xxx.com@g" /etc/apt/sources.list
```
若要使用https源，则需要执行apt-get install apt-transport-https，再执行apt-get update更新索引。

### 常用软件源：

|网站|协议|
|--|--|
|163| http|


## 参考
1. [Debian 9 Stretch国内常用镜像源](https://www.cnblogs.com/liuguanglin/p/9397428.html)
