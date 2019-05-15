# nginx 日志解析工具
目前初步的需求就是要把nginx的access.log的内容解析成字段存入mysql，方便之后分析

## 方案
1. Nginx日志不压缩access文件，且只保留7天
2. crontab每日定时执行本工具，将前一天的access.log解析并存入mysql
