# ElasticSearch 备份
## 综述
借助于ElasticSearch提供的镜像功能实现ElasticSearch索引归功能,执行过程:
- 1. 备份
   Elasticsearch 定期对索引进行快照到公共目录。然后通过文件同步服务，同步到归档环境的公共目录。
- 2. 归档环境恢复
   待归档文件同步完成，在归档elasticsearch执恢复操作。恢复索引

## 准备
### 1. 准备Elasticsearch各个节点可以共同访问的公共目录
### 2. 配置Elasticsearch的公共目录
### 3. 注册repository到ElasticSearch

## 执行备份

## 同步镜像文件

## 执行恢复操作
