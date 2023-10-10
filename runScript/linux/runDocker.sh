# !/bin/bash
docker pull registry.cn-shenzhen.aliyuncs.com/xxxx/easy-pm-cloud-lab-gateway:1.0.0
docker pull registry.cn-shenzhen.aliyuncs.com/xxxx/easy-pm-cloud-mall-lab-api:1.0
docker run -d --net=host -p 8087:8087 -v D:\software\apache-skywalking-apm-8.1.0\apache-skywalking-apm-bin\agent:/mnt registry.cn-shenzhen.aliyuncs.com/xxxx/easy-pm-cloud-mall-lab-api:1.0
docker run -d --net=host -p 8980:8980 -v D:\software\apache-skywalking-apm-8.1.0\apache-skywalking-apm-bin\agent:/mnt registry.cn-shenzhen.aliyuncs.com/xxxx/easy-pm-cloud-lab-gateway:1.0.0
