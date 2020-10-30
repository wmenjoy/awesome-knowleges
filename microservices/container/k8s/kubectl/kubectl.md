# k8s命令学习

## 通用参数

```
kubectl $command [-n $namespace] [--all-namespaces] --show-tags=true
```

## run

```
kubectl run nginx --image=nginx
# expose container和service port
kubectl run nginx --image=nginx --port 6237 [--expose=true]

#指定命令
kubectl run nginx --image=nginx --port 6237 [--expose=true] --comand="sh /nginx"

kubectl run nginx --image=nginx --port 6237 [--expose=true] --comand="sh /nginx" --labels="app=nginx,env=dev"


kubectl run nginx --image=nginx --port 6237 [--expose=true] --comand="sh /nginx" --labels="app=nginx,env=dev" --replicas=1


kubectl run nginx --image=nginx --port 6237 --expose=true --comand="sh /nginx" --labels="app=nginx,env=dev" --env = "env=dev" --replicas=1 --service-generator="nginx-service"

# 快速创建deployment和service
kubectl run mynginx --image=nginx --replicas=2 --port 80 --expose=true --hostport=32251  --env "apply=mynginx" --labels="app=myngginx" --restart=Always
```

## delete

```
kubectl run mynginx --image=nginx --replicas=2 --port 80 --expose=true --hostport=32251  --env "apply=mynginx" --labels="app=myngginx" --restart=Always
#同时删除多个资源
kubectl delete pod,service mynginx

kubectl delete 

```
