# Apache SkyWalking 

## 1. Introduction

### 1.1 Concepts

- **Service**. Represent a set/group of workloads to provide the same behaviours for incoming requests. You can define the service name when you are using instrument agents or SDKs. Or SkyWalking uses the name you defined in platform such as Istio.

- **Service Instance**. Each one workload in the Service group is named as an instance. Like `pods` in Kubernetes, it doesn't need to be a single process in OS. Also if you are using instrument agents, an instance is actually a real process in OS.

- **Endpoint**. It is a path in the certain service for incoming requests, such as **HTTP** URI path or **gRPC** service class + method
signature. 

- **Probe**. In SkyWalking, probe means an agent or SDK library integrated into target system, which take charge of collecting telemetry data including tracing and metrics. Based on the target system tech stack, probe could use very different ways to do so. But ultimately they are same, just collect and reformat data, then send to backend.

### 1.2 What's SkyWalking

SkyWalking is an open source observability platform to collect, analyze, aggregate and visualize data from services and cloud native infrastructures. SkyWalking provides an easy way to keep you have a clear view of your distributed system, even across Cloud. It is more like a modern APM, specially designed for cloud native, container based and distributed system.

Many supported Features are list here: 
- Service, service instance, endpoint metrics analysis
- Root cause analysis
- Service topology map analysis
- Service, service instance and endpoint dependency analysis
- Slow services and endpoints detected
- Performance optimization
- Distributed tracing and context propagation
- Database access metrics. Detect slow database access statements(including SQL statements).
- Alarm

