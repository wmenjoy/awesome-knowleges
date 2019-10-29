# 约定
1、go是通过package来组织的（与python类似），只有package名为main的包可以包含main函数，一个可执行程序有且仅有一个main包，通过import关键字来导入其他非main包。

2、可见性规则。go语言中，使用大小写来决定该常量、变量、类型、接口、结构或函数是否可以被外部包含调用。根据约定，函数名首字母小写即为private，函数名首字母大写即为public。
