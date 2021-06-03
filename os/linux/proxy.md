# 代理

## 使用squid
### 1. 安装

```
yum install squid
```

### 2. 调整配置文件
1. 编辑 /etc/squid/squid.conf。 根据实际情况修改文件中的ACL列，或者将http_access deny all   将其修改为 http_access allow all  表示所有用户都可以访问这个代理，
2. 端口配置在  http_port 3128  修改为  http_port 192.168.3.171:3128  这里的IP及端口是 squid的代理IP及端口
3. systemctl restart squid
4. export http_proxy=http://192.168.3.171:3128
5. 比如centos的配置，可以修改 /etc/yum.conf。增加proxy=http://192.168.3.171:3128 

## 文章
1. [linux内网机器访问外网代理设置 - 272764732的个人空间 - OSCHINA - 中文开源技术交流社区](https://my.oschina.net/mingpeng/blog/293744)
