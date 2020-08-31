# 数据源更新


## Debain
### 使用说明
一般情况下，修改/etc/apt/sources.list文件，将Debian的默认源地址改成新的地址即可，比如将http://deb.debian.org改成https://mirrors.xxx.com，可使用以下命令：
```bash
sed -i "s@http://deb.debian.org@https://mirrors.xxx.com@g" /etc/apt/sources.list
```
若要使用https源，则需要执行apt-get install apt-transport-https，再执行apt-get update更新索引。

### 数据源种类

|类别|说明|
|---|---|
|deb stretch||
|deb-src stretch||
|deb stretch-updates||
|deb-src stretch-updates||
|deb stretch-backports||
|deb-src stretch-backports||
|deb stretch/updates||
|deb-src stretch/updates||


### 常用软件源：

|网站|前缀|
|--|--|
|163|http://mirrors.163.com|
|中科大|https://mirrors.ustc.edu.cn|
|阿里云镜像站|http://mirrors.aliyun.com/|
|华为|https://mirrors.huaweicloud.com|
|清华|https://mirrors.tuna.tsinghua.edu.cn/|
|兰州|http://mirror.lzu.edu.cn|
|上海交大|https://mirror.sjtu.edu.cn/|

最后附上官方全球镜像站列表地址https://www.debian.org/mirror/list

## 参考
1. [Debian 9 Stretch国内常用镜像源](https://www.cnblogs.com/liuguanglin/p/9397428.html)
