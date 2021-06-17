# Shell 知识大观

## 命令

1. [mac和Linux命令对比:持续更新](posix_commond.md)
1. [Speed Test: Check the Existence of a Command in Bash and Zsh](https://www.topbug.net/blog/2016/10/11/speed-test-check-the-existence-of-a-command-in-bash-and-zsh/)
![image](https://user-images.githubusercontent.com/9961069/122332797-a57ed300-cf69-11eb-9f3d-0c016aa44de9.png)

## Linux 进程研究

### pgrep 模拟

```shell
find /proc -maxdepth 2 -name cmdline  -exec grep -in "java" {} \;|grep -v find|while read LINE; do  path=${LINE:15}; echo -n "${pat/*} "; if [ -f "${LINE:9}" ]; then cat ${LINE:9};echo -e ""; fi done
```
### Json数据处理

JQ命令专门处理json 数据可以参考 [JQ Manual](https://stedolan.github.io/jq/manual/)

### 输出到多个文件

参考[tee](command/tee.md)
``` java
ls -al|tee -a 1.output

```
### 查看真实的内存占用
```
du --max-depth=1 --human-readable --no-dereference --one-file-system /var/lib/docker/overlay2
du -d 1 -h .  ## 这个计算不准确
```
### A Better Linux Command
参考[此处](https://www.topbug.net/blog/2016/11/28/a-better-ls-command/#more-953)
### Linux 录屏命令

script

## 混淆工具
1. [Bashfuscator/Bashfuscator: A fully configurable and extendable Bash obfuscation framework. This tool is intended to help both red team and blue team.](https://github.com/Bashfuscator/Bashfuscator)

##  参考

1. [命令行工具](https://juejin.im/post/5d89899ef265da03a95076fb?utm_source=gold_browser_extension)
2. [proc研究](../../os/linux/file/proc.md)
3. [Linux 下记录工具（history，screen，script）使用](https://www.linuxidc.com/Linux/2013-10/91614.htm)
1. [Effective Shell](https://effective-shell.com/docs/part-1-transitioning-to-the-shell/2-navigating-your-system/)
1. [How to Type Less and Do More in Terminals | by Henry Huang | Mac O’Clock | Medium](https://medium.com/macoclock/how-to-type-less-and-do-more-in-terminals-ee2af303b512)
