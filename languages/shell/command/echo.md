# 命令简介
## linux 的描述
```
NAME
       echo - display a line of text

SYNOPSIS
       echo [SHORT-OPTION]... [STRING]...
       echo LONG-OPTION

DESCRIPTION
       Echo the STRING(s) to standard output.
       
echo support -n -e -E options
```
## mac 的描述
```
NAME
     echo -- write arguments to the standard output

SYNOPSIS
     echo [-n] [string ...]
DESCRIPTION
     The echo utility writes any specified operands, separated by single blank (` ') characters and fol-
     lowed by a newline (`\n') character, to the standard output.
     
     only -n option is supported to do not print the trailing newline character
     
     echo command default enables the interpretation of backslash escapes. 
```
## 兼容性
1、 Mac系统的echo 是内置命令， linux 位于/bin/echo

2、两者支持共同的-n命令

# 用途
## 1、输出文本到文件

## 2、用作函数的结果

