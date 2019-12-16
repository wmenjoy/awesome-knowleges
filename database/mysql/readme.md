# Mysql 数据库相关知识汇总



## tips
* 获取数据库文件的大小
``` sql
select table_name,table_rows,data_length+index_length, concat(round((data_length+index_length)/1024/1024,2),'MB')  data from tables where table_schema='xxx_db';
```
