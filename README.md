# rss-reader

RSS阅读器，基于Java SpringBoot搭建。

## 部署运行

```shell
docker run -d --restart=always \
-v ./config:/app/config \
-v ./log:/app/log \
-v ./images:/app/images \
-v /etc/localtime:/etc/localtime:ro \
--name rss-reader \
bcrjl/rss-reader:latest
```

打开`data.json`配置需要订阅的RSS源;打开`config.setting`配置系统参数;

以上配置均为动态刷新，无需重启容器

### 映射目录说明

| 本地目录 | 容器目录 | 说明    |
| --- | --- |-------|
| ./config | /app/config | 配置文件  |
| ./log | /app/log | 运行日志  |
| ./images | /app/images | 下载的文件 |

