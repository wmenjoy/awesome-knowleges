# cni

## iptables
![image](https://user-images.githubusercontent.com/9961069/123911146-fb159f80-d9ad-11eb-95f0-006dae3d75fc.png)
k8s 官方定义的iptables Chain
``` golang
// the services chain
kubeServicesChain utiliptables.Chain = "KUBE-SERVICES"
 
// the external services chain
kubeExternalServicesChain utiliptables.Chain = "KUBE-EXTERNAL-SERVICES"
 
// the nodeports chain
kubeNodePortsChain utiliptables.Chain = "KUBE-NODEPORTS"
 
// the kubernetes postrouting chain
kubePostroutingChain utiliptables.Chain = "KUBE-POSTROUTING"
 
// the mark-for-masquerade chain
KubeMarkMasqChain utiliptables.Chain = "KUBE-MARK-MASQ"     /*对于未能匹配到跳转规则的traffic set mark 0x8000，有此标记的数据包会在filter表drop掉*/
 
// the mark-for-drop chain
KubeMarkDropChain utiliptables.Chain = "KUBE-MARK-DROP"    /*对于符合条件的包 set mark 0x4000, 有此标记的数据包会在KUBE-POSTROUTING chain中统一做MASQUERADE*/
 
// the kubernetes forward chain
kubeForwardChain utiliptables.Chain = "KUBE-FORWARD"
```

## 参考
1. [k8s iptables_bijiarong8928的博客-CSDN博客](https://blog.csdn.net/bijiarong8928/article/details/100964459)
2. [iptables 的mangle表_a_tu_的专栏-CSDN博客_mangle](https://blog.csdn.net/a_tu_/article/details/79359341)