Go to [Live Demo](http://122.112.182.72:8080/)

### 1.3 Why use SkyWalking

SkyWalking provides solutions for observing and monitoring distributed system, in many different scenarios. First of all,
like traditional ways, SkyWalking provides auto instrument agents for service, such as Java, C#
and Node.js. At the same time, it provides manual instrument SDKs for Go(Not yet), C++(Not yet).
Also with more languages required, risks in manipulating codes at runtime, cloud native infrastructures grow 
more powerful, SkyWalking could use Service Mesher infra probes to collect data for understanding the whole distributed system.


In general, it provides observability capabilities for **service**(s), **service instance**(s), **endpoint**(s).

## 2. Architecture And Design
### 2.1 Architecture

The SkyWalking is Logically split into four parts: Probes, Platform backend, Storage and UI
![The SkyWaling Architecture](images/SkyWorking_Architecture.jpeg)

* The **Probes**: collect data and reformat them in SkyWalking requirements.

* The **Platform backend** is for aggregation, analysis and driving process from probe to UI. It also provides the pluggable capabilities for incomming formats (like Zipkin's), storage implementors and cluster management, You even can customize aggregation and analysis by using [Observability Analysis Language
](https://github.com/apache/skywalking/blob/master/docs/en/concepts-and-designs/oal.md)

* The **Storage**: Many Implementors can be choose, such as Elasticsearch, H2, Mysql. 

* The **UI**: Beautiful UI for Users

### 2.2 Design Goals

- **Keep Observability**. No matter how does the target system deploy, SkyWalking could provide a solution or 
integration way to keep observability for it. Based on this, SkyWalking provides several runtime forms and probes.

- **Topology, Metrics and Trace Together**. The first step to see and understand a distributed system should be 
from topology map. It visualizes the whole complex system as an easy map. Under that topology, OSS people requires
more about metrics for service, instance, endpoint and calls. Trace exists as detail logs for making sense of those metrics.
Such as when endpoint latency becomes long, you want to see the slowest the trace to find out why. So you can see,
they are from big picture to details, they are all needed. SkyWalking integrates and provides a lot of features to
make this possible and easy understand.

- **Light Weight**. There two parts of light weight are needed. (1) In probe, we just depend on network
communication framework, prefer gRPC. By that, the probe should be as small as possible, to avoid the library
conflicts and the payload of VM, such as permsize requirement in JVM.
(2) As an observability platform, it is secondary and third level system in your project environment.
So we are using our own light weight framework to build the backend core. Then you don't need to 
deploy big data tech platform and maintain them. SkyWalking should be simple in tech stack.

- **Pluggable**. SkyWalking core team provides many default implementations, but definitely it is not enough,
and also don't fit every scenario. So, we provide a lot of features for being pluggable. 

- **Portability**.  SkyWalking can run in multiple environments, including: 
(1) Use traditional register center like eureka.
(2) Use RPC framework including service discovery, like Spring Cloud, Apache Dubbo.
(3) Use Service Mesh in modern infrastructure.
(4) Use cloud services.
(5) Across cloud deployment. 
SkyWalking should run well in all these cases.

- **Interop**. Observability is a big landscape, SkyWalking is impossible to support all, even by its community.
As that, it supports to interop with other OSS system, mostly probes, such as Zipkin, Jaeger, OpenTracing, OpenCensus.
To accept and understand their data formats makes sure SkyWalking more useful for end users. And don't require
the users to switch their libraries.

## 3. Details

### 3.1 Probes

#### 3.1.1 Types of Probes
- **Language based native agent**. This kind of agents runs in target service user space, like a part of user codes. Such as SkyWalking Java agent, use -javaagent command line argument to manipulate codes in runtime, manipulate means change and inject user's codes. Another kind of agents is using some hook or intercept mechanism provided by target libraries. So you can see, these kinds of agents based on languages and libraries.
- **Service Mesh probe**. Service Mesh probe collects data from sidecar, control panel in service mesh or proxy. In old days, proxy is only used as ingress of the whole cluster, but with the Service Mesh and sidecar, now we can do observe based on that.
- **3rd-party instrument library**. SkyWalking accepts other popular used instrument libraries data format. It analysis the
data, transfer it to SkyWalking formats of trace, metrics or both. This feature starts with accepting Zipkin span data. See
[Receiver for other tracers](https://github.com/apache/skywalking/blob/master/docs/en/setup/backend/backend-receivers.md) to know more.

#### 3.1.2 How to choose Probes

1. Use Language based native agent only.
2. Use 3rd-party instrument library only, like Zipkin instrument ecosystem.
3. Use Service Mesh probe only.
4. Use Service Mesh probe with Language based native agent or 3rd-party instrument library in tracing status. (Advanced usage)

#### 3.1.3 Reference

1. [Service auto instrument agent](https://github.com/apache/skywalking/blob/master/docs/en/concepts-and-designs/service-agent.md)

2. [Manual instrument SDK](https://github.com/apache/skywalking/blob/master/docs/en/concepts-and-designs/manual-sdk.md)

3. [Service Mesh probe](https://github.com/apache/skywalking/blob/master/docs/en/concepts-and-designs/service-mesh-probe.md)

### 3.2 Backend - Observability Analysis Platform

#### 3.2.1 Capabilities

OAP(Observability Analysis Platform) accepts data from different sources. 

- **Tracing** Including, SkyWalking native data formats. Zipkin v1,v2 data formats and Jaeger data formats.

- **Tracing** skyWalking integrates with Service Mesh platforms, such as Istio, Envoy, Linkerd, to provide observability from data panel or control panel. Also, SkyWalking native agents can run in metrics mode, which highly improve the performance.

By using any integration solution provided, such as SkyWalking log plugin or toolkits, SkyWalking provides visualization integration for binding tracing and logging together by using the trace id and span id.

#### 3.2.2 Reference

1. [Observability Analysis Language](https://github.com/apache/skywalking/blob/master/docs/en/concepts-and-designs/oal.md)

2. [Query in OAP](https://github.com/apache/skywalking/blob/master/docs/en/protocols/README.md#query-protocol)

### 3.2 UI

SkyWalking native UI provides the default visualization solution.
It provides observability related graphs
about overview, service, service instance, endpoint, trace and alarm, 
including topology, dependency graph, heatmap, etc.

Also, we have already known, many of our users have integrated SkyWalking
into their products. 
If you want to do that too, please use [SkyWalking query protocol]([../protocols/README.md#query-protocol](https://github.com/apache/skywalking/blob/master/docs/en/protocols/README.md#query-protocol)).
 

### 3.3 CLI

SkyWalking CLI is a command interaction tool for the SkyWalking user or OPS team, as an alternative besides using browser GUI. It is based on SkyWalking [GraphQL query protocol](https://github.com/apache/skywalking-query-protocol), same as GUI

## 4 Setup

服务之间的依赖关系参考： ![服务之间的调用关系](images/deploy_ui.png)

- Download Backend, UI, probe Agent at [Download Page](http://skywalking.apache.org/downloads/). 

- Choose Suitable Language Agents In Service. [Java agent](https://github.com/apache/skywalking/blob/master/docs/en/setup/service-agent/java-agent/README.md), [Other Language](https://github.com/apache/skywalking/blob/master/docs/en/setup/README.md#language-agents-in-service)

- Service Mesh 参考[SkyWalking in Istio](https://github.com/apache/skywalking/blob/master/docs/en/setup/istio/README.md)

- [Backend UI and CLI setup document](https://github.com/apache/skywalking/blob/master/docs/en/setup/backend/backend-ui-setup.md)
