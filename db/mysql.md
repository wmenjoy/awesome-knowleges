# Mac 安装 Mysql 5.7
## 1. mac 安装mysql5.7
``` zsh
brew  install mysql@5.7

```
## 2 mac 下mysql的安全配置
``` bash
$ mysql_secure_installation

Securing the MySQL server deployment.

Connecting to MySQL using a blank password.

VALIDATE PASSWORD PLUGIN can be used to test passwords
and improve security. It checks the strength of password
and allows the users to set only those passwords which are
secure enough. Would you like to setup VALIDATE PASSWORD plugin?
// 提示是否设置密码
Press y|Y for Yes, any other key for No: y
// 提示选择密码强度等级
There are three levels of password validation policy:

LOW    Length >= 8
MEDIUM Length >= 8, numeric, mixed case, and special characters
STRONG Length >= 8, numeric, mixed case, special characters and dictionary                  file

Please enter 0 = LOW, 1 = MEDIUM and 2 = STRONG: 1
Please set the password for root here.
// 按照所选的密码强度要求设定密码
New password: 

Re-enter new password: 

// 提示密码强度50,不符合要求重新设置密码
Estimated strength of the password: 50 
Do you wish to continue with the password provided?(Press y|Y for Yes, any other key for No) : y
 ... Failed! Error: Your password does not satisfy the current policy requirements

New password: 

Re-enter new password: 
// 提示密码强度100,符合要求继续进行
Estimated strength of the password: 100 
Do you wish to continue with the password provided?(Press y|Y for Yes, any other key for No) : y
By default, a MySQL installation has an anonymous user,
allowing anyone to log into MySQL without having to have
a user account created for them. This is intended only for
testing, and to make the installation go a bit smoother.
You should remove them before moving into a production
environment.
// 提示删除默认无密码用户
Remove anonymous users? (Press y|Y for Yes, any other key for No) : y
Success.


Normally, root should only be allowed to connect from
'localhost'. This ensures that someone cannot guess at
the root password from the network.
// 提示禁止远程root登录
Disallow root login remotely? (Press y|Y for Yes, any other key for No) : no

 ... skipping.
By default, MySQL comes with a database named 'test' that
anyone can access. This is also intended only for testing,
and should be removed before moving into a production
environment.

// 提示删除默认自带的test数据库
Remove test database and access to it? (Press y|Y for Yes, any other key for No) : y
 - Dropping test database...
Success.

 - Removing privileges on test database...
Success.

Reloading the privilege tables will ensure that all changes
made so far will take effect immediately.
// 提示是否重新加载privilege tables
Reload privilege tables now? (Press y|Y for Yes, any other key for No) : y
Success.

All done! 
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
