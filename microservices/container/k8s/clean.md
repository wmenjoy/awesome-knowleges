# 环境清理:
## 清理脚本
```
## 删除所有容器  
sudo docker rm -f $(sudo docker ps -qa)  
#删除/var/etcd目录 
sudo rm -rf /var/etcd
#删除/var/lib/kubelet/目录，删除前先卸载  
for m in $(sudo tac /proc/mounts | sudo awk '{print $2}'|sudo grep /var/lib/kubelet);do  
sudo umount $m||true  
done  
sudo rm -rf /var/lib/kubelet/  
#删除/var/lib/rancher/目录，删除前先卸载  
for m in $(sudo tac /proc/mounts | sudo awk '{print $2}'|sudo grep /var/lib/rancher);do  
sudo umount $m||true  
done  
sudo rm -rf /var/lib/rancher/
#删除/run/kubernetes/ 目录  
sudo rm -rf /run/kubernetes/  
#删除所有的数据卷  
sudo docker volume rm $(sudo docker volume ls -q)  

```

## 确认脚本
  
#再次显示所有的容器和数据卷，确保没有残留  
sudo docker ps -a  
sudo docker volume ls

## 镜像删除：

```
## 查看镜像
docker image ls
## 删除镜像
docker rmi $镜像id
## 如果镜像被其他镜像引用，需要先删除引用，如果被容器使用，需要先删除容器

```
        
    
