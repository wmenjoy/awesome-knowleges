* 1、nginx 转发
* 2、使用ssh做端口转发 ssh root@192.168.116.84 "ausearch -c 'sshd' --raw | audit2allow -M my-sshd;semodule -i my-sshd.pp"; ssh -L 9092:192.168.116.84:9092 root@192.168.116.84
