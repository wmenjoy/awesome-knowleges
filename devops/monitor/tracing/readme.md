# Tracing 

## What's Tracing

A trace is used to track the time spent by an application processing a request and the status of this request. Each trace consists of one or more spans.

### Trace And Span

**Traces** in OpenTracing are defined implicitly by their **Spans**. In particular, a **Traces**  can be thought of as a directed acyclic graph (DAG) of **Spans**, where the edges between **Spans** are called **References**.

For example, the following is an example Trace made up of 8 Spans:


```
Causal relationships between Spans in a single Trace


        [Span A]  ←←←(the root span)
            |
     +------+------+
     |             |
 [Span B]      [Span C] ←←←(Span C is a `ChildOf` Span A)
     |             |
 [Span D]      +---+-------+
               |           |
           [Span E]    [Span F] >>> [Span G] >>> [Span H]
                                       ↑
                                       ↑
                                       ↑
                         (Span G `FollowsFrom` Span F)

```
Sometimes it's easier to visualize Traces with a time axis as in the diagram below:

```
––|–––––––|–––––––|–––––––|–––––––|–––––––|–––––––|–––––––|–> time

 [Span A···················································]
   [Span B··············································]
      [Span D··········································]
    [Span C········································]
         [Span E·······]        [Span F··] [Span G··] [Span H··]
```


## Design Goals of a Tracing System

- Low Overhead 以减少对程序的影响为前提

- Application-level transparency: 开发人员应该对Tracing System无感知，否则instrumentation的问题，导致服务挂掉。

- Scalability: 需要满足未来服务增长的规模。

## Trace Standard

1. [OpenTelemetry](https://github.com/open-telemetry/opentelemetry-specification/blob/master/specification/overview.md)

2. [OpenTracing]()

3. [OpenCensus](https://opencensus.io/tracing/span/link/)


## How to choose Suitable Trace Systems

- 你使用的什么语言，决定了你用什么方式实现探针。选择对语言入侵相对较少的

- 所选择的系统，尽可能遵守，行业标准， 适配，迁移方便

- 所选择的系统扩展性一定要较高。

- 提供的功能尽可能的全面。


## 开源对比

### Open Sources Project

1. [zipkin](zipkin/readme.md)
2. [jaeger](jaeger/readme.md)
3. [SkyWalking](../projects/skywalking/readme.md)
4. [Korean Pinpoint](https://github.com/naver/pinpoint)
5. [CAT](https://github.com/dianping/cat/wiki/model)

### 整体功能对比

| | Pinpoint | Zipkin| Jaeger| SkyWalking|CAT|
| :--: | --------| -------| ------|----------|-----|
| OpenTracing兼容|否|支持|支持|支持|否|
|文档|文档完善 |文档完善|文档完善|文档完善|资料比较少|
|开发者社区|naver|twitter|uber, CNCF|apache,华为|大众点评|
|社区活跃度|9.7k|12.1k|9.8k|11.6k|12.3k|
|client语言支持|java, php|java,c#,go,php等|java,c#,go,php等|Java, .NET Core, NodeJS and PHP,Go|Java,c,C++,Python, go, Node.js|
|服务端语言|java|go|java|java|java
|扩展性|弱|强|强|中|低
|UI丰富程度|高|低|中|中|中|
|client和服务端的协议|thrift|http,MQ|udp,http|udp,http,grpc|未知
|告警支持|支持|不支持|不支持|支持|支持|
|发布包|war|jar|jar|jar|war
|存储选择|hbase|es,mysql,cassandra,内存|ES，kafka,Cassandra,内存|ES，H2,mysql,TIDB,sharding sphere|mysql,hdfs
|跟踪粒度|细|一般|一般|一般|一般
|trace查询|不支持|支持|支持|支持|不支持|
|埋点监控|支持|不支持|不支持|支持|支持
|认证方式|-|-|不支持|基本认证|-


### Java Client Probes

| | Pinpoint | Zipkin| Jaeger| SkyWalking|CAT|
| :--: | --------| -------| ------|----------|-----|
| 实现方式|字节码注入|拦截请求|拦截请求|字节码注入，拦截支持|拦截
|代码侵入性|无|有|有|无|有
| Jvm监控|支持|不支持|不支持|支持|支持
|性能损失|高|中|中|高|中

#### PinPoint 和SkyWalking 支持的插件对比

|类别|Pinpoint|SkyWalking|
|---|----------|---------|
|web容器|Tomcat6/7/8,Resin,Jetty,JBoss,Websphere|Tomcat7/8/9,Resin,Jetty|
|JDBC|Oracle,mysql|Oracle,mysql,Sharding-JDBC|
|消息中间件	ActiveMQ, RabbitMQ|RocketMQ 4.x,Kafka
|日志	log4j, Logback|log4j,log4j2, Logback
|HTTP库	Apache HTTP Client, GoogleHttpClient, OkHttpClient|	Apache HTTP Client, OkHttpClient,Feign
|Spring体|spring,springboot|spring,springboot,eureka,hystrix
|RPC框架|Dubbo,Thrift|Dubbo,Motan,gRPC,ServiceComb
|NOSQL|Memcached, Redis, CASSANDRA|Memcached, Redis


### Apache Skywalking And CNCF Jaeger

#### 集群和部署

Apache Skywalking 支持使用 Apache ZooKeeper 或 Kubernetes 标签进行集群部署，主要是为了Metric的聚合，但是，这种方法就迫使客户端和服务端的位置紧密耦合在一起。
Jaeger 是提供了个独特的代理，与微服务共存，这种就把路由和发现从客户端隔离开。这种特别适合部署到k8s进群中，但是，将端口部署到非k8s架构可能会比较麻烦。需要部署代理

#### 自适应采样

自适应采样，指的是根据目前的跟踪信息，改变采样频率的能力，尤其是对于流量巨大，频繁使用的服务，应该减少采样次数。
目前，这两种都不支持， Jaeger已经有规划。


### 参考

1. [分布式调用链调研](https://my.oschina.net/u/3770892/blog/3005395?from=timeline&isappinstalled=0)
   
2. [APM巅峰对决：skywalking P.K. Pinpoint
](https://www.jianshu.com/p/626cae6c0522) 

3. [调用链选型之Zipkin，Pinpoint，SkyWalking，CAT](https://www.jianshu.com/p/0fbbf99a236)

4. [Jaeger vs Apache Skywalking
](https://blog.getantler.io/jaeger-vs-apache-skywalking/)

5. [Google Dapper](https://storage.googleapis.com/pub-tools-public-publication-data/pdf/36356.pdf)

6. [Spring Cloud Sleuth](https://cloud.spring.io/spring-cloud-sleuth/reference/html/)

7. [OpenTelemetry-可观察性的新时代](https://www.jianshu.com/p/4c19591bd7d2)
