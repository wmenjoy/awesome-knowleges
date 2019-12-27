# 日志脱敏

## 为什么要脱敏

日志是程序运行信息的必要展示途径，但是我们总是在不经意间，会把系统的配置信息，安全，敏感信息，所承载的业务数据暴露出来。如果日志存储不当，被恶意使用，那么后果不堪设想。 

## 敏感信息有哪些

1. **账号，姓名**：我们银行账号，我们的姓名，我们其他的登录账号，涉及到私人信息的，是需要加密的
2. **邮箱**, 邮箱目前和我们的姓名，账号一样，是个人信息很关键的一部分。
3. **密码，证书** 系统密码，用户登录密码，用户相关的私钥证书
4. **身份证，护照，ID**
5. **手机号**
6. **地址**等 

以json格式为例，我们原始信息如下：
```
{"id":"130281201101181001","email":"liujinliang123345@qq.com", "name":"张三",  "mobile":"187211113211", "bankCard":"6625880137889924","bizContent":"{\"id\":\"130281201101181001\"}"
```
按照业内通用的混下规则，我们脱敏的效果如下
```
{"id":"130**********1001","email":"****45@qq.com", "name":"*三",  "mobile":"18***113211", "bankCard":"662****137889924","bizContent":"{\"id\":\"130**********1001\"}"
```


## 哪些地方需要脱敏

1. 处理用户请求，输出的时候

2. 调用第三方信息输出调试信息的时候。http，rpc等

3. 各种中间件加载的时候，业务的配置信息可能输出到日志中

## 日志脱敏分类 

- 日志是否有固定格式，比如json， xml，urlpath，而有些日志是用户输入的，格式未知

- 日志的敏感信息是否固定  比如脱敏的字段是不是已知固定的

## 有哪些处理方式

### 要求

1. 日志对应字段不输出
2. 日志对应字段混淆， 姓名，邮箱，手机号，ID，可能有不同的混淆方案
3. 对日志的敏感字段加密输出，便于利用相关信息统计分析

### 实现手段

1. 日志工具在输出日志的时候，会自动调用对象的toString方法，所以我们可以重写ToString()方法。 但是toString在输出有很大的局限性，而且好多日志本身是String, 所以这种方法不具有通用性。

2. 使用Json，xml等工具在将对象转换为String对像的过程中，处理（借助于注解，等）和ToString一样，只能针对对象

3. 直接字符串处理，对于有固定格式，固定key值的，用正则表达式替换比较精确，不容易误杀，其他的只能根据值来正则替换，误杀率比较高

### 处理时机

1. 对象转换为字符串的之前
   - toString
   - 使用Json，xml等工具在将对象转换为String对像的过程中

2. 对象转换为字符串以后或者本身为字符串
   - 在日志消息格式化之前
   - 在日志消息格式化的过程中

**对比**

字符串转换之前处理，效率高，精确， 转换之后处理，效率低，但是可能处理不了所有的日志情况，有遗漏，而且在日志角度看消息，格式可能好多种，处理起来，比较难，不精确。但是能处理到所有的日志。

## 详解

### ToString

ToString 可以说是Java序列化的核心，比较简单，没有特殊需求，完全可以自己手动完成，另一个比较不错的选择就是使用apache的commons包提供的ToStringBuilder

``` java
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
...

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("userId", userId)
                .append("bindCardId", MaskUtil.idMask(bindCardId))
                .build();
 }
```


### Json 序列化工具对混淆的支持

fastJson和Jackson听歌Filter的功能, 基本思路如下：

1. 自定义注解，可让用户自定义脱敏方式，用于实体类的属性

2. 基于ValueFilter进行 属性注解拦截，并多value进行替换脱敏

3. 使用json序列化对象是指定自定义序列化Filter

具体参考 [json, logback日志脱敏](https://blog.csdn.net/qq_26418435/article/details/103620548)


### 直接正则表达式的字符串替换

无法确认需要处理的key的情况下，枚举所有可能的正则表达式，只对值进行匹配，然后对字符串进行正则替换，误杀率比较高，一般不建议使用。 这里就不在说明

#### 根据key和value替换

不管是json，xml，url路径的参数，还是其他的日志格式，如果是key，value这种格式，其实都是有办法用正则表达式完成替换的

比如json，key，可以是多个值，或者模糊值， 替换的字符串就是利用正则表达式的分组功能。假如: beforeKeepLength，混淆前面保留的字符串数, endKeepLength 混淆后保留的字符串数
简单就是这种: 
```
(.*)"tempKey":"(.{beforeKeepLength}).*.{endKeepLength}".* 
```
具体代码如下
``` java

    String tempKey = "(?:" + key +")";
    String  replaceStr = "$1" + getMaskStr(maskLength) + "$3"; //替换后的字符串

    patternStr = "((?:\\\\)*\"" + tempKey + "(?:\\\\)*\"\\s*:\\s*(?:\\\\)*\".{" + beforeKeepLength + "})([^\",]*)" +
    "([^\\\\\",]{" + endKeepLength + "}(?:\\\\)*\")";

```

其他可参考[MixerUtils](src/MixerUtils.java)

### Logback 消息插件
  
重写MessageConverter类，对收到的消息，进行正则替换

``` java

public class LogDesensitizeConverter  extends MessageConverter {

   LogMixer logMixer;
  /**
   * 日志脱敏关键字
   */
  @Override
  public String convert(ILoggingEvent event) {
    // 获取原始日志
    String oriLogMsg = event.getFormattedMessage();

    return replace(oriLogMsg);
  }

  private String replace(String oriLogMsg){
    return logMixer.replace(oriLogMsg);
  }
}

```

这里LogMixer 参考[MixerUtils](src/MixerUtils.java)

## 参考

1. [JSON和Logback日志脱敏](https://blog.csdn.net/qq_26418435/article/details/103620548)

2. [使用fastjson进行序列化时进行数据脱敏](https://blog.csdn.net/HinstenyHisoka/article/details/85167805)



