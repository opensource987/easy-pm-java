#ela版本：7.13

## 模块添加对应依赖
```xml
<dependency>
    <groupId>com.mdframework</groupId>
    <artifactId>easy-pm-elasticsearch</artifactId>
</dependency>
```

## yml文件添加对应配置
```yaml
spring: 
    data:
      elasticsearch:
        client:
          reactive:
            connection-timeout:
```
