# 约定
1、go是通过package来组织的（与python类似），只有package名为main的包可以包含main函数，一个可执行程序有且仅有一个main包，通过import关键字来导入其他非main包。

2、可见性规则。go语言中，使用大小写来决定该常量、变量、类型、接口、结构或函数是否可以被外部包含调用。根据约定，函数名首字母小写即为private，函数名首字母大写即为public。

3、函数不用先声明，即可使用。

4、在函数内部可以通过 := 隐士定义变量。（函数外必须显示使用var定义变量）

5、函数名字必须唯一，不支持类型推断

6、函数可以返回任意多个值，函数本身也是一个值，所以可以返回函数, return 语句可以没有任何参数返回（naked return)，但是和返回值一样的变量会自动返回。naked return 应该在不影响阅读的前提下使用

7、函数的返回值也可以命名

``` func定义

func ${funcName}(${varName} ${varType}, ...) (${returnName} ${returnType}){
//body
}

```
# Defer
defer的语句，实在函数返回的时候执行,类似于java的finally
```go

import "fmt"

func main() {
	defer fmt.Println("world")

	fmt.Println("hello")
}

```
## 多个defer
defer 是使用栈来存储， 所以后申请的，先调用
``` go
package main

import "fmt"

func main() {
	fmt.Println("counting")

	for i := 0; i < 10; i++ {
		defer fmt.Println(i)
	}

	fmt.Println("done")
}

```
result:
```shell
counting
done
2
1
0
```


