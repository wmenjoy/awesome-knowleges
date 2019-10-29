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
# 指针
Go支持指针， *T 定义为只想T类型value的指针， 默认为nil
```go
var p * int = &i

fmt.Println(*p) // read i through the pointer p
*p = 21         // set i through the pointer p
```
不想c语言，指针没有数字计算功能

# 结构体
```go
import "fmt"

type Vertex struct {
	X int
	Y int
	//X, Y int等价

}

func main() {
	v := Vertex{1, 2}
	v.X = 4
	fmt.Println(v.X)
	
	p := &v
	p.X = 1e9 //指针可以不必使用(*p).X
	p  = &Vertex{1, 2} // has type *Vertex
	v1 = Vertex{1, 2}  // has type Vertex
	v2 = Vertex{X: 1}  // Y:0 is implicit
	v3 = Vertex{}      // X:0 and Y:0
	p  = &Vertex{1, 2} // has type *Vertex

}

```
1、结构体类似于 c 
```go
type $Name struct{
}
```
2、结构体申明和变量类似， 赋值可以使用Name:synatx语法，也可以使用$Struct{变量的形式}，可以有空值
# 数组
[n]T 是一个有n个类型为T的数组， 长度固定
```
var a [10]int;
func main() {
	var a [2]string
	a[0] = "Hello"
	a[1] = "World"
	fmt.Println(a[0], a[1])
	fmt.Println(a)

	primes := [6]int{2, 3, 5, 7, 11, 13} //初始化类似于结构体，
	fmt.Println(primes)
}
```
# slice
[]T 是一个slice ，  a[low: high] 是子slice， slice 更像是对数组的指针

``` go
import "fmt"

func main() {
	primes := [6]int{2, 3, 5, 7, 11, 13}

	var s []int = primes[1:4]
	fmt.Println(s)
	names := [4]string{
		"John",
		"Paul",
		"George",
		"Ringo",
	}
	fmt.Println(names)

	a := names[0:2]
	b := names[1:3]
	fmt.Println(a, b)

	b[0] = "XXX"
	fmt.Println(a, b)
	fmt.Println(names)
	q := []int{2, 3, 5, 7, 11, 13}
	fmt.Println(q)

	r := []bool{true, false, true, true, false, true}
	fmt.Println(r)

	s := []struct {
		i int
		b bool
	}{
		{2, true},
		{3, false},
		{5, true},
		{7, true},
		{11, false},
		{13, true},
	}
	fmt.Println(s)
}

```
## 默认值
下面等价
```
a[0:10]
a[:10]
a[0:]
a[:]
```
## 长度和容量
len(s)和cap(s)分别来完成这个任务
```go
func main() {
	s := []int{2, 3, 5, 7, 11, 13}
	printSlice(s)

	// Slice the slice to give it zero length.
	s = s[:0]
	printSlice(s)

	// Extend its length.
	s = s[:4]
	printSlice(s)

	// Drop its first two values.
	s = s[2:]
	printSlice(s)
}

func printSlice(s []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}

```
## nil slices
slice默认是nil 长度为0，容量为0， 没有底层的数组
```go
var s []int
	fmt.Println(s, len(s), cap(s))
	if s == nil {
		fmt.Println("nil!")
	}
```
## 构造
```go
a: = make([]int, 5) // len(a)=5, 值为0
b := make([]int, 0, 5) // len(b)=0, cap(b)=5
b = b[:cap(b)] // len(b)=5, cap(b)=5
b = b[1:]      // len(b)=4, cap(b)=4

```
## slice嵌套
```go
	// Create a tic-tac-toe board.
	board := [][]string{
		[]string{"_", "_", "_"},
		[]string{"_", "_", "_"},
		[]string{"_", "_", "_"},
	}

	// The players take turns.
	board[0][0] = "X"
	board[2][2] = "O"
	board[1][2] = "X"
	board[1][0] = "O"
	board[0][2] = "X"

	for i := 0; i < len(board); i++ {
		fmt.Printf("%s\n", strings.Join(board[i], " "))
	}
```
# append
slice的内建append函数
```
func append(s []T, vs ...T) []T
```
使用例子
```go
func main() {
	var s []int
	printSlice(s)

	// append works on nil slices.
	s = append(s, 0)
	printSlice(s)

	// The slice grows as needed.
	s = append(s, 1)
	printSlice(s)

	// We can add more than one element at a time.
	s = append(s, 2, 3, 4)
	printSlice(s)
}

func printSlice(s []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}

```
## range
用于遍历 slice和map
```go
var pow = []int{1, 2, 4, 8, 16, 32, 64, 128}

func main() {
	for i, v := range pow {
		fmt.Printf("2**%d = %d\n", i, v)
	}
}

```
使用_可以忽略不需要的index或者value
```go
for i, _ := range pow
for _, value := range pow
```

# Map
## 定义和使用
```go
type Vertex struct {
	Lat, Long float64
}

var m map[string]Vertex

func main() {
	m = make(map[string]Vertex)
	m["Bell Labs"] = Vertex{
		40.68433, -74.39967,
	}
	fmt.Println(m["Bell Labs"])
}

```
## 遍历
```go
import "fmt"

type Vertex struct {
	Lat, Long float64
}

var m = map[string]Vertex{
	"Bell Labs": Vertex{
		40.68433, -74.39967,
	},
	"Google": Vertex{
		37.42202, -122.08408,
	},
}

var t = map[string]Vertex{
	"Bell Labs": {40.68433, -74.39967},
	"Google":    {37.42202, -122.08408},
}


func main() {
	fmt.Println(m)
}
```
## 操作

```go
m := make(map[string]int)

	m["Answer"] = 42
	fmt.Println("The value:", m["Answer"])

	m["Answer"] = 48
	fmt.Println("The value:", m["Answer"])

	delete(m, "Answer")
	fmt.Println("The value:", m["Answer"])

	v, ok := m["Answer"] //If key is in m, ok is true. If not, ok is false.
	fmt.Println("The value:", v, "Present?", ok)
```

# 函数变量
``` go

func compute(fn func(float64, float64) float64) float64 {
	return fn(3, 4)
}

func main() {
	hypot := func(x, y float64) float64 {
		return math.Sqrt(x*x + y*y)
	}
	fmt.Println(hypot(5, 12))

	fmt.Println(compute(hypot))
	fmt.Println(compute(math.Pow))
}

```
## 闭包
```go
func adder() func(int) int {
	sum := 0
	return func(x int) int {
		sum += x
		return sum
	}
}

func main() {
	pos, neg := adder(), adder()
	for i := 0; i < 10; i++ {
		fmt.Println(
			pos(i),
			neg(-2*i),
		)
	}
}

```
