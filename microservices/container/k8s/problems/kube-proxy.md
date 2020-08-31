# Kube Proxy 问题总结

## 问题

### 1. kube-proxy 日志一直有 --random-fully日志输出
1. 现象
  ``` bash
  I0831 09:54:43.941698   26922 proxier.go:793] Not using `--random-fully` in the MASQUERADE rule for iptables because the local version of iptables does not support it
  ```
1. 分析
   从日志的分析来看。 这个应该当前kube-proxy的版本不对导致, the Flag `--random-fully`出现在[kernetes 1.16: proxier](https://github.com/kubernetes/kubernetes/blob/efb461bc0727030dfcbdc6cfdc8ef054049d20bc/pkg/proxy/iptables/proxier.go#L789)
   对于iptables 来说，起源于`https://patchwork.ozlabs.org/patch/844016/`
   > There is a known race condition when allocating a port for masquerading that
     can lead to insertion in the conntrack table to fail under heavy load, with an
     increment of the insert_failed counter. The kernel supports the
     NF_NAT_RANGE_PROTO_RANDOM_FULLY flag since [1] which uses prandom_u32() to
     choose the first port to try when looking for a free tuple. Using this flag
     significantly reduces the number of insertion collision. This patch provides
     the user space part to make randomize-full support available in iptables on the
     MASQUERADE target as it was done for SNAT [2].
   关于Flag的描述：[iptables-extension.man](http://ipset.netfilter.org/iptables-extensions.man.html)
   > --random-fully
        Full randomize source port mapping If option --random-fully is used then port mapping will be fully randomized (kernel >= 3.13).
        IPv6 support available since Linux kernels >= 3.7.
1. 功能
1. 影响
1. 解决


## 参考

1. [kubernets错误总结-及解决办法](https://www.gylinux.cn/2795.html)
