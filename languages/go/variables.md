# 约定
1、var 关键字用于定义变量

2、函数内部可以通过 := 定义变量，函数外必须使用var

3、var的声明可以有initializers， go支持多复制，

``` go
package main

import "fmt"

var i, j int = 1, 2

func main() {
	var c, python, java = true, false, "no!"
	fmt.Println(i, j, c, python, java)
}

```
