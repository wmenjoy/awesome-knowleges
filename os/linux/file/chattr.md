该命令只有root有权限使用,并且设置后对root用户有效
chattr [+-=] 选项 文件或目录
常见选项说明:

选项|说明
----|--------
A|文件或目录的 atime (access time)不可被修改(modified), 可以有效预防例如手提电脑磁盘I/O错误的发生。
S|硬盘I/O同步选项，功能类似sync。
a|即append，设定该参数后，只能向文件中添加数据，而不能删除，多用于服务器日志文件安全，只有root才能设定这个属性。
c|即compresse，设定文件是否经压缩后再存储。读取时需要经过自动解压操作。
d|即no dump，设定文件不能成为dump程序的备份目标。
i|设定文件不能被删除、改名、设定链接关系，同时不能写入或新增内容。i参数对于文件 系统的安全设置有很大帮助。
j|即journal，设定此参数使得当通过mount参数：data=ordered 或者 data=writeback 挂 载的文件系统，文件在写入时会先被记录(在journal中)。如果filesystem被设定参数为 data=journal，则该参数自动失效。
s|保密性地删除文件或目录，即硬盘空间被全部收回。
u|与s相反，当设定为u时，数据内容其实还存在磁盘中，可以用于undeletion。

各参数选项中常用到的是a和i。a选项强制只可添加不可删除，多用于日志系统的安全设定。而i是更为严格的安全设定，只有superuser (root) 或具有CAP_LINUX_IMMUTABLE处理能力（标识）的进程能够施加该选项。

以最常见的选项i和a举例说明
i:如果对文件设置i属性,那么不允许对文件进行删除、改名、增加和修改数据。
如果对目录设置i属性，那么只能修改目录下文件的数据，但是不能创建新文件和删除已有文件


# 参考
1. [Linux文件属性命令chattr](https://www.cnblogs.com/iaknehc/p/6891867.html)
2. [5 Practical “chattr” Command Usage Examples in Linux
](https://www.sanfoundry.com/5-practical-chattr-command-usage-examples-in-linux/)
