# 命令描述:
## Linux:的描述
```
env - run a program in a modified environment
env [OPTION]... [-] [NAME=VALUE]... [COMMAND [ARG]...]
```
## Mac的描述：
```
env -- set environment and execute command, or print environment
env [-iv] [-P altpath] [-S string] [-u name] [name=value ...] [utility [argument ...]]
```
## 兼容性说明
* 1、env的位置 linux /bin/env, /usr/bin/env, mac /usr/bin/env， 使用/usr/bin/env可以满足大多数情况
* 2、env 通用的时-u， -i 命令是通用的。 mac的命令更强大，但是最慎用
