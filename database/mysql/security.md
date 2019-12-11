# Mysql的安全

## 密码安全

数据库作为关键信息的存储地方，密码的保存是一个很关键但是有很麻烦的事情

### 原则
  1. 选择安全的密码，参考[Password Security](/security/cryptography/password.md)
  2. 密码存储在合适的地方，不要明文存储，甚至加密存储也要借助于权限控制
  3. 安全的使用密码，不要泄露密码到文件，系统中
  4. 定期更新密码
### 风险与解决办法
  - linux的history命令能够显示历史命令，所以如果命令行中有秘钥，也会展示出来
    ```
      mysql -uroot -pxxxxxx 
      mysqldump -uroot > 1.sql
    ```
    使用：
    * mysql_config_editor set --login-path=mysql_login --host=127.0.0.1 --port=33061 --user=root --password,
    * mysql --login-path=mysql_login
    * 参考 [Mysql免密登录](https://opensourcedbms.com/dbms/passwordless-authentication-using-mysql_config_editor-with-mysql-5-6/)
  - 秘钥不要存放在代码中。 服务器的配置中等，如果有人能够访问这些代码就有可能获取密码
    借助于专业的秘钥管理工具来存储，并且一定要结合合理的安全控制，来保证密码的安全


### 参考
[Passwordless authentication using mysql_config_editor with MySQL 5.6](https://opensourcedbms.com/dbms/passwordless-authentication-using-mysql_config_editor-with-mysql-5-6/)
[老黄的Mysql密码使用方式](https://github.com/bingoohuang/blog/issues/118#issuecomment-548677887)
