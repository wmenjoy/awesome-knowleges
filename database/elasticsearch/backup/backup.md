# ElasticSearch 备份
## 综述
借助于ElasticSearch提供的镜像功能实现ElasticSearch索引归功能,执行过程:
- 1. 备份
   Elasticsearch 定期对索引进行快照到公共目录。然后通过文件同步服务，同步到归档环境的公共目录。
- 2. 归档环境恢复
   待归档文件同步完成，在归档elasticsearch执恢复操作。恢复索引

## 准备
### 1. 准备nfs server
``` bash
yum install -y nfs-utils
systemctl enable rpcbind.service
systemctl enable nfs-server.service
systemctl start rpcbind.service
systemctl start nfs-server.service

mkdir /data/elasticsearch/backup
echo /data/elasticsearch/backup 10.64.62.0/24(rw,sync,all_squash) > /etc/exports
exportfs -r
# 查看是否生效
exportfs -s
```
### 2. 准备Elasticsearch各个节点可以共同访问的公共目录
```bash
yum -y install showmount
#开启服务：
systemctl enable rpcbind.service
systemctl start rpcbind.servic
#查看nfs挂载信息
showmount -e 10.4.7.22


mount -t nfs 10.4.7.22:/data/db/elasticsearch/backup /data/es-backups/
```

### 2. 配置Elasticsearch的公共目录
修改elasticsearch的配置文件elasticsearch.yml
``` properties
path.repo: ["/data/elasticsearch/backup"]
```

### 3. 注册repository到ElasticSearch
```
PUT _snapshot/bjca_backup 
{
    "type": "fs", 
    "settings": {
        "location": "/data/elasticsearch/backup",
        "compress": true
    }
}

```
## 执行备份
###1. 发起备份申请
```
PUT /_snapshot/my_backup/snapshot_1
{
  "indices": "${indexName1},${indexName2}",
  "ignore_unavailable": true,
  "include_global_state": false
}

# 查看状态
GET _snapshot/my_backup/snapshot_name/_status
```
 // 备份创建好之后，在共享目录/root/backup里是这样的：
-rw-r--r-- 1 root root   31 12月 15 22:14 index
drwxr-xr-x 3 root root 4096 12月 15 22:14 indices
-rw-r--r-- 1 root root   83 12月 15 22:14 metadata-snapshot_name
-rw-r--r-- 1 root root  181 12月 15 22:14 snapshot-snapshot_name

###


## 同步镜像文件

## 执行恢复操作
```
POST /_snapshot/my_backup/snapshot_1/_restore
{
  "indices": "${indexName1},${indexName2}",
  "index_settings": {
    "index.number_of_replicas": 0
  },
  "ignore_index_settings": [
    "index.refresh_interval"
  ]
}
```

