# Shell 知识大观

## 命令学习
[命令学习](posix_commond.md)

## Linux 进程研究
### pgrep 模拟
```shell
find /proc -maxdepth 2 -name cmdline  -exec grep -in "java" {} \;|grep -v find|while read LINE; do  path=${LINE:15}; echo -n "${pat/*} "; if [ -f "${LINE:9}" ]; then cat ${LINE:9};echo -e ""; fi done
```
* 1.[proc研究](../../os/linux/file/proc.md)
