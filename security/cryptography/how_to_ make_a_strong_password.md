# 谈一谈密码
来试一下你自己的密码有多安全[点击](https://howsecureismypassword.net/)， 安全意识从我们自己做起哦
网站如下图
![image](./images/how_to_make_password.png)
好了，来看点有用的东西，快速生成安全的密码。更多请关注[password](https://github.com/wmenjoy/awesome-knowleges/blob/master/security/cryptography/password.md)
## 1. 使用openssh 
```
  openssl rand -base64 14
```
![image](/images/openssh.png)
## 2. pwgen
pwgen 是一个简单却非常有用的命令行工具，用它可以在短时间内生成一个随机且高强度的密码。它设计出的安全密码可以被人们更容易地记住。在大多数的类 Unix 系统中都可以获取到它。

在基于 RPM 的系统中，运行：
```
yum  install pwgen
```
在基于 APT 的系统中，则运行：
```
sudo apt-get install pwgen
```
使用
```
pwgen 14 1
```
![image](/images/pwgeen.png)

另外，pwgen 命令还有一些很实用的选项：
```
-c 或 --capitalize 在密码中包含至少一个大写字母
-A 或 --no-capitalize 在密码中不包含大写字母
-n 或 --numerals 在密码中包含至少一个数字
-0 或 --no-numerals 在密码中不包含数字
-y 或 --symbols 在密码中包含至少一个特殊字符
-s 或 --secure 生成完全随机的密码
-B 或 --ambiguous 在密码中不包含难于区分的字母，如 0 和 o、1 和 l
-h 或 --help 输出帮助信息
-H 或 --sha1=path/to/file[#seed] 使用某个给定文件的 sha1 哈希值来作为随机数的生成种子
-C 按列输出生成好的密码
-1 不按列输出生成好的密码
-v 或 --no-vowels 不使用任何元音字母，以防止生成下流的词语 ```

```
## 3. 使用GPG
GPG (GnuPG 或 GNU Privacy Guard) 是一个自由开源的命令行程序，可以用于替代赛门铁克的 PGP 加密软件。在类 Unix 操作系统、Microsoft Windows 和 Android 中都可以获取到它。
``` 
gpg --gen-random --armor 1 14
```
生成样例：
```
DkmsrUy3klzzbIbavx8=
```

## 4. 使用SHA算法来加密日期，并输出结果的前32个字符：
``` bash
date +%s |sha256sum |base64 |head -c 32 ;echo
```
生成结果如下:
```
ZTNiMGM0NDI5OGZjMWMxNDlhZmJmNGM4
```
## 5 /dev/urandom 生成随机数
### 使用内嵌的/dev/urandom，并过滤掉那些日常不怎么使用的字符。这里也只输出结果的前32个字符：
```
< /dev/urandom tr -dc _A-Z-a-z-0-9 |head -c${1:-32};echo
```
生成结果如下:
```
pDj0Xwz7exD_Qb5B27BwWsM1hrF3a7cJ
```
### 使用string命令，它从一个文件中输出可打印的字符串
```
strings /dev/urandom | grep -o '[[:alnum:]]' | head -n 32 | tr -d '\n'; echo
```
生成结果如下:
```
W4v1iQtkmQ8sIDd9jxDQNpg8HPMOZ8
```
### 使用非常有用的dd命令
```
dd if=/dev/urandom bs=1 count=32 2>/dev/null | base64 -w 0 | rev | cut -b 2- | rev
```
生成结果如下:
```
9+0RUd4U3HmSdMlgD7j0sf/r09MZFDVBS28W+pO2WcA
```

如果每次都使用上述某种方法，那更好的办法是将它保存为函数。如果这样做了，那么在首次运行命令之后，你便可以在任何时间只使用randpw就可以生成随机密码。或许你可以把它保存到你的~/.bashrc文件里面
```
randpw(){ < /dev/urandom tr -dc _A-Z-a-z-0-9 | head -c${1:-16};echo;}
```
生成结果如下:
```
vgBX8cNo950RiykZRpPYa4BvbAvZbY_x
```
## 6 使用古诗词生成密码

参考[黄药师的古诗词生成文章](https://github.com/bingoohuang/blog/issues/22)

## 参考
[生成密码的四种形式](https://blog.csdn.net/u014743697/article/details/54136133)
[利用Linux系统生成随机密码的10种方法](https://www.cnblogs.com/xyz0601/p/4445711.html)
