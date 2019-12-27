# tee
## 命令简介
### linux 的描述
```
NAME
       tee - read from standard input and write to standard output and files

SYNOPSIS
       tee [OPTION]... [FILE]...

DESCRIPTION
       Copy standard input to each FILE, and also to standard output.

```
### mac 的描述
```
NAME
     tee -- pipe fitting

SYNOPSIS
     tee [-ai] [file ...]

DESCRIPTION
     The tee utility copies standard input to standard output, making a copy in zero or more files.  
     The output is unbuffered. file  A pathname of an output file. The tee utility takes the default action for all signals, except in the event of the -i option. The tee utility exits 0 on success, and >0 if an error occurs.
```
## 兼容性说明
```
-a        [Linux]: append to the given FILEs, do not overwrite
          [Mac]: Append the output to the files rather than overwriting them.

-i        [Linux]: ignore interrupt signals
          [Mac]: Ignore the SIGINT signal.

```

## 用途
### 1、输出到多个文件

### 2.拷贝输入到文件

## 参考

[man7: tee(1)](http://man7.org/linux/man-pages/man1/tee.1.html)
