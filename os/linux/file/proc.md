# Introduction
The **proc** filesystem is a pseudo-system which provide an interface to kernel data structures. Typically, It's mounted automatically by the system, but it can also be mounted manually using a command such as:
```
mount -t proc proc /proc
```
其他加载参数参考[Mount Options](./proc.md#MountOptions)

Most of the files in the **proc** file
# MountOptions
The **proc** filesystem supports the following mount options:

* **hidepid**=n (since Linux 3.3) 
    This Potion controls who can access the information in the */proc/[pid]* directories. The argument, n, is one of the following values
    |||
    |:-|:-|
    0|Everybody may access all */proc/[pid]* directories. This is the         traditional behavior, and the default if this mount option is not specified
    1|Users may not access files and subdirectors inside any */proc/[pid]   directories but their own.
    2|As for mode 1, but in addition the */proc/[pid]* directories belonging to other users become invisible

* gid=**gid**(since Linux 3.3)
  
    Specifies the ID of a group whose members are authorized too learn process information otherwise prohibited by **hidepid**
# Overview
  Underneath **proc**, there are the following general groups of files and  subdirectories
   |||
   |:-|:-|
    |/proc/[pid]| Each one of these subdirectories contains files and subdirectories exposing information about the process with the corresponding process ID.
    |/proc/[tid] | Each one of these subdirectories contains files and subdirectories exposing information about the thread with the corresponding thread ID.\nThe contents of these directories are thesame as the corresponding /proc/[pid]/task/[tid] directories.<br\>The /proc/[tid] subdirectories are not visible when iterating through /proc with getdents(2) (and thus are not visible when one uses ls(1) to view the contents of /proc).|


# 参考
1、[Linux Programmer's Manual: PROC(5)](http://man7.org/linux/man-pages/man5/proc.5.html)