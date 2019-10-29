# 语法
## 普通语句
语法如下，和c，java等最大的不同，是不需要括号
```go
for ${init_statement}; ${condition_statement}; ${post statement}{
  // work
}
```

``` go
package main

import "fmt"

func main() {
	sum := 0
	for i := 0; i < 10; i++ {
		sum += i
	}
	fmt.Println(sum)
}


```
## while
如果没有初始值，和后续步骤，可以只有一个condition，类似于while循环
``` go
func main() {
	sum := 1
	for sum < 1000 {
		sum += sum
	}
	fmt.Println(sum)
}

```
## 死循环
```
for {
}
```
# if
## normal
if 的条件不需要括号，但是语句必须要括号
```go 
func sqrt(x float64) string {
	if x < 0 {
		return sqrt(-x) + "i"
	}
	return fmt.Sprint(math.Sqrt(x))
}
```
## specail
if 条件半段之前可以有一个简单的语句
```go
	if v := math.Pow(x, n); v < lim {
		return v
	}
	return lim
```
## else

```go
func pow(x, n, lim float64) float64 {
	if v := math.Pow(x, n); v < lim {
		return v
	} else if v == 0{
		fmt.Printf("%g >= %g\n", v, lim)
	} else {
  
  }
	// can't use v here, though
	return lim
}
```

# switch
switch 默认只运行选择的语句，可以不必须是常量或者整数
```go
import (
	"fmt"
	"runtime"
)

func main() {
	fmt.Print("Go runs on ")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("Linux.")
	default:
		// freebsd, openbsd,
		// plan9, windows...
		fmt.Printf("%s.\n", os)
	}
}
```
## switch 可以没有condition,类似于长的if-then-else
``` go
	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("Good morning!")
	case t.Hour() < 17:
		fmt.Println("Good afternoon.")
	default:
		fmt.Println("Good evening.")
	}
```





