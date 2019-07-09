# Mac 安装 Mysql 5.7
## 1. mac 安装mysql5.7
``` zsh
brew  install mysql@5.7

```
## 2 mac 下mysql的安全配置
``` bash
mysql_secure_installation
``` 
## 3 mac 下启动mysql
``` bash
brew services start mysql@5.7
```
或者
``` bash
  /usr/local/opt/mysql@5.7/bin/mysql.server start
```
## 4 启动
``` bash
 ~  mysql -u root -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 7
Server version: 5.7.23 Homebrew

Copyright (c) 2000, 2018, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> 
```

# Mysql5.8数据库 不兼容
## 1、用户认证方式变更
* 提供单独不使用新的验证方式降级
## 2、Caused by: java.sql.SQLException: Could not retrieve transation read-only status server
* 更新驱动，或者mysql降级
