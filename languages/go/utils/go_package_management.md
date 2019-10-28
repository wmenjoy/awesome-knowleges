# go 包管理的历史
## go path
## go Deb 和go get
## go vendor
Vendor目录是Golang从1.5版本开始引入的，为项目开发提供了一种离线保存第三方依赖包的方法，但是到了golang 1.11之后，优先使用Module， Vendor目录被忽略了。
## go Mod
从go 1.11开始，官方支持的一个包管理工具， 1.12版本开始正式GA， golang 不再依赖于gopath
# Go Mod 讲解
## 1、go mod启用
go mod的启用是通过环境变量GO111MODULE开启的
```
#on: 开启go module，off: 不开启，auto:或者不设置: 当前目录不在GOPATH/src下并且存在go.mod时启用
export GO111MODULE=on
```
## 2、go 依赖包的下载
在国内，比如google.golang.org下的包下不来，但是在开启go module后这些下不来的依赖包可以通过goproxy.io下载（在go get命令中自动使用代理），也可以通过 https://github.com/goproxyio/goproxy.git 自己搭建私人代理服务器
```
export GOPROXY=https://goproxy.io
#更改之后通过goproxy.io下载会很顺利（尤其有墙的时候）
export GOPROXY=https://mirrors.aliyun.com/goproxy/
#阿里云提供的go moudule代理仓库（相比上一个推荐用这个）
```
另一个代理是 https://athens.azurefd.net 这个是微软的代理，同样可以自行搭建 https://github.com/gomods/athens ，文档 https://docs.gomods.io/

此外可以通过replace包来完成
```
replace (
    golang.org/x/crypto => github.com/golang/crypto v0.0.0-20181127143415-eb0de9b17e85
    golang.org/x/net => github.com/golang/net v0.0.0-20181114220301-adae6a3d119a
)
```
或者使用本地宝
```
require (
    modtest v0.0.0
)

replace (
    modtest v0.0.0 => ../modtest
)
```

## 3、IDE 使用go Mod
如果使用了GOLANG作为IDE，可以在File -> Settings -> Go -> Go Module(vgo)中打开“Enable Go Modules(vgo) integration”选项，就可以让IDE使用go module了
## 4、go mod 命令
```
edit        编辑go.mod文件
graph       打印模块依赖图
init        在当前文件夹下初始化一个新的module, 创建go.mod文件
tidy        增加丢失的module，去掉未用的module
vendor      将依赖复制到vendor下,注意依赖需要在import 中声明后才能进行导入
verify      校验依赖
why         解释为什么需要依赖
```
注: go get ./... 命令可以查找出当前项目的依赖
## 5、使go mod 打包
* 1、使用GOPATH模式进行打包
```
export GO111MODULE=off
export CGO_ENABLED=0
go build  -a -v -o app main.go
```
* 2、使用 vender 目录下包来进行打包
```
export GO111MODULE=on
export CGO_ENABLED=0
go build -mod=vendor -a -v -o app main.go
```


