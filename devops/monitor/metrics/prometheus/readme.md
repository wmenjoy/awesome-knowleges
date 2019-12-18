# prometheus

## Introduction

### What's Prometheus?

Prometheus is an open-source systems monitoring and alerting toolkit originally built at SoundCloud. Prometheus joined the Cloud Native Computing Foundation in 2016 as the second hosted project, after Kubernetes.

### Features

* a multi-dimensional data model with time series data identified by metric name and key/value pairs
* PromQL, a flexible query language to leverage this dimensionality no reliance on distributed storage; single server nodes are autonomous
* time series collection happens via a pull model over HTTP pushing time series is supported via an intermediary gateway
* targets are discovered via service discovery or static configuration
* multiple modes of graphing and dashboarding support


## Architecture And Design

下面这张图说明了Prometheus的架构，以及他的生态组件：

![Prometueus Architecture](images/prometheus_architecture.png)

### 组件说明
* **Instrument Jobs**: Prometheus从这些job直接或者间接的获取Metrics数据。
* **Job Exporters** 针对HAProxy, StatsD,Graphite等的特殊exporters
* **Push Gateway** 支持shot-lived Jobs
* **Prometheus Server** 对收集的数据进行存储，或者对这些数据进行处理，或者聚合，或者报警。
* **Alert Manager** 负责产生报警，并且发消息
* **UI And API** Grafana或者其他的API消费者，用来可视化收集的数据