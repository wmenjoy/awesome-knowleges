1、安装字体

```bash
yum -y install fontconfig #安装字体库
yum -y install ttmkfdir mkfontscale #安装字体索引信息
```
可拷贝Windows的字体目录：C:\Windows\Fonts

2. linux字体目录：/usr/share/fonts，建议创建一个目录mkdir chinese 放中文字体
3. 把字体上传到/usr/share/fonts/chinese目录
4. 然后在/usr/share/fonts/chinese执行命令，生成字库索引信息
    ```
    mkfontscale（如果提示 mkfontscale: command not found，需自行安装 # yum install mkfontscale ）

    mkfontdir
    ```
5. 更新字体缓存
```
fc-cache
```
其他：

 

linux中不显示中文即没有安装相应的字体，我们安装字体即可：

 

在centos中执行：yum install bitmap-fonts bitmap-fonts-cjk
