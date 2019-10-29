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

# 基本类型
```go
bool // true, false 没有初始值为false

string //没有初始值，“”

//数字没有初始值为0

int  int8  int16  int32  int64
uint uint8 uint16 uint32 uint64 uintptr //uint ，uintptr 可能与机器有关

byte // alias for uint8

rune // alias for int32
     // represents a Unicode code point

float32 float64

complex64 complex128

```
# 类型转换
使用 T(v) 转换值v为类型T
```
var i int = 42
var f float64 = float64(i)
var u uint = uint(f)

i := 42
f := float64(i)
u := uint(f)
```

# 类型推断
使用 :=syntax 或者 var = expression syntax
go 会自动类型推断
```
var i int
j := i // j is an int
```
但是如果=的右边包含未归类的数字类型，左边的类型，可能是int,float64,或者complex128
```
i := 42           // int
f := 3.142        // float64
g := 0.867 + 0.5i // complex128
```
# 常量
使用const 申请， 可以是字符串，字符，boolean和数字类型， 常量不能使用:= 
未指定类型的数字常量会根据需要自动归类
```go
package main

import "fmt"

const (
	// Create a huge number by shifting a 1 bit left 100 places.
	// In other words, the binary number that is 1 followed by 100 zeroes.
	Big = 1 << 100
	// Shift it right again 99 places, so we end up with 1<<1, or 2.
	Small = Big >> 99
)

func needInt(x int) int { return x*10 + 1 }
func needFloat(x float64) float64 {
	return x * 0.1
}

func main() {
	fmt.Println(needInt(Small))
	fmt.Println(needFloat(Small))
	fmt.Println(needFloat(Big)) //Big太多会报错 ./prog.go:21:13: constant 1267650600228229401496703205376 overflows int

}


```

