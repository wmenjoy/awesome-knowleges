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

## 一个特别的需求

前提：配置好了go module和proxy

需求：在内网有一个gitlab之类的代码库，需要下载该代码库的依赖包

问题：

（1）在配置了proxy的时候，所有的go get都从代理服务器下载，不再从本地使用git，也就无法从内网拉取代码

（2）在内部gitlab中的包，有可能在项目中使用“github.com/***”或者其他路径引用，go get的时候就会从引用路径找到如“github.com”的地址去使用git拉取代码，导致拉取失败

分析：

（1）针对问题一，可以通过在内网搭设可访问google.golang.org等网址的代理服务器，如果无法访问，也可以通过再走一层代理，即如果不是内网请求就代理到 https://goproxy.io、https://github.com/gomods/athens 或者自己用国外vps搭建的

（2）针对问题二，可以使用代理，相当于在（1）的方案上自定义代理规则；也可以通过git config配置指定url替换，如 git config --global url."git@.com:".insteadOf "https://github.com/***"

方案：

（1）通过在内网搭设代理服务器，自定义代理规则，可以优雅地解决这两个问题，但是需要搭建服务器，一来过程麻烦，二来不一定条件允许（比如公司内网）

（2）在需要非内网的包时，直接通过go的proxy，需要内网时，关闭go proxy，通过git的配置来指向正确的连接

（3）一个比较偏门的方法是修改go源码，改变go get的逻辑，让它通过代码逻辑可以分别使用git和proxy拉取代码，这个没有找到案例也没有尝试过，只是要给猜想假设
