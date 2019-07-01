# 安装
# 使用手册

# github使用plantum1、创建单独的文件rpc.txt，假如路径为wmenjoy/awesome-knowleges/master/nio/rpc.txt 简单的例子如下
```java
@startuml

class VersionableObject{
    + String  version;
    + String key;
    + V value;

}


class ConfigCenter{
  + String getVersion(String key)
  + V getValue(String key)
  + boolean update(key, V value)
}
ConfigCenter --> VersionableObject

@enduml
```
* 2、使用
``` md
![uncached image](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/wmenjoy/awesome-knowleges/master/nio/rpc.txt)
```
效果如下
![uncached image](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/wmenjoy/awesome-knowleges/master/nio/rpc.txt)

