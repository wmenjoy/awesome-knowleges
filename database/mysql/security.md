# Mysql的安全

## 密码安全

数据库作为关键信息的存储地方，密码的保存是一个很关键但是有很麻烦的事情

### 原则
  1、选择安全的密码，参考[Password Security](/security/cryptography/password.md)
  2、密码存储在合适的地方，不要明文存储，甚至加密存储也要借助于权限控制
  3、安全的使用密码，不要泄露密码到文件，系统中
  4、定期更新密码



### 参考
[Passwordless authentication using mysql_config_editor with MySQL 5.6](https://opensourcedbms.com/dbms/passwordless-authentication-using-mysql_config_editor-with-mysql-5-6/)
